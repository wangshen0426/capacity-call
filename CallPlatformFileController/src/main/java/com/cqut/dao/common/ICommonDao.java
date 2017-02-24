package com.cqut.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.type.NullableType;
import org.hibernate.type.Type;

import com.cqut.dao.HibernateDao;

/**
 * 公共DAO接口
 * @author cy
 *
 */
public interface ICommonDao extends HibernateDao{
	public abstract <T> void delete(Class<T> entity, Serializable entityId);
	public abstract <T> void delete(Class<T> entity, Serializable[] entityIds);
	public abstract <T> T findById(Class<T> entity, Serializable entityId);
	/**
	 * 执行SQL
	 * @param sql
	 */
	public int execute(String sql);
	/**
	 * 给流程执行sql使用
	 * @param sql
	 * @param argm
	 * @param flag 未使用
	 * @return
	 */
	public int executeForFlow(final String sql, String argm, String flag);
	/**
	 * 执行SQL并返回
	 * @param sql
	 * @return
	 */
	public List<Object> executeAndReturn(String sql);
	/**
	 * 执行HQL
	 * @param sql
	 */
	public int executeHql(String hql);
	/**
	 * 执行HQL并返回
	 * @param hql
	 * @return
	 */
	public List<Object> executeHqlAndReturn(String hql);
	/**
	 * 在hibernate中使用sql的函数来查询需要特殊处理，
	 * 如SELECT count(*)，这个count函数就需要一个别名来处理，如count(*) AS e，然后给e一个hibernate的type
	 * @param sql
	 * @param scalar
	 * @return
	 */
	public List<Object> findSQL(String sql, Map<String , NullableType> scalar);
}
