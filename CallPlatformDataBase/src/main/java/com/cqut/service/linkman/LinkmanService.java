package com.cqut.service.linkman;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.linkman.customInterface.ILinkmanEntityDao;
import com.cqut.dao.linkman.customInterface.ILinkmanMapDao;
import com.cqut.entity.linkman.Linkman;

import com.cqut.service.linkman.customInterface.ILinkmanService;

@Controller  
@RemoteProxy
public class LinkmanService implements ILinkmanService {

	@Resource(name = "linkmanMapDao")
	private ILinkmanMapDao mapDao;
	@Resource(name = "linkmanEntityDao")
	private ILinkmanEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findLinkmans(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findLinkmans(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findLinkmans(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getLinkman(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findLinkmans(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public Linkman getLinkmanEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findLinkmans(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new Linkman(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<Linkman> findLinkmanByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findLinkmans(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(Linkman.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(Linkman value) {
		return deleteById(value.getLinkmanId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(Linkman.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(Linkman[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (Linkman item : values) {
			ids[index++] = item.getLinkmanId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new Linkman(value));
	}

	@RemoteMethod
	public boolean saveEntity(Linkman value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new Linkman(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Linkman value) {
		Linkman linkman = (Linkman) commonDao.merge(value);
		if (linkman != null) {
			return linkman.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(Linkman data, String condition) {
		if(mapDao.updateLinkman(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
