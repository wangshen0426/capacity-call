package com.cqut.dao.myGroupesList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.myGroupesList.MyGroupesList;

import com.cqut.dao.myGroupesList.customInterface.IMyGroupesListEntityDao;

@Component
public class MyGroupesListEntityDao extends BaseDao implements IMyGroupesListEntityDao {

	@Override
	public Class<?> getEntity() {
		return MyGroupesList.class;
	}
	
	public List<MyGroupesList> findMyGroupesLists(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<MyGroupesList> results = new ArrayList<MyGroupesList>(list.size());
		for(Map<String, Object> item : list){
			results.add(new MyGroupesList(item));
		}
		return results;
	}

}
