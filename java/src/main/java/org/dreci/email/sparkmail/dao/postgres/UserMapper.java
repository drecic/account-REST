package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bgalusha.email.sparkmail.dao.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.setId(rs.getString("UserID"));
		user.setCreatedAt(rs.getTimestamp("CreatedAt"));
		user.setUsername(rs.getString("Username"));
		user.setRecoveryEmail(rs.getString("RecoveryEmail"));
		user.setEmailVerified(rs.getBoolean("EmailVerified"));
		user.setDisabledAt(rs.getTimestamp("DisabledAt"));
		
		return user;
	}

}
