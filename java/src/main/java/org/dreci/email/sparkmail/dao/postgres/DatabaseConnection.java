package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bgalusha.email.sparkmail.ServiceException;

public class DatabaseConnection {
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "";
	
	private static DatabaseConnection instance;
	private Connection connection = null;

	private DatabaseConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ServiceException(this.getClass().getSimpleName() + ": failed to load Postgres Driver", e);
		}
		this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	public static boolean hasInstance() {
		return (instance != null);
	}

	public static DatabaseConnection getInstance() throws SQLException {
		if (instance == null || instance.connection.isClosed()) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
}
