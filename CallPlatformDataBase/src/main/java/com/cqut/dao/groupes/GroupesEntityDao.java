package com.cqut.dao.groupes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.groupes.Groupes;

import com.cqut.dao.groupes.customInterface.IGroupesEntityDao;

@Component
public class GroupesEntityDao extends BaseDao implements IGroupesEntityDao {

	@Override
	public Class<?> getEntity() {
		return Groupes.class;
	}
	
	public List<Groupes> findGroupess(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<Groupes> results = new ArrayList<Groupes>(list.size());
		for(Map<String, Object> item : list){
			results.add(new Groupes(item));
		}
		return results;
	}

}
