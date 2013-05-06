package cn.huangshaoping.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;

import cn.huangshaoping.bean.Team;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.dao.UserDao;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.exception.DataAccessException;


public class UserDaoImpl implements UserDao{
	
	private static Logger log = Logger.getLogger(UserDaoImpl.class);
	
	
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
			log.info(sql);
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(ps, conn);
		}
		
		return execSuccess;
	}

	
	public boolean update(User user) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean delete(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public List<User> queryAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public User queryByAccountAndPassword(String account, String password) throws DataAccessException {
		
		String sql = "select u.account, u.empno, u.id, u.name, u.team_id, t.team_name, t.description " +
				"from kq_user u left join kq_team t on u.team_id=t.id where u.account=? and u.password=?";
		
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
			log.info(sql);
			
			if(rs.next()) {
				user = new User();
				user.setAccount(rs.getString("account"));
				user.setEmpno(rs.getString("empno"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				
				Team team = new Team();
				team.setId((Integer)rs.getObject("team_id"));
				team.setTeamName(rs.getString("team_name"));
				team.setDescription(rs.getString("description"));
				user.setTeam(team);
			}
			
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return user;
	}

	
	public boolean execute(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public List<User> executeQuery(String sql) throws DataAccessException {
		
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<User> users = new ArrayList<User>();
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			log.info(sql);
			
			while(rs.next()) {
				user = new User();
				user.setAccount(rs.getString("account"));
				user.setEmpno(rs.getString("empno"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return users;
	}


}
