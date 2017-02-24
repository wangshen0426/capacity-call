package com.cqut.dao.informationes.customInterface;

import java.util.List;
import java.util.Map;

public interface IInformationesMapDao {

	public List<Map<String, Object>> findInformationess(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateInformationes(Map<String, Object> properties, String condition);
}
