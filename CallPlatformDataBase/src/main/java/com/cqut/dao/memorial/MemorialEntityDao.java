package com.cqut.dao.memorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.memorial.Memorial;

import com.cqut.dao.memorial.customInterface.IMemorialEntityDao;

@Component
public class MemorialEntityDao extends BaseDao implements IMemorialEntityDao {

	@Override
	public Class<?> getEntity() {
		return Memorial.class;
	}
	
	public List<Memorial> findMemorials(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<Memorial> results = new ArrayList<Memorial>(list.size());
		for(Map<String, Object> item : list){
			results.add(new Memorial(item));
		}
		return results;
	}

}
