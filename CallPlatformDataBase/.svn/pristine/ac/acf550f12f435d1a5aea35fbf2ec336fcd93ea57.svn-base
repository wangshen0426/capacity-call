package com.cqut.service.myGroupesList.customInterface;

import java.util.List;
import java.util.Map;

import com.cqut.entity.myGroupesList.MyGroupesList;

public interface IMyGroupesListService {

	/**
	 * 查询指定属性的值
	 * 
	 * @param properties 查询的属性
	 * @param condition 查询限制条件
	 * @param needLink 是否外键链接
	 * @param curPage 当前显示的页数
	 * @param limit 每页显示数量
	 * @return
	 */
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit);
	
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink);
	
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink);
			
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink);

	public List<MyGroupesList> findMyGroupesListByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit);
	
	public Map<String, Object> getMyGroupesList(String[] properties,
			String condition, boolean needLink);

	public MyGroupesList getMyGroupesListEntity(String[] properties,
			String condition, boolean needLink);
	
	public boolean save(Map<String, Object> value);

	public boolean saveEntity(MyGroupesList value);

	public Map<String, Object> saveAndReturn(Map<String, Object> value);

	public Map<String, Object> saveAndReturn(MyGroupesList value);

	public boolean deleteById(String id);

	public boolean deleteByEntity(MyGroupesList value);

	public boolean deleteByIds(String[] ids);

	public boolean deleteByEntitys(MyGroupesList[] values);
	
	public boolean updateEntity(MyGroupesList data, String condition);
}
