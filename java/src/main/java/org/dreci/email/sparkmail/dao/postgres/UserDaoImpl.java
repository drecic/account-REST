package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bgalusha.email.sparkmail.ServiceException;
import org.bgalusha.email.sparkmail.dao.User;
import org.bgalusha.email.sparkmail.dao.UserDao;

public class UserDaoImpl implements UserDao {
	
	private static UserMapper mapper = new UserMapper();

	@Override
	public List<User> findAll() {
		final String SQL = "select * from users";
		List<User> toReturn = new LinkedList<User>();
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				User user = mapper.mapRow(rs);
				toReturn.add(user);
	    	}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findAll() failed", e);
		}
		return toReturn;
	}

	@Override
	public User findById(UUID id) {
		final String SQL = "select * from users where UserID = ?";
		User toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findById() failed", e);
		}
		return toReturn;
	}

	@Override
	public User findByUsername(String username) {
		final String SQL = "select * from users where Username = ?";
		User toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, username);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByUsername() failed", e);
		}
		return toReturn;
	}

	@Override
	public User findByLogin(String username, String password) {
		final String SQL = "select * from users where Username = ? and Password = crypt(?, gen_salt('bf', 8))";
		User toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, username);
			stmt.setObject(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByLogin() failed", e);
		}
		return toReturn;
	}

	@Override
	public UUID insertUser(User toInsert, String password) {
		UUID toReturn = null;
		final String SQL = "insert into users (Username, Password, RecoveryEmail) values (?, crypt(?, gen_salt('bf', 8)), ?) returning UserID";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setString(1, toInsert.getUsername());
			stmt.setString(2, password);
			stmt.setString(4, toInsert.getRecoveryEmail());
			
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				toReturn = UUID.fromString(rs.getString(1));
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": insertUser() failed", e);
		}
		return toReturn;
	}

	@Override
	public void updateUser(User toUpdate) {
		final String SQL = "update users set Username = ?, RecoveryEmail = ?, EmailVerified = ?, DisabledAt = ? where UserID = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setString(1, toUpdate.getUsername());
			stmt.setString(2, toUpdate.getRecoveryEmail());
			stmt.setBoolean(3, toUpdate.isEmailVerified());
			stmt.setTimestamp(4, toUpdate.getDisabledAt());
			stmt.setObject(5, toUpdate.getId());
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": updateUser() failed", e);
		}
	}

	@Override
	public void deleteUser(User toDelete) {
		final String SQL = "delete from users where UserID = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setObject(1, toDelete.getId());
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": updateUser() failed", e);
		}
	}

	@Override
	public void setPassword(String username, String newPassword) {
		final String SQL = "update users set Password = crypt(?, gen_salt('bf', 8)) where Username = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setString(1, newPassword);
			stmt.setString(2, username);
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": setPassword() failed", e);
		}
	}

	@Override
	public User findByUsernameAndSecret(String username, String secret) {
		final String SQL = "select * from users where Username = ? and Secret = crypt(?, gen_salt('bf', 8))";
		User toReturn = null;
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setString(1, username);
			stmt.setString(2, secret);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				toReturn = mapper.mapRow(rs);
			}
			
	    	rs.close();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": findByUsernameAndSecret() failed", e);
		}
		return toReturn;
	}

	@Override
	public void setSecret(String username, String newSecret) {
		final String SQL = "update users set Secret = crypt(?, gen_salt('bf', 8)) where Username = ?";
		try {
			Connection jdbc = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = jdbc.prepareStatement(SQL);
			stmt.setString(1, newSecret);
			stmt.setString(2, username);
			
			stmt.executeUpdate();
	    	stmt.close();
		} catch (SQLException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": setSecret() failed", e);
		}
	}
}
