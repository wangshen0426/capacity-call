package com.cqut.dao.memorial;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.memorial.Memorial;

import com.cqut.dao.memorial.customInterface.IMemorialMapDao;

@Component
public class MemorialMapDao extends BaseDao implements IMemorialMapDao {

	
	public Class<?> getEntity() {
		return Memorial.class;
	}

	public List<Map<String, Object>> findMemorials(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateMemorial(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}