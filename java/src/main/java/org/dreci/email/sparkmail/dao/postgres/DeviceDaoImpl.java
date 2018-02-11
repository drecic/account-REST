package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bgalusha.email.sparkmail.ServiceException;
import org.bgalusha.email.sparkmail.dao.Device;
import org.bgalusha.email.sparkmail.dao.DeviceDao;

public class DeviceDaoImpl implements DeviceDao {
	
	private static DeviceMapper mapper = new DeviceMapper();

	@Override
	public List<Device> findAll() {
		final String SQL = "select * from devices";
		List<Device> toReturn = new LinkedList<Device>();
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Device device = mapper.mapRow(rs);
				toReturn.add(device);
	    	}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findAll() failed", e);
		}
		return toReturn;
	}

	@Override
	public List<Device> findByUserId(UUID userId) {
		final String SQL = "select * from devices where UserID = ?";
		List<Device> toReturn = new LinkedList<Device>();
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Device device = mapper.mapRow(rs);
				toReturn.add(device);
	    	}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByUserId() failed", e);
		}
		return toReturn;
	}

	@Override
	public Device findByToken(String token) {
		final String SQL = "select * from devices where Token = crypt(?, gen_salt('bf', 8))";
		Device toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, token);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
	    	}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByToken() failed", e);
		}
		return toReturn;
	}

	@Override
	public Device findByName(String name) {
		final String SQL = "select * from devices where Name = ?";
		Device toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, name);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
	    	}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByName() failed", e);
		}
		return toReturn;
	}

	@Override
	public UUID insertDevice(Device toInsert, String token) {
		UUID toReturn = null;
		final String SQL = "insert into Devices (UserID, Token, Expires, Name) values (?, crypt(?, gen_salt('bf', 8)), ?, ?) returning DeviceID";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, toInsert.getUserId());
			stmt.setString(2, token);
			stmt.setTimestamp(3, toInsert.getExpires());
			stmt.setString(4, toInsert.getName());
			
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				toReturn = UUID.fromString(rs.getString(1));
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": insertDevice() failed", e);
		}
		return toReturn;
	}

	@Override
	public void updateDevice(Device toUpdate) {
		final String SQL = "update Devices set Expires = ?, LastRefresh = ?, Name = ? where DeviceID = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setTimestamp(1, toUpdate.getExpires());
			stmt.setTimestamp(2, toUpdate.getLastRefresh());
			stmt.setString(3, toUpdate.getName());
			stmt.setObject(4, toUpdate.getId());
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": updateDevice() failed", e);
		}
	}

	@Override
	public void deleteDevice(Device toDelete) {
		final String SQL = "delete from devices where DeviceID = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, toDelete.getId());
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": deleteDevice() failed", e);
		}
	}
}
