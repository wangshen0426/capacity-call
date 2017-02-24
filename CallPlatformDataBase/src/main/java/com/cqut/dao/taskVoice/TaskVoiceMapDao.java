package com.cqut.dao.taskVoice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.taskVoice.TaskVoice;

import com.cqut.dao.taskVoice.customInterface.ITaskVoiceMapDao;

@Component
public class TaskVoiceMapDao extends BaseDao implements ITaskVoiceMapDao {

	
	public Class<?> getEntity() {
		return TaskVoice.class;
	}

	public List<Map<String, Object>> findTaskVoices(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateTaskVoice(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}