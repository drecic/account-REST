package org.drecic.email.sparkmail.dao;

import java.util.List;
import java.util.UUID;

public interface UserDao {
	List<User> findAll();
	User findById(UUID id);
	User findByUsername(String username);
	User findByLogin(String username, String password);
	User findByUsernameAndSecret(String username, String secret);
	
	public void setPassword(String username, String newPassword);
	public void setSecret(String username, String newSecret);
	
	public UUID insertUser(User toInsert, String password);
	public void updateUser(User toUpdate);
	public void deleteUser(User toDelete);
}
