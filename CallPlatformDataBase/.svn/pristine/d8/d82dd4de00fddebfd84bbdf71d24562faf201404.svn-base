package com.cqut.dao.user.customInterface;

import java.util.List;
import java.util.Map;

public interface IUserMapDao {

	public List<Map<String, Object>> findUsers(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateUser(Map<String, Object> properties, String condition);
}
