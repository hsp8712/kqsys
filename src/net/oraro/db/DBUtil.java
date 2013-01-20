package net.oraro.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

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
	 */
	private static DataSource getDataSource() {
		if(dataSource != null) {
			return dataSource;
		}
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			dataSource = (DataSource)envCtx.lookup("jdbc/kqsys");
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
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
			if(conn != null || !conn.isClosed()) {
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
			if(stmt != null || !stmt.isClosed()) {
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
			if(rs != null || !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		release(stmt, conn);
	}
}
