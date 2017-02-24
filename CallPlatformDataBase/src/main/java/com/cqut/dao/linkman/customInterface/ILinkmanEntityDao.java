package com.cqut.dao.linkman.customInterface;

import java.util.List;

import com.cqut.entity.linkman.Linkman;

public interface ILinkmanEntityDao {

	public List<Linkman> findLinkmans (String[] property,
			String condition, boolean needLink, int index, int limit);
}
