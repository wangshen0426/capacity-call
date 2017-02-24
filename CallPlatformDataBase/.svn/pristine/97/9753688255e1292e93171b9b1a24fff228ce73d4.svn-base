package com.cqut.dao.groupMember;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.groupMember.GroupMember;

import com.cqut.dao.groupMember.customInterface.IGroupMemberMapDao;

@Component
public class GroupMemberMapDao extends BaseDao implements IGroupMemberMapDao {

	
	public Class<?> getEntity() {
		return GroupMember.class;
	}

	public List<Map<String, Object>> findGroupMembers(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateGroupMember(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}