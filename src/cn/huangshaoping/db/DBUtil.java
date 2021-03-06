package cn.huangshaoping.db;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

import cn.huangshaoping.bean.Team;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.exception.DataAccessException;


/**
 * 数据库工具
 * @author 6261000301
 *
 */
public class DBUtil {
	
	private static Logger log = Logger.getLogger(DBUtil.class);
	
	/** 数据源 */
	private static DataSource dataSource;
	
	/**	线程独占连接 */
	private static ThreadLocal<Connection> connectionContext = new ThreadLocal<Connection>();
	
	/**
	 * 获取数据源
	 * @return
	 * @throws SQLException 
	 */
	private static DataSource getDataSource() throws SQLException {
		if(dataSource != null) {
			return dataSource;
		}
		
		Properties dbConfig = null;
		try {
			dbConfig = new Properties();
			dbConfig.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new SQLException("File db.properties load failed.");
		}
		try {
			dataSource = BasicDataSourceFactory.createDataSource(dbConfig);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SQLException("DataSource created failed.");
		}
		
		return dataSource;
	}
	
	/**
	 * 获取当前线程的连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = connectionContext.get();
		
		if(conn == null || conn.isClosed()) {
			
			// Get a new connection, and set it into connectionContext
			
			DataSource ds = getDataSource();
			if(ds == null) {
				throw new SQLException("Cannot get DataSource, DataSource is null.");
			}
			conn = ds.getConnection();
			connectionContext.set(conn);
		}
		
		return conn;
	}
	
	/**
	 * 资源释放-释放Connection
	 * @param conn
	 * @throws SQLException
	 */
	public static void release(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 资源释放-释放Connection,Statement
	 * @param conn
	 * @throws SQLException
	 */
	public static void release(Statement stmt, Connection conn) {
		try {
			if(stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		release(conn);
	}
	
	/**
	 * 资源释放-释放Connection,Statement,ResultSet
	 * @param conn
	 * @throws SQLException
	 */
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		release(stmt, conn);
	}
	
	/**
	 * 执行存储过程
	 * @param procedureName		过程名称
	 * @param paramMap			所有参数列表 Map, key为参数索引位置
	 * @return	输出参数结果列表 Map, key为参数索引位置
	 */
	public static Map<Integer, Object> execProcedure(String procedureName, Map<Integer, CallableParam> paramMap) 
			throws SQLException {
		
		if(procedureName == null) {
			throw new NullPointerException("Procedure name is null.");
		}
		
		Connection con = null;
		CallableStatement cstmt = null;
		boolean execSuccess = false;
		
		StringBuffer sqlBuffer = new StringBuffer("{call ").append(procedureName); 
		
		Set<Integer> keys = paramMap.keySet();
		
		for (int i = 0; i < keys.size(); i++) {
			
			if(i == 0) {
				sqlBuffer.append("(?");
			} else {
				sqlBuffer.append(" ,?");
			}
			
			if(i == keys.size() - 1) {
				sqlBuffer.append(")");
			}
		}
		
		sqlBuffer.append(" }");
		
		// output parameters
		Map<Integer, Object> outParamMap = new HashMap<Integer, Object>();
		try {
			con = getConnection();
			String execSql = sqlBuffer.toString();
			log.info("Execute procedure sql:\n" + execSql + ", with parameters:" + paramMap);
			cstmt = con.prepareCall(execSql);
			for (Integer key : keys) {
				CallableParam cp = paramMap.get(key);
				cstmt.setObject(key, cp.getValue());
			}
			
			execSuccess = cstmt.execute();
		
			for (Integer key : keys) {
				CallableParam cp = paramMap.get(key);
				if (cp.getType() == CallableParam.TYPE_OUT
						|| cp.getType() == CallableParam.TYPE_INOUT) {
					Object outValue = cstmt.getObject(key);
					outParamMap.put(key, outValue);
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			if(!execSuccess) {
				throw new SQLException("Procedure execute failed.");
			} else {
				throw new SQLException("Procedure execute successfully, but get output parameter error.");
			}
		} finally {
			release(cstmt, con);
		}
		
		return outParamMap;
	}
	
	/**
	 * 执行查询一条记录
	 * @param sql
	 * @return
	 */
	public static Map<String, String> executeQueryOne(String sql) {
		
		Map<String, String> resultMap = null;
		
		List<Map<String, String>> list = executeQuery(sql);
		if(list != null && list.size() > 0) {
			resultMap = list.get(0);
		}
		
		return resultMap;
	}
	
	/**
	 * 执行sql查询，结果保存到List<Map<String, String>>中，map代表一行记录
	 * @param sql
	 * @return
	 */
	public static List<Map<String, String>> executeQuery(String sql) {
		
		log.info(sql);
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Map<String, String>> list = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			String[] columnNames = getColumnNames(rs.getMetaData());
			
			list = new ArrayList<Map<String,String>>();
			while(rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (String columnName : columnNames) {
					Object obj = rs.getObject(columnName);
					String columnVal = obj == null ? null : String.valueOf(obj);
					map.put(columnName, columnVal);
				}
				list.add(map);
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return list;
	}
	
	private static String[] getColumnNames(ResultSetMetaData rsmd) {
		if(rsmd == null) {
			return new String[0];
		}
		
		String[] columnNames = null;
		
		try {
			int columnCount = rsmd.getColumnCount();
			columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnLabel(i + 1);
				columnNames[i] = columnName;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		
		if(columnNames == null) {
			columnNames = new String[0];
		}
		
		return columnNames;
	}
	
	/*
	public static void test() {
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, 1));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, null));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, "DEV-testproc3"));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_IN, "DEV-testproc3"));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_IN, 2));
		paramMap.put(6, new CallableParam(CallableParam.TYPE_OUT, null));
		paramMap.put(7, new CallableParam(CallableParam.TYPE_OUT, null));
		Map<Integer, Object> outParamMap = execProcedure("kqp_team_manage", paramMap);
		
		System.out.println(outParamMap);
	}
	
	*/
}
