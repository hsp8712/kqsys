package net.oraro.service;

import java.util.List;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import cn.huangshaoping.page.Page;

/**
 * 日考勤记录服务
 * @author 6261000301
 *
 */
public interface TeamService {
	
	/**
	 * 获取日考勤分页记录
	 * @param pageNo
	 * @return
	 */
	Page<Team> getAllTeams(int pageNo);
	
	/**
	 * 获取未分配组的用户list集合
	 * @return
	 */
	List<User> getNoTeamUsers();
	
	/**
	 * 新增组
	 * @param team
	 * @return
	 */
	boolean addTeam(Team team);
	
}
