package com.cqut.dao.informationes;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.informationes.Informationes;

import com.cqut.dao.informationes.customInterface.IInformationesMapDao;

@Component
public class InformationesMapDao extends BaseDao implements IInformationesMapDao {

	
	public Class<?> getEntity() {
		return Informationes.class;
	}

	public List<Map<String, Object>> findInformationess(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateInformationes(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}