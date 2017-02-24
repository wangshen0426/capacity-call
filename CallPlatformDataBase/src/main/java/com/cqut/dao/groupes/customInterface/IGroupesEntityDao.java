package com.cqut.dao.groupes.customInterface;

import java.util.List;

import com.cqut.entity.groupes.Groupes;

public interface IGroupesEntityDao {

	public List<Groupes> findGroupess (String[] property,
			String condition, boolean needLink, int index, int limit);
}
