package com.cqut.dao.myGroupesList;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.myGroupesList.MyGroupesList;

import com.cqut.dao.myGroupesList.customInterface.IMyGroupesListMapDao;

@Component
public class MyGroupesListMapDao extends BaseDao implements IMyGroupesListMapDao {

	
	public Class<?> getEntity() {
		return MyGroupesList.class;
	}

	public List<Map<String, Object>> findMyGroupesLists(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateMyGroupesList(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}