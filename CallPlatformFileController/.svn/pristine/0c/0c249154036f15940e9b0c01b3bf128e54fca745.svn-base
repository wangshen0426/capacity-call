package com.cqut.dao.common;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.NullableType;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.AbstractEntity;
import com.cqut.entity.ITreeStructureEntity;

@Component
public class CommonDao extends BaseDao implements
		ICommonDao {

	public int execute(final String sql) {
		Object result = this.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				int result = query.executeUpdate();
				return result;
			}
		});
		return (Integer)result;
	}
	
	public int executeForFlow(final String sql, final String argm, String flag) {
		Object result = this.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				if(argm != null){
					query.setString(0, argm);
				}
				int result = query.executeUpdate();
				return result;
			}
		});
		return (Integer)result;
	}

	@SuppressWarnings("unchecked")
	public List<Object> executeAndReturn(final String sql) {
		List<Object> list = this.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				//
				return query.list();
			}
		});
		return list;
	}

	public int executeHql(final String hql) {
		Object result = this.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				int result = query.executeUpdate();
				return result;
			}
		});
		return (Integer)result;
	}

	@SuppressWarnings("unchecked")
	public List<Object> executeHqlAndReturn(final String sql) {
		List<Object> list = this.executeFind(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(sql);
				//
				return query.list();
			}
		});
		return list;
	}

	public <T> void delete(Class<T> entity, Serializable entityId) {
		Object obj = super.load(entity, entityId);
		try{
			super.delete(obj);
			if(obj instanceof ITreeStructureEntity){
				changeChildrenProperty(entity, false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public <T> void delete(Class<T> entity, Serializable[] entityIds) {
		for (Serializable entityId : entityIds) {
			this.delete(entity, entityId);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T findById(Class<T> entity, Serializable entityId) {
		return (T) super.get(entity, entityId);
	}
	
	@Override
	public Object merge(Object entity) throws DataAccessException {
		AbstractEntity ae = (AbstractEntity)entity;
		Object result = super.merge(ae);
		if(result instanceof ITreeStructureEntity && result != null){
			//处理树形结构的实体
			changeChildrenProperty(result, true);
		}
		return result;
	}
	
	/**
	 * 修改是否有子结点的字段
	 * @param entity
	 * @param type
	 */
	private void changeChildrenProperty(Object entity, boolean type){
		if(!(entity instanceof ITreeStructureEntity)){
			return;
		}
		if(type){
			doChildrenChange(entity, type);
		}else{
			ITreeStructureEntity tse = (ITreeStructureEntity)entity;
			String condition = tse.getParentPropertyName()+"="+tse.getParentPropertyValue();
			String hcpn = tse.getHasChildrenParpertyName();
			int count = super.getCount(new String[]{hcpn}, condition, false);
			if(count == 0){//没有子结点了
				doChildrenChange(entity, type);
			}
		}
	}
	
	private void doChildrenChange(Object entity, boolean type){
		ITreeStructureEntity tse = (ITreeStructureEntity)entity;
		String condition = tse.getRelationPropertiesName()+"="+tse.getParentPropertyValue();
		String hcpn = tse.getHasChildrenParpertyName();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(hcpn, type);
		updateProperties(entity.getClass(), data, condition);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findSQL(final String sql, final Map<String , NullableType> scalar) {
		List<Object> list = this.executeFind(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				Iterator<String> iterator = scalar.keySet().iterator();
				while(iterator.hasNext()){
					String temp = iterator.next();
					query = query.addScalar(temp, scalar.get(temp));
				}
				return query.list();
			}
		});
		return list;
	}

	@Override
	public Class<?> getEntity() {
		return this.getClass();
	}

}
