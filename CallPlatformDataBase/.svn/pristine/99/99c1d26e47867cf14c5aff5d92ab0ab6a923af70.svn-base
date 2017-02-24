package com.cqut.dao.callTask.customInterface;

import java.util.List;
import java.util.Map;

public interface ICallTaskMapDao {

	public List<Map<String, Object>> findCallTasks(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateCallTask(Map<String, Object> properties, String condition);
}
