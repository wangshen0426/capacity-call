package com.cqut.dao.fileSystem;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.fileSystem.FileSystem;

import com.cqut.dao.fileSystem.customInterface.IFileSystemMapDao;

@Component
public class FileSystemMapDao extends BaseDao implements IFileSystemMapDao {

	
	public Class<?> getEntity() {
		return FileSystem.class;
	}

	public List<Map<String, Object>> findFileSystems(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateFileSystem(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}