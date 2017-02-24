package com.cqut.dao.fileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.fileSystem.FileSystem;

import com.cqut.dao.fileSystem.customInterface.IFileSystemEntityDao;

@Component
public class FileSystemEntityDao extends BaseDao implements IFileSystemEntityDao {

	@Override
	public Class<?> getEntity() {
		return FileSystem.class;
	}
	
	public List<FileSystem> findFileSystems(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<FileSystem> results = new ArrayList<FileSystem>(list.size());
		for(Map<String, Object> item : list){
			results.add(new FileSystem(item));
		}
		return results;
	}

}
