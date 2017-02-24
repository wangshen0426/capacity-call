package com.cqut.dao.memorial.customInterface;

import java.util.List;
import java.util.Map;

public interface IMemorialMapDao {

	public List<Map<String, Object>> findMemorials(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateMemorial(Map<String, Object> properties, String condition);
}
