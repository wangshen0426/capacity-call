package com.cqut.service.taskVoice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.taskVoice.customInterface.ITaskVoiceEntityDao;
import com.cqut.dao.taskVoice.customInterface.ITaskVoiceMapDao;
import com.cqut.entity.taskVoice.TaskVoice;

import com.cqut.service.taskVoice.customInterface.ITaskVoiceService;

@Controller  
@RemoteProxy
public class TaskVoiceService implements ITaskVoiceService {

	@Resource(name = "taskVoiceMapDao")
	private ITaskVoiceMapDao mapDao;
	@Resource(name = "taskVoiceEntityDao")
	private ITaskVoiceEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findTaskVoices(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findTaskVoices(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findTaskVoices(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getTaskVoice(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findTaskVoices(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public TaskVoice getTaskVoiceEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findTaskVoices(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new TaskVoice(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<TaskVoice> findTaskVoiceByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findTaskVoices(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(TaskVoice.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(TaskVoice value) {
		return deleteById(value.getTaskVoiceId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(TaskVoice.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(TaskVoice[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (TaskVoice item : values) {
			ids[index++] = item.getTaskVoiceId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new TaskVoice(value));
	}

	@RemoteMethod
	public boolean saveEntity(TaskVoice value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new TaskVoice(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(TaskVoice value) {
		TaskVoice taskVoice = (TaskVoice) commonDao.merge(value);
		if (taskVoice != null) {
			return taskVoice.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(TaskVoice data, String condition) {
		if(mapDao.updateTaskVoice(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
