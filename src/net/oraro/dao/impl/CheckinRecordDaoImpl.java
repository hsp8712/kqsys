package net.oraro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import net.oraro.bean.CheckinRecord;
import net.oraro.bean.User;
import net.oraro.dao.CheckinRecordDao;
import net.oraro.db.DBUtil;
import net.oraro.exception.DataAccessException;

import org.apache.log4j.Logger;

public class CheckinRecordDaoImpl implements CheckinRecordDao {
	
	private Logger log = Logger.getLogger(CheckinRecordDaoImpl.class);
	
	@Override
	public boolean insert(CheckinRecord checkinRecord)
			throws DataAccessException {
		
		if(checkinRecord == null) {
			throw new NullPointerException("CheckinRecord object is null.");
		}
		
		boolean isSuccess = false;
		String sql = "insert into kq_checkinrecord(user_id,check_time) values(?,?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			
			User user = checkinRecord.getUser();
			if(user == null) {
				throw new NullPointerException("The user object which to checkin is null.");
			}
			
			ps.setInt(1, user.getId());
			ps.setTimestamp(2, new Timestamp(checkinRecord.getCheckTime().getTime()));
			
			isSuccess = ps.execute();
			log.info(sql);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DataAccessException("数据访问异常.");
		}
		
		return isSuccess;
	}

	@Override
	public boolean update(CheckinRecord checkinRecord)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CheckinRecord> queryAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute(String sql) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CheckinRecord> executeQuery(String sql)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
