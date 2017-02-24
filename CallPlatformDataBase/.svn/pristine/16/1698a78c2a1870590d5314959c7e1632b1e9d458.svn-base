package com.cqut.dao.taskVoice.customInterface;

import java.util.List;
import java.util.Map;

public interface ITaskVoiceMapDao {

	public List<Map<String, Object>> findTaskVoices(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateTaskVoice(Map<String, Object> properties, String condition);
}
