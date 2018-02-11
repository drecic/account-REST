package org.drecic.email.sparkmail.dao;

import java.util.List;
import java.util.UUID;

public interface DeviceDao {
	List<Device> findAll();
	List<Device> findByUserId(UUID userId);
	Device findByToken(String token);
	Device findByName(String name);
	
	public UUID insertDevice(Device toInsert, String token);
	public void updateDevice(Device toUpdate);
	public void deleteDevice(Device toDelete);
}
