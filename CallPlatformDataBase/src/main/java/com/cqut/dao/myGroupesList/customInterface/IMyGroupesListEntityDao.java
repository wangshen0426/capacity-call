package com.cqut.dao.myGroupesList.customInterface;

import java.util.List;

import com.cqut.entity.myGroupesList.MyGroupesList;

public interface IMyGroupesListEntityDao {

	public List<MyGroupesList> findMyGroupesLists (String[] property,
			String condition, boolean needLink, int index, int limit);
}
