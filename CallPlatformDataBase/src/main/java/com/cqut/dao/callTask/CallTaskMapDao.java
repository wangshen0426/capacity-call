package com.cqut.dao.callTask;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.callTask.CallTask;

import com.cqut.dao.callTask.customInterface.ICallTaskMapDao;

@Component
public class CallTaskMapDao extends BaseDao implements ICallTaskMapDao {

	
	public Class<?> getEntity() {
		return CallTask.class;
	}

	public List<Map<String, Object>> findCallTasks(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateCallTask(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}