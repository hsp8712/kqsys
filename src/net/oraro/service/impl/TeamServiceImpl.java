package net.oraro.service.impl;

import java.util.List;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.common.Constants;
import net.oraro.dao.DaoFactory;
import net.oraro.exception.DataAccessException;
import net.oraro.service.TeamService;

import org.apache.log4j.Logger;

import cn.huangshaoping.page.Page;

public class TeamServiceImpl implements TeamService{
	
	private Logger log = Logger.getLogger(TeamServiceImpl.class);
	
	public Page<Team> getAllTeams(int pageNo) {
		
		List<Team> teams = null;
		try {
			teams = DaoFactory.getInstance().getTeamDao().queryAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		return new Page<Team>(teams, Constants.PAGE_SIZE, pageNo);
	}

	@Override
	public List<User> getNoTeamUsers() {
		String sql = "select * from kq_user where team_id is null";
		
		List<User> users = null;
		try {
			users = DaoFactory.getInstance().getUserDao().executeQuery(sql);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		return users;
	}

	@Override
	public boolean addTeam(Team team) {
		
		
		
		return false;
	}

}
