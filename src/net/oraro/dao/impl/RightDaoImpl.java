package net.oraro.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.oraro.bean.Right;
import net.oraro.dao.RightDao;
import net.oraro.db.DBUtil;
import net.oraro.exception.DataAccessException;

public class RightDaoImpl implements RightDao{
	
	private Logger log = Logger.getLogger(RightDaoImpl.class);
	
	public List<Right> queryByUserId(Integer userId) throws DataAccessException {
		
		if(userId == null) {
			throw new DataAccessException("User id is null.");
		}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Right> rights = null;
		try {
			conn = DBUtil.getConnection();
			
			String sql = "select b.* from kq_user a, kq_right b, kq_rightgrp_right c where a.id=" + userId +
							" and c.rightgrp_id=a.rightgrp_id and c.right_id=b.id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			log.info(sql);
			
			rights = new ArrayList<Right>();
			while(rs.next()) {
				Right right = new Right();
				right.setId(rs.getInt("id"));
				right.setRightLink(rs.getString("right_link"));
				right.setRightName(rs.getString("right_name"));
				
				rights.add(right);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据访问异常.");
		} finally {
			DBUtil.release(rs, stmt, conn);
		}
		
		return rights;
	}


}
