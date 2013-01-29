package net.oraro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.dao.TeamDao;
import net.oraro.dao.UserDao;
import net.oraro.db.DBUtil;
import net.oraro.exception.DataAccessException;

import org.apache.log4j.Logger;

public class TeamDaoImpl implements TeamDao{
	
	private static Logger log = Logger.getLogger(TeamDaoImpl.class);

	@Override
	public boolean insert(Team team) throws DataAccessException {
		return false;
	}

	@Override
	public boolean update(Team team) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Team> queryAll() throws DataAccessException {
		
		String sql = "select a.id as team_id, a.team_name, a.description, b.id as user_id, b.name, b.empno " +
				"from kq_team a left join kq_user b on a.manager_id=b.id;";
		
		List<Team> teams = new ArrayList<Team>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			log.info(sql);
			
			while(rs.next()) {
				Team team = new Team();
				
				team.setId(rs.getInt("team_id"));
				team.setTeamName(rs.getString("team_name"));
				team.setDescription(rs.getString("description"));
				
				Object user_id_obj = rs.getObject("user_id");
				if(user_id_obj != null) {
					User user = new User();
					user.setId((Integer) user_id_obj);
					user.setEmpno(rs.getString("empno"));
					user.setName(rs.getString("team_name"));
					team.setManager(user);
				}
				teams.add(team);
			}
			
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return teams;
	}

	@Override
	public Team queryByAccountAndPassword(String account, String password)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Team> executeQuery(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
