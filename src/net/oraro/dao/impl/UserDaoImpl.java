package net.oraro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.dao.UserDao;
import net.oraro.db.DBUtil;
import net.oraro.exception.DataAccessException;

import org.apache.log4j.Logger;

public class UserDaoImpl implements UserDao{
	
	private static Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@Override
	public boolean insert(User user) throws DataAccessException {
		
		boolean execSuccess = false;
		
		String sql = "insert into " +
				"kq_user(empno, name, account, password, team_id) " +
				"values(?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmpno());
			ps.setString(2, user.getName());
			ps.setString(3, user.getAccount());
			ps.setString(4, user.getPassword());
			
			Team belongTeam = user.getTeam();
			if(belongTeam == null) {
				throw new DataAccessException("用户所数组不能为空.");
			}
			ps.setInt(5, belongTeam.getId());
			
			execSuccess = ps.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(ps, conn);
		}
		
		return execSuccess;
	}

	@Override
	public boolean update(User user) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> queryAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User queryByAccountAndPassword(String account, String password) throws DataAccessException {
		
		String sql = "select * from kq_user where account=? and password=?";
		
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setAccount(rs.getString("account"));
				user.setEmpno(rs.getString("empno"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return user;
	}

	@Override
	public boolean execute(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> executeQuery(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
