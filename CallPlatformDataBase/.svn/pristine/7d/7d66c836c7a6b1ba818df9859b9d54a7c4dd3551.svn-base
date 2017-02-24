package com.cqut.dao.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.user.User;

import com.cqut.dao.user.customInterface.IUserEntityDao;

@Component
public class UserEntityDao extends BaseDao implements IUserEntityDao {

	@Override
	public Class<?> getEntity() {
		return User.class;
	}
	
	public List<User> findUsers(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<User> results = new ArrayList<User>(list.size());
		for(Map<String, Object> item : list){
			results.add(new User(item));
		}
		return results;
	}

}
