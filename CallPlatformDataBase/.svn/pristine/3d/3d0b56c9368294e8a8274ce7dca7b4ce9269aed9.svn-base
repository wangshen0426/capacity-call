package com.cqut.dao.linkman;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.linkman.Linkman;

import com.cqut.dao.linkman.customInterface.ILinkmanMapDao;

@Component
public class LinkmanMapDao extends BaseDao implements ILinkmanMapDao {

	
	public Class<?> getEntity() {
		return Linkman.class;
	}

	public List<Map<String, Object>> findLinkmans(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateLinkman(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}