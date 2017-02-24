package com.cqut.service.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.user.customInterface.IUserEntityDao;
import com.cqut.dao.user.customInterface.IUserMapDao;
import com.cqut.entity.user.User;

import com.cqut.service.user.customInterface.IUserService;

@Controller  
@RemoteProxy
public class UserService implements IUserService {

	@Resource(name = "userMapDao")
	private IUserMapDao mapDao;
	@Resource(name = "userEntityDao")
	private IUserEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findUsers(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findUsers(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findUsers(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getUser(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findUsers(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public User getUserEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findUsers(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new User(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<User> findUserByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findUsers(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(User.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(User value) {
		return deleteById(value.getUserId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(User.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(User[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (User item : values) {
			ids[index++] = item.getUserId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new User(value));
	}

	@RemoteMethod
	public boolean saveEntity(User value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new User(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(User value) {
		User user = (User) commonDao.merge(value);
		if (user != null) {
			return user.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(User data, String condition) {
		if(mapDao.updateUser(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
