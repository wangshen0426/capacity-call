package com.cqut.dao.linkman.customInterface;

import java.util.List;
import java.util.Map;

public interface ILinkmanMapDao {

	public List<Map<String, Object>> findLinkmans(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateLinkman(Map<String, Object> properties, String condition);
}
