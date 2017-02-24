package com.cqut.dao.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.user.User;

import com.cqut.dao.user.customInterface.IUserMapDao;

@Component
public class UserMapDao extends BaseDao implements IUserMapDao {

	
	public Class<?> getEntity() {
		return User.class;
	}

	public List<Map<String, Object>> findUsers(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateUser(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}