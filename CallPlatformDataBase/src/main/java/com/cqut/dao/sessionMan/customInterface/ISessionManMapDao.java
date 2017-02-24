package com.cqut.dao.sessionMan.customInterface;

import java.util.List;
import java.util.Map;

public interface ISessionManMapDao {

	public List<Map<String, Object>> findSessionMans(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateSessionMan(Map<String, Object> properties, String condition);
}
