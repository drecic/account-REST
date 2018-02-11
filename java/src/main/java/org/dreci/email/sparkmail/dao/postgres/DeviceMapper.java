package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bgalusha.email.sparkmail.dao.Device;

public class DeviceMapper implements RowMapper<Device> {

	@Override
	public Device mapRow(ResultSet rs) throws SQLException {
		Device toReturn = new Device();
		toReturn.setId(UUID.fromString(rs.getString("DeviceID")));
		toReturn.setUserId(UUID.fromString(rs.getString("UserID")));
		toReturn.setIssued(rs.getTimestamp("Issued"));
		toReturn.setExpires(rs.getTimestamp("Expires"));
		toReturn.setLastRefresh(rs.getTimestamp("LastRefresh"));
		toReturn.setName(rs.getString("Name"));
		return toReturn;
	}
}
