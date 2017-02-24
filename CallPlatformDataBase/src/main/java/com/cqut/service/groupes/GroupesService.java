package com.cqut.service.groupes;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.groupes.customInterface.IGroupesEntityDao;
import com.cqut.dao.groupes.customInterface.IGroupesMapDao;
import com.cqut.entity.groupes.Groupes;

import com.cqut.service.groupes.customInterface.IGroupesService;

@Controller  
@RemoteProxy
public class GroupesService implements IGroupesService {

	@Resource(name = "groupesMapDao")
	private IGroupesMapDao mapDao;
	@Resource(name = "groupesEntityDao")
	private IGroupesEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findGroupess(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findGroupess(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findGroupess(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getGroupes(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findGroupess(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public Groupes getGroupesEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findGroupess(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new Groupes(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<Groupes> findGroupesByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findGroupess(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(Groupes.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(Groupes value) {
		return deleteById(value.getGroupId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(Groupes.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(Groupes[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (Groupes item : values) {
			ids[index++] = item.getGroupId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new Groupes(value));
	}

	@RemoteMethod
	public boolean saveEntity(Groupes value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new Groupes(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Groupes value) {
		Groupes groupes = (Groupes) commonDao.merge(value);
		if (groupes != null) {
			return groupes.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(Groupes data, String condition) {
		if(mapDao.updateGroupes(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
