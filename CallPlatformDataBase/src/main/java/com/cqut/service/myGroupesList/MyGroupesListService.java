package com.cqut.service.myGroupesList;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.myGroupesList.customInterface.IMyGroupesListEntityDao;
import com.cqut.dao.myGroupesList.customInterface.IMyGroupesListMapDao;
import com.cqut.entity.myGroupesList.MyGroupesList;

import com.cqut.service.myGroupesList.customInterface.IMyGroupesListService;

@Controller  
@RemoteProxy
public class MyGroupesListService implements IMyGroupesListService {

	@Resource(name = "myGroupesListMapDao")
	private IMyGroupesListMapDao mapDao;
	@Resource(name = "myGroupesListEntityDao")
	private IMyGroupesListEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findMyGroupesLists(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findMyGroupesLists(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findMyGroupesLists(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getMyGroupesList(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findMyGroupesLists(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public MyGroupesList getMyGroupesListEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findMyGroupesLists(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new MyGroupesList(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<MyGroupesList> findMyGroupesListByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findMyGroupesLists(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(MyGroupesList.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(MyGroupesList value) {
		return deleteById(value.getMyGroupesListId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(MyGroupesList.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(MyGroupesList[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (MyGroupesList item : values) {
			ids[index++] = item.getMyGroupesListId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new MyGroupesList(value));
	}

	@RemoteMethod
	public boolean saveEntity(MyGroupesList value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new MyGroupesList(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(MyGroupesList value) {
		MyGroupesList myGroupesList = (MyGroupesList) commonDao.merge(value);
		if (myGroupesList != null) {
			return myGroupesList.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(MyGroupesList data, String condition) {
		if(mapDao.updateMyGroupesList(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
