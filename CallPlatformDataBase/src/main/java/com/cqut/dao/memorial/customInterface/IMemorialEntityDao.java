package com.cqut.dao.memorial.customInterface;

import java.util.List;

import com.cqut.entity.memorial.Memorial;

public interface IMemorialEntityDao {

	public List<Memorial> findMemorials (String[] property,
			String condition, boolean needLink, int index, int limit);
}
