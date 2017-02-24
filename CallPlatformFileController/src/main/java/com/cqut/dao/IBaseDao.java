package com.cqut.dao;

import java.util.List;
import java.util.Map;


public interface IBaseDao extends HibernateDao{
	
	
	/**
	 * 查询数据
	 * @param entity 要查询的实体
	 * @param property 有参照实体的可以用.来连接（如parent.name）
	 * @param codition sql中的where部分
	 * @param needLink 是否自动连接
	 * @param index 起始位置
	 * @param limit 限制条数
	 * @return
	 */
	public List<Map<String, Object>> get(String[] properties, String condition, boolean needLink,int index, int limit);
	
	/**
	 * 查询数据(带排序)
	 * @param property
	 * @param condition
	 * @param sortField 排序的字段
	 * @param order 排序的类型 ASC DESC
	 * @param needLink
	 * @param index
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> get(String[] properties, String condition, String sortField, String order, boolean needLink,int index, int limit);
	
	/**
	 * 简洁查询
	 * @param properties 要查询的属性
	 * @param condition 查询的条件
	 * @param needLink 是否连接
	 * @return
	 */
	public List<Map<String, Object>> get(String[] properties, String condition, boolean needLink);
	
	/**
	 * 查询数据条数
	 */
	public int getCount(String[] properties, String condition, boolean needLink);
	
	/**
	 * 更新数据
	 * @param data
	 * @param condition
	 * @return
	 */
	public int updateProperties(Map<String, Object> data, String condition);
	
}
