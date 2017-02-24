package com.cqut.dao.myGroupesList.customInterface;

import java.util.List;
import java.util.Map;

public interface IMyGroupesListMapDao {

	public List<Map<String, Object>> findMyGroupesLists(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateMyGroupesList(Map<String, Object> properties, String condition);
}
