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
import cn.huangshaoping.dao.TeamDao;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.exception.DataAccessException;


public class TeamDaoImpl implements TeamDao{
	
	private static Logger log = Logger.getLogger(TeamDaoImpl.class);

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
					user.setName(rs.getString("name"));
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

	public Team queryById(Integer id) throws DataAccessException {
		
		if(id == null) {
			throw new NullPointerException("Team id is null.");
		}
		
		String sql = "select id,team_name,description,manager_id from kq_team where id=" + id.toString();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Team team = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			log.info(sql);
			
			team = new Team();
			if(rs.next()) {
				team.setId(id);
				team.setTeamName(rs.getString("team_name"));
				team.setDescription(rs.getString("description"));
				
				User user = new User();
				user.setId(rs.getInt("manager_id"));
				team.setManager(user);
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return team;
	}

}
