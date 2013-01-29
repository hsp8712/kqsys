package net.oraro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.oraro.bean.DailyRecord;
import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.dao.DailyRecordDao;
import net.oraro.db.DBUtil;
import net.oraro.exception.DataAccessException;

import org.apache.log4j.Logger;

public class DailyRecordDaoImpl implements DailyRecordDao {
	
	private Logger log = Logger.getLogger(DailyRecordDaoImpl.class);

	@Override
	public boolean insert(DailyRecord dailyRecord) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DailyRecord dailyRecord) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DailyRecord> queryAll() throws DataAccessException {
		
		String sql = "select * from kq_dailyrecord";
		
		List<DailyRecord> dailyRecords = new ArrayList<DailyRecord>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			log.info(sql);
			
			while(rs.next()) {
				DailyRecord record = new DailyRecord();
				record.setDate(rs.getDate("record_date"));
				record.setFirstTime(rs.getTime("first_time"));
				record.setLastTime(rs.getTime("last_time"));
				record.setOverTime(rs.getTime("over_time"));
				record.setOverTimeHour(rs.getFloat("over_time_hour"));
				dailyRecords.add(record);
			}
			
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataAccessException("数据库异常.");
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		
		return dailyRecords;
	}

	@Override
	public boolean execute(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DailyRecord> executeQuery(String sql)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
