package net.oraro.db;

import java.sql.Connection;
import java.sql.SQLException;

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
	private static ThreadLocal<Connection> connectionContext = new ThreadLocal<Connection>() {
		
		@Override
		protected Connection initialValue() {
			
			Connection conn = null;
			try {
				conn = DBUtil.getDataSource().getConnection();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
			
			return conn;
		}
	};
	
	/**
	 * 获取数据源
	 * @return
	 */
	private static DataSource getDataSource() {
		if(dataSource != null) {
			return dataSource;
		}
		
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e) {
			log.error(e.getMessage());
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
				throw new SQLException("DataSource is null.");
			}
			conn = ds.getConnection();
			connectionContext.set(conn);
		}
		
		return conn;
	}
}
