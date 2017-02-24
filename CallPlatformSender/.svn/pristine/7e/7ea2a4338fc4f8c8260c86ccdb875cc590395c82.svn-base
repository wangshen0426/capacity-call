package com.cqut.dao.YZ_GroupCall;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.YZ_GroupCall.YZ_GroupCall;
import com.cqut.dao.YZ_GroupCall.customInterface.IGroupCallMapDao;

@Component
public class GroupCallMapDao extends BaseDao implements IGroupCallMapDao {

	
	public Class<?> getEntity() {
		return YZ_GroupCall.class;
	}

	public List<Map<String, Object>> findYZ_GroupCalls(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateYZ_GroupCall(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}