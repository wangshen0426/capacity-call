package com.cqut.service.callTask;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.callTask.customInterface.ICallTaskEntityDao;
import com.cqut.dao.callTask.customInterface.ICallTaskMapDao;
import com.cqut.entity.callTask.CallTask;

import com.cqut.service.callTask.customInterface.ICallTaskService;

@Controller  
@RemoteProxy
public class CallTaskService implements ICallTaskService {

	@Resource(name = "callTaskMapDao")
	private ICallTaskMapDao mapDao;
	@Resource(name = "callTaskEntityDao")
	private ICallTaskEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findCallTasks(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findCallTasks(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findCallTasks(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getCallTask(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findCallTasks(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public CallTask getCallTaskEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findCallTasks(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new CallTask(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<CallTask> findCallTaskByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findCallTasks(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(CallTask.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(CallTask value) {
		return deleteById(value.getCallTaskId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(CallTask.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(CallTask[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (CallTask item : values) {
			ids[index++] = item.getCallTaskId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new CallTask(value));
	}

	@RemoteMethod
	public boolean saveEntity(CallTask value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new CallTask(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(CallTask value) {
		CallTask callTask = (CallTask) commonDao.merge(value);
		if (callTask != null) {
			return callTask.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(CallTask data, String condition) {
		if(mapDao.updateCallTask(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
