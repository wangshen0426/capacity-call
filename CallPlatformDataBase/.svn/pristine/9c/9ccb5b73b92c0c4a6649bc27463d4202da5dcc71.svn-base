package com.cqut.dao.sessionMan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.sessionMan.SessionMan;

import com.cqut.dao.sessionMan.customInterface.ISessionManEntityDao;

@Component
public class SessionManEntityDao extends BaseDao implements ISessionManEntityDao {

	@Override
	public Class<?> getEntity() {
		return SessionMan.class;
	}
	
	public List<SessionMan> findSessionMans(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<SessionMan> results = new ArrayList<SessionMan>(list.size());
		for(Map<String, Object> item : list){
			results.add(new SessionMan(item));
		}
		return results;
	}

}
