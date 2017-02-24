package com.cqut.dao.groupMember.customInterface;

import java.util.List;
import java.util.Map;

public interface IGroupMemberMapDao {

	public List<Map<String, Object>> findGroupMembers(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateGroupMember(Map<String, Object> properties, String condition);
}
