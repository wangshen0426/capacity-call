package com.cqut.service.callTaskKey;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.callTaskKey.customInterface.ICallTaskKeyEntityDao;
import com.cqut.dao.callTaskKey.customInterface.ICallTaskKeyMapDao;
import com.cqut.entity.callTaskKey.CallTaskKey;

import com.cqut.service.callTaskKey.customInterface.ICallTaskKeyService;

@Controller  
@RemoteProxy
public class CallTaskKeyService implements ICallTaskKeyService {

	@Resource(name = "callTaskKeyMapDao")
	private ICallTaskKeyMapDao mapDao;
	@Resource(name = "callTaskKeyEntityDao")
	private ICallTaskKeyEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findCallTaskKeys(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findCallTaskKeys(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findCallTaskKeys(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getCallTaskKey(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findCallTaskKeys(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public CallTaskKey getCallTaskKeyEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findCallTaskKeys(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new CallTaskKey(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<CallTaskKey> findCallTaskKeyByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findCallTaskKeys(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(CallTaskKey.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(CallTaskKey value) {
		return deleteById(value.getTaskKeyId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(CallTaskKey.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(CallTaskKey[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (CallTaskKey item : values) {
			ids[index++] = item.getTaskKeyId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new CallTaskKey(value));
	}

	@RemoteMethod
	public boolean saveEntity(CallTaskKey value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new CallTaskKey(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(CallTaskKey value) {
		CallTaskKey callTaskKey = (CallTaskKey) commonDao.merge(value);
		if (callTaskKey != null) {
			return callTaskKey.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(CallTaskKey data, String condition) {
		if(mapDao.updateCallTaskKey(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
