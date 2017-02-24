package com.cqut.dao.groupes;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.groupes.Groupes;

import com.cqut.dao.groupes.customInterface.IGroupesMapDao;

@Component
public class GroupesMapDao extends BaseDao implements IGroupesMapDao {

	
	public Class<?> getEntity() {
		return Groupes.class;
	}

	public List<Map<String, Object>> findGroupess(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateGroupes(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}