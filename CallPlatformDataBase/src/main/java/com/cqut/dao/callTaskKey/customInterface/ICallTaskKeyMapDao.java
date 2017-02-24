package com.cqut.dao.callTaskKey.customInterface;

import java.util.List;
import java.util.Map;

public interface ICallTaskKeyMapDao {

	public List<Map<String, Object>> findCallTaskKeys(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateCallTaskKey(Map<String, Object> properties, String condition);
}
