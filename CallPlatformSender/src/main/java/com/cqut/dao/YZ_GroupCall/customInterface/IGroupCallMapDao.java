package com.cqut.dao.YZ_GroupCall.customInterface;

import java.util.List;
import java.util.Map;

public interface IGroupCallMapDao {

	public List<Map<String, Object>> findYZ_GroupCalls(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateYZ_GroupCall(Map<String, Object> properties, String condition);
}
