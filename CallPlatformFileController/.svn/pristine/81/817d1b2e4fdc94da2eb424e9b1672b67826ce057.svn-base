package com.cqut.dao.fileSystem.customInterface;

import java.util.List;
import java.util.Map;

public interface IFileSystemMapDao {

	public List<Map<String, Object>> findFileSystems(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateFileSystem(Map<String, Object> properties, String condition);
}
