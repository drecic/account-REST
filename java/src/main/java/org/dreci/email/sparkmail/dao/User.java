package org.drecic.email.sparkmail.dao;

import java.sql.Timestamp;
import java.util.UUID;

public class User {
	
	private UUID id;
	private Timestamp createdAt;
	private String username;
	private String recoveryPhone;
	private String recoveryEmail;
	private boolean phoneVerified;
	private boolean emailVerified;
	private Timestamp disabledAt;
	
	public User() {
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRecoveryPhone() {
		return recoveryPhone;
	}

	public void setRecoveryPhone(String recoveryPhone) {
		this.recoveryPhone = recoveryPhone;
	}

	public String getRecoveryEmail() {
		return recoveryEmail;
	}

	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Timestamp getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(Timestamp disabledAt) {
		this.disabledAt = disabledAt;
	}

	@Override
	public String toString() {
		return "\nUser\n\tid = " + id + "\n\tusername = " + username + "\n";
	}	
}
