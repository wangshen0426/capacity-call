package com.cqut.dao.linkman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.linkman.Linkman;

import com.cqut.dao.linkman.customInterface.ILinkmanEntityDao;

@Component
public class LinkmanEntityDao extends BaseDao implements ILinkmanEntityDao {

	@Override
	public Class<?> getEntity() {
		return Linkman.class;
	}
	
	public List<Linkman> findLinkmans(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<Linkman> results = new ArrayList<Linkman>(list.size());
		for(Map<String, Object> item : list){
			results.add(new Linkman(item));
		}
		return results;
	}

}
