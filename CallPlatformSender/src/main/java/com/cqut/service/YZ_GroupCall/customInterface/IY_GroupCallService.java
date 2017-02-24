package com.cqut.service.YZ_GroupCall.customInterface;

import java.util.List;
import java.util.Map;

import com.cqut.entity.YZ_GroupCall.YZ_GroupCall;

public interface IY_GroupCallService {

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

	public List<YZ_GroupCall> findYZ_GroupCallByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit);
	
	public Map<String, Object> getYZ_GroupCall(String[] properties,
			String condition, boolean needLink);

	public YZ_GroupCall getYZ_GroupCallEntity(String[] properties,
			String condition, boolean needLink);
	
	public boolean save(Map<String, Object> value);

	public boolean saveEntity(YZ_GroupCall value);

	public Map<String, Object> saveAndReturn(Map<String, Object> value);

	public Map<String, Object> saveAndReturn(YZ_GroupCall value);

	public boolean deleteById(String id);

	public boolean deleteByEntity(YZ_GroupCall value);

	public boolean deleteByIds(String[] ids);

	public boolean deleteByEntitys(YZ_GroupCall[] values);
	
	public boolean updateEntity(YZ_GroupCall data, String condition);
	
	public Map<String, Object> selectNotReceipt(String[] str);
}
