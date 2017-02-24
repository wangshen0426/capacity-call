package com.cqut.dao.YZ_GroupCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.dao.YZ_GroupCall.customInterface.IGroupCallEntityDao;
import com.cqut.entity.YZ_GroupCall.YZ_GroupCall;


@Component
public class GroupCallEntityDao extends BaseDao implements IGroupCallEntityDao {

	@Override
	public Class<?> getEntity() {
		return YZ_GroupCall.class;
	}
	
	public List<YZ_GroupCall> findYZ_GroupCalls(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<YZ_GroupCall> results = new ArrayList<YZ_GroupCall>(list.size());
		for(Map<String, Object> item : list){
			results.add(new YZ_GroupCall(item));
		}
		return results;
	}

}
