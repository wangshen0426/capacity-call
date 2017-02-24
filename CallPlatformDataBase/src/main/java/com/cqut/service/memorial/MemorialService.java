package com.cqut.service.memorial;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.memorial.customInterface.IMemorialEntityDao;
import com.cqut.dao.memorial.customInterface.IMemorialMapDao;
import com.cqut.entity.memorial.Memorial;

import com.cqut.service.memorial.customInterface.IMemorialService;

@Controller  
@RemoteProxy
public class MemorialService implements IMemorialService {

	@Resource(name = "memorialMapDao")
	private IMemorialMapDao mapDao;
	@Resource(name = "memorialEntityDao")
	private IMemorialEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findMemorials(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findMemorials(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findMemorials(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getMemorial(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findMemorials(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public Memorial getMemorialEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findMemorials(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new Memorial(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<Memorial> findMemorialByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findMemorials(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(Memorial.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(Memorial value) {
		return deleteById(value.getMemorialId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(Memorial.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(Memorial[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (Memorial item : values) {
			ids[index++] = item.getMemorialId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new Memorial(value));
	}

	@RemoteMethod
	public boolean saveEntity(Memorial value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new Memorial(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Memorial value) {
		Memorial memorial = (Memorial) commonDao.merge(value);
		if (memorial != null) {
			return memorial.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(Memorial data, String condition) {
		if(mapDao.updateMemorial(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
