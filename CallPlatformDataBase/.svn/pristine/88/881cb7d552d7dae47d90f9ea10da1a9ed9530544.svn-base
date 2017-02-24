package com.cqut.dao.sessionMan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.sessionMan.SessionMan;

import com.cqut.dao.sessionMan.customInterface.ISessionManMapDao;

@Component
public class SessionManMapDao extends BaseDao implements ISessionManMapDao {

	
	public Class<?> getEntity() {
		return SessionMan.class;
	}

	public List<Map<String, Object>> findSessionMans(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateSessionMan(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}