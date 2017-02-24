package com.cqut.service.informationes;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.informationes.customInterface.IInformationesEntityDao;
import com.cqut.dao.informationes.customInterface.IInformationesMapDao;
import com.cqut.entity.informationes.Informationes;

import com.cqut.service.informationes.customInterface.IInformationesService;

@Controller  
@RemoteProxy
public class InformationesService implements IInformationesService {

	@Resource(name = "informationesMapDao")
	private IInformationesMapDao mapDao;
	@Resource(name = "informationesEntityDao")
	private IInformationesEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findInformationess(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findInformationess(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findInformationess(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getInformationes(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findInformationess(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public Informationes getInformationesEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findInformationess(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new Informationes(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<Informationes> findInformationesByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findInformationess(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(Informationes.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(Informationes value) {
		return deleteById(value.getInformationesId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(Informationes.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(Informationes[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (Informationes item : values) {
			ids[index++] = item.getInformationesId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new Informationes(value));
	}

	@RemoteMethod
	public boolean saveEntity(Informationes value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new Informationes(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Informationes value) {
		Informationes informationes = (Informationes) commonDao.merge(value);
		if (informationes != null) {
			return informationes.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(Informationes data, String condition) {
		if(mapDao.updateInformationes(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
