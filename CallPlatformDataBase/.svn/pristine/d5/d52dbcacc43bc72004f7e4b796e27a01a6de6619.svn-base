package com.cqut.dao.groupMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.groupMember.GroupMember;

import com.cqut.dao.groupMember.customInterface.IGroupMemberEntityDao;

@Component
public class GroupMemberEntityDao extends BaseDao implements IGroupMemberEntityDao {

	@Override
	public Class<?> getEntity() {
		return GroupMember.class;
	}
	
	public List<GroupMember> findGroupMembers(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<GroupMember> results = new ArrayList<GroupMember>(list.size());
		for(Map<String, Object> item : list){
			results.add(new GroupMember(item));
		}
		return results;
	}

}
