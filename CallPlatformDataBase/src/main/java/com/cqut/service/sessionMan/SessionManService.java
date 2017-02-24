package com.cqut.service.sessionMan;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.sessionMan.customInterface.ISessionManEntityDao;
import com.cqut.dao.sessionMan.customInterface.ISessionManMapDao;
import com.cqut.entity.sessionMan.SessionMan;

import com.cqut.service.sessionMan.customInterface.ISessionManService;

@Controller  
@RemoteProxy
public class SessionManService implements ISessionManService {

	@Resource(name = "sessionManMapDao")
	private ISessionManMapDao mapDao;
	@Resource(name = "sessionManEntityDao")
	private ISessionManEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findSessionMans(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findSessionMans(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findSessionMans(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getSessionMan(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findSessionMans(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public SessionMan getSessionManEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findSessionMans(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new SessionMan(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<SessionMan> findSessionManByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findSessionMans(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(SessionMan.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(SessionMan value) {
		return deleteById(value.getSessionManId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(SessionMan.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(SessionMan[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (SessionMan item : values) {
			ids[index++] = item.getSessionManId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new SessionMan(value));
	}

	@RemoteMethod
	public boolean saveEntity(SessionMan value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new SessionMan(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(SessionMan value) {
		SessionMan sessionMan = (SessionMan) commonDao.merge(value);
		if (sessionMan != null) {
			return sessionMan.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(SessionMan data, String condition) {
		if(mapDao.updateSessionMan(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
