package org.drecic.email.sparkmail.dao;

import java.sql.Timestamp;
import java.util.UUID;

public class Device {
	
	private UUID id;
	private UUID userId;
	private Timestamp issued;
	private Timestamp expires;
	private Timestamp lastRefresh;
	private String name;
	
	public Device() {
		
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public Timestamp getIssued() {
		return issued;
	}
	public void setIssued(Timestamp issued) {
		this.issued = issued;
	}
	public Timestamp getExpires() {
		return expires;
	}
	public void setExpires(Timestamp expires) {
		this.expires = expires;
	}
	public Timestamp getLastRefresh() {
		return lastRefresh;
	}
	public void setLastRefresh(Timestamp lastRefresh) {
		this.lastRefresh = lastRefresh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
