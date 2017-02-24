package com.cqut.dao.callTaskKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.callTaskKey.CallTaskKey;

import com.cqut.dao.callTaskKey.customInterface.ICallTaskKeyEntityDao;

@Component
public class CallTaskKeyEntityDao extends BaseDao implements ICallTaskKeyEntityDao {

	@Override
	public Class<?> getEntity() {
		return CallTaskKey.class;
	}
	
	public List<CallTaskKey> findCallTaskKeys(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<CallTaskKey> results = new ArrayList<CallTaskKey>(list.size());
		for(Map<String, Object> item : list){
			results.add(new CallTaskKey(item));
		}
		return results;
	}

}
