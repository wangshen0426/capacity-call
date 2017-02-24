package com.cqut.dao.informationes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.informationes.Informationes;

import com.cqut.dao.informationes.customInterface.IInformationesEntityDao;

@Component
public class InformationesEntityDao extends BaseDao implements IInformationesEntityDao {

	@Override
	public Class<?> getEntity() {
		return Informationes.class;
	}
	
	public List<Informationes> findInformationess(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<Informationes> results = new ArrayList<Informationes>(list.size());
		for(Map<String, Object> item : list){
			results.add(new Informationes(item));
		}
		return results;
	}

}
