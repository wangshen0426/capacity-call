package com.cqut.dao.callTaskKey;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.callTaskKey.CallTaskKey;

import com.cqut.dao.callTaskKey.customInterface.ICallTaskKeyMapDao;

@Component
public class CallTaskKeyMapDao extends BaseDao implements ICallTaskKeyMapDao {

	
	public Class<?> getEntity() {
		return CallTaskKey.class;
	}

	public List<Map<String, Object>> findCallTaskKeys(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateCallTaskKey(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}