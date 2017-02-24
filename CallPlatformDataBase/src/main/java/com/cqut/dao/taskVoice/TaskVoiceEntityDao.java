package com.cqut.dao.taskVoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.taskVoice.TaskVoice;

import com.cqut.dao.taskVoice.customInterface.ITaskVoiceEntityDao;

@Component
public class TaskVoiceEntityDao extends BaseDao implements ITaskVoiceEntityDao {

	@Override
	public Class<?> getEntity() {
		return TaskVoice.class;
	}
	
	public List<TaskVoice> findTaskVoices(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<TaskVoice> results = new ArrayList<TaskVoice>(list.size());
		for(Map<String, Object> item : list){
			results.add(new TaskVoice(item));
		}
		return results;
	}

}
