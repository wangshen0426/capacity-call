package com.cqut.service.fileSystem;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.fileSystem.customInterface.IFileSystemEntityDao;
import com.cqut.dao.fileSystem.customInterface.IFileSystemMapDao;
import com.cqut.entity.fileSystem.FileSystem;

import com.cqut.service.fileSystem.customInterface.IFileSystemService;

@Controller  
@RemoteProxy
public class FileSystemService implements IFileSystemService {

	@Resource(name = "fileSystemMapDao")
	private IFileSystemMapDao mapDao;
	@Resource(name = "fileSystemEntityDao")
	private IFileSystemEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findFileSystems(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findFileSystems(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findFileSystems(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getFileSystem(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findFileSystems(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public FileSystem getFileSystemEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findFileSystems(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new FileSystem(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<FileSystem> findFileSystemByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findFileSystems(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(FileSystem.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(FileSystem value) {
		return deleteById(value.getFileId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(FileSystem.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(FileSystem[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (FileSystem item : values) {
			ids[index++] = item.getFileId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new FileSystem(value));
	}

	@RemoteMethod
	public boolean saveEntity(FileSystem value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new FileSystem(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(FileSystem value) {
		FileSystem fileSystem = (FileSystem) commonDao.merge(value);
		if (fileSystem != null) {
			return fileSystem.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(FileSystem data, String condition) {
		if(mapDao.updateFileSystem(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
