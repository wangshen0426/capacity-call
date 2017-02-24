package com.cqut.service.groupMember;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.groupMember.customInterface.IGroupMemberEntityDao;
import com.cqut.dao.groupMember.customInterface.IGroupMemberMapDao;
import com.cqut.entity.groupMember.GroupMember;

import com.cqut.service.groupMember.customInterface.IGroupMemberService;

@Controller  
@RemoteProxy
public class GroupMemberService implements IGroupMemberService {

	@Resource(name = "groupMemberMapDao")
	private IGroupMemberMapDao mapDao;
	@Resource(name = "groupMemberEntityDao")
	private IGroupMemberEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findGroupMembers(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findGroupMembers(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findGroupMembers(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getGroupMember(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findGroupMembers(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public GroupMember getGroupMemberEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findGroupMembers(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new GroupMember(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<GroupMember> findGroupMemberByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findGroupMembers(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(GroupMember.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(GroupMember value) {
		return deleteById(value.getGroupMemberId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(GroupMember.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(GroupMember[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (GroupMember item : values) {
			ids[index++] = item.getGroupMemberId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new GroupMember(value));
	}

	@RemoteMethod
	public boolean saveEntity(GroupMember value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new GroupMember(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(GroupMember value) {
		GroupMember groupMember = (GroupMember) commonDao.merge(value);
		if (groupMember != null) {
			return groupMember.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(GroupMember data, String condition) {
		if(mapDao.updateGroupMember(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
