package com.cqut.dao.callTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.callTask.CallTask;

import com.cqut.dao.callTask.customInterface.ICallTaskEntityDao;

@Component
public class CallTaskEntityDao extends BaseDao implements ICallTaskEntityDao {

	@Override
	public Class<?> getEntity() {
		return CallTask.class;
	}
	
	public List<CallTask> findCallTasks(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<CallTask> results = new ArrayList<CallTask>(list.size());
		for(Map<String, Object> item : list){
			results.add(new CallTask(item));
		}
		return results;
	}

}
