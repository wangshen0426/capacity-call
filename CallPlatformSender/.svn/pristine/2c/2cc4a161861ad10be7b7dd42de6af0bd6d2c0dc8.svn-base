/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cqut.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Convenient super class for Hibernate-based data access objects.
 * 
 * <p>
 * Requires a {@link org.hibernate.SessionFactory} to be set, providing a
 * {@link org.springframework.orm.hibernate3.HibernateTemplate} based on it to
 * subclasses through the {@link #getHibernateTemplate()} method. Can
 * alternatively be initialized directly with a HibernateTemplate, in order to
 * reuse the latter's settings such as the SessionFactory, exception translator,
 * flush mode, etc.
 * 
 * <p>
 * This base class is mainly intended for HibernateTemplate usage but can also
 * be used when working with a Hibernate Session directly, for example when
 * relying on transactional Sessions. Convenience {@link #getSession} and
 * {@link #releaseSession} methods are provided for that usage style.
 * 
 * <p>
 * This class will create its own HibernateTemplate instance if a SessionFactory
 * is passed in. The "allowCreate" flag on that HibernateTemplate will be "true"
 * by default. A custom HibernateTemplate instance can be used through
 * overriding {@link #createHibernateTemplate}.
 * 
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setSessionFactory
 * @see #getHibernateTemplate
 * @see org.springframework.orm.hibernate3.HibernateTemplate
 */
public abstract class HibernateDaoSupportWithTemplate extends DaoSupport implements HibernateDao {

	private HibernateTemplate hibernateTemplate;

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Resource(name = "sessionFactory")
	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = createHibernateTemplate(sessionFactory);
	}

	/**
	 * Create a HibernateTemplate for the given SessionFactory. Only invoked if
	 * populating the DAO with a SessionFactory reference!
	 * <p>
	 * Can be overridden in subclasses to provide a HibernateTemplate instance
	 * with different configuration, or a custom HibernateTemplate subclass.
	 * 
	 * @param sessionFactory
	 *            the Hibernate SessionFactory to create a HibernateTemplate for
	 * @return the new HibernateTemplate instance
	 * @see #setSessionFactory
	 */
	protected HibernateTemplate createHibernateTemplate(
			SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#getSessionFactory()
	 */
	public final SessionFactory getSessionFactory() {
		return (this.hibernateTemplate != null ? this.hibernateTemplate
				.getSessionFactory() : null);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setHibernateTemplate(org.springframework.orm.hibernate3.HibernateTemplate)
	 */
	public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#getHibernateTemplate()
	 */
	public final HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	protected final void checkDaoConfig() {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException(
					"'sessionFactory' or 'hibernateTemplate' is required");
		}
	}

	/**
	 * Obtain a Hibernate Session, either from the current transaction or a new
	 * one. The latter is only allowed if the
	 * {@link org.springframework.orm.hibernate3.HibernateTemplate#setAllowCreate "allowCreate"}
	 * setting of this bean's {@link #setHibernateTemplate HibernateTemplate} is
	 * "true".
	 * <p>
	 * <b>Note that this is not meant to be invoked from HibernateTemplate code
	 * but rather just in plain Hibernate code.</b> Either rely on a
	 * thread-bound Session or use it in combination with
	 * {@link #releaseSession}.
	 * <p>
	 * In general, it is recommended to use HibernateTemplate, either with the
	 * provided convenience operations or with a custom HibernateCallback that
	 * provides you with a Session to work on. HibernateTemplate will care for
	 * all resource management and for proper exception conversion.
	 * 
	 * @return the Hibernate Session
	 * @throws DataAccessResourceFailureException
	 *             if the Session couldn't be created
	 * @throws IllegalStateException
	 *             if no thread-bound Session found and allowCreate=false
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory,
	 *      boolean)
	 */
	protected final Session getSession()
			throws DataAccessResourceFailureException, IllegalStateException {

		return getSession(this.hibernateTemplate.isAllowCreate());
	}

	/**
	 * Obtain a Hibernate Session, either from the current transaction or a new
	 * one. The latter is only allowed if "allowCreate" is true.
	 * <p>
	 * <b>Note that this is not meant to be invoked from HibernateTemplate code
	 * but rather just in plain Hibernate code.</b> Either rely on a
	 * thread-bound Session or use it in combination with
	 * {@link #releaseSession}.
	 * <p>
	 * In general, it is recommended to use
	 * {@link #getHibernateTemplate() HibernateTemplate}, either with the
	 * provided convenience operations or with a custom
	 * {@link org.springframework.orm.hibernate3.HibernateCallback} that
	 * provides you with a Session to work on. HibernateTemplate will care for
	 * all resource management and for proper exception conversion.
	 * 
	 * @param allowCreate
	 *            if a non-transactional Session should be created when no
	 *            transactional Session can be found for the current thread
	 * @return the Hibernate Session
	 * @throws DataAccessResourceFailureException
	 *             if the Session couldn't be created
	 * @throws IllegalStateException
	 *             if no thread-bound Session found and allowCreate=false
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory,
	 *      boolean)
	 */
	protected Session getSession(boolean allowCreate)
			throws DataAccessResourceFailureException, IllegalStateException {

		return (!allowCreate ? SessionFactoryUtils.getSession(
				getSessionFactory(), false) : SessionFactoryUtils.getSession(
				getSessionFactory(), this.hibernateTemplate
						.getEntityInterceptor(), this.hibernateTemplate
						.getJdbcExceptionTranslator()));
	}

	/**
	 * Convert the given HibernateException to an appropriate exception from the
	 * <code>org.springframework.dao</code> hierarchy. Will automatically
	 * detect wrapped SQLExceptions and convert them accordingly.
	 * <p>
	 * Delegates to the
	 * {@link org.springframework.orm.hibernate3.HibernateTemplate#convertHibernateAccessException}
	 * method of this DAO's HibernateTemplate.
	 * <p>
	 * Typically used in plain Hibernate code, in combination with
	 * {@link #getSession} and {@link #releaseSession}.
	 * 
	 * @param ex
	 *            HibernateException that occured
	 * @return the corresponding DataAccessException instance
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#convertHibernateAccessException
	 */
	protected final DataAccessException convertHibernateAccessException(
			HibernateException ex) {
		return this.hibernateTemplate.convertHibernateAccessException(ex);
	}

	/**
	 * Close the given Hibernate Session, created via this DAO's SessionFactory,
	 * if it isn't bound to the thread (i.e. isn't a transactional Session).
	 * <p>
	 * Typically used in plain Hibernate code, in combination with
	 * {@link #getSession} and {@link #convertHibernateAccessException}.
	 * 
	 * @param session
	 *            the Session to close
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#releaseSession
	 */
	protected final void releaseSession(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
	}

	// -----------------------------------------wrap for hibernateTempate

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setAllowCreate(boolean)
	 */
	public void setAllowCreate(boolean allowCreate) {
		this.hibernateTemplate.setAllowCreate(allowCreate);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#isAllowCreate()
	 */
	public boolean isAllowCreate() {
		return this.hibernateTemplate.isAllowCreate();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setAlwaysUseNewSession(boolean)
	 */
	public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
		this.hibernateTemplate.setAlwaysUseNewSession(alwaysUseNewSession);

	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#isAlwaysUseNewSession()
	 */
	public boolean isAlwaysUseNewSession() {
		return this.hibernateTemplate.isAlwaysUseNewSession();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setExposeNativeSession(boolean)
	 */
	public void setExposeNativeSession(boolean exposeNativeSession) {
		this.hibernateTemplate.setExposeNativeSession(exposeNativeSession);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#isExposeNativeSession()
	 */
	public boolean isExposeNativeSession() {
		return this.hibernateTemplate.isExposeNativeSession();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setCheckWriteOperations(boolean)
	 */
	public void setCheckWriteOperations(boolean checkWriteOperations) {
		this.hibernateTemplate.setCheckWriteOperations(checkWriteOperations);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#isCheckWriteOperations()
	 */
	public boolean isCheckWriteOperations() {
		return this.hibernateTemplate.isCheckWriteOperations();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setCacheQueries(boolean)
	 */
	public void setCacheQueries(boolean cacheQueries) {
		this.hibernateTemplate.setCacheQueries(cacheQueries);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#isCacheQueries()
	 */
	public boolean isCacheQueries() {
		return this.hibernateTemplate.isCacheQueries();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setQueryCacheRegion(java.lang.String)
	 */
	public void setQueryCacheRegion(String queryCacheRegion) {
		this.hibernateTemplate.setQueryCacheRegion(queryCacheRegion);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#getQueryCacheRegion()
	 */
	public String getQueryCacheRegion() {
		return this.hibernateTemplate.getQueryCacheRegion();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setFetchSize(int)
	 */
	public void setFetchSize(int fetchSize) {
		this.hibernateTemplate.setFetchSize(fetchSize);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#getFetchSize()
	 */
	public int getFetchSize() {
		return this.hibernateTemplate.getFetchSize();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#setMaxResults(int)
	 */
	public void setMaxResults(int maxResults) {
		this.hibernateTemplate.setMaxResults(maxResults);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#getMaxResults()
	 */
	public int getMaxResults() {
		return this.hibernateTemplate.getMaxResults();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#execute(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object execute(HibernateCallback action) throws DataAccessException {
		return this.hibernateTemplate.execute(action);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#executeFind(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	@SuppressWarnings("unchecked")
	public List executeFind(HibernateCallback action)
			throws DataAccessException {
		return this.hibernateTemplate.executeFind(action);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#execute(org.springframework.orm.hibernate3.HibernateCallback, boolean)
	 */
	@SuppressWarnings("deprecation")
	public Object execute(HibernateCallback action, boolean exposeNativeSession)
			throws DataAccessException {
		return this.hibernateTemplate.execute(action, exposeNativeSession);
	}

	// -------------------------------------------------------------------------
	// Convenience methods for loading individual objects
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#get(java.lang.Class, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public Object get(Class entityClass, Serializable id)
			throws DataAccessException {
		return this.hibernateTemplate.get(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#get(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	@SuppressWarnings("unchecked")
	public Object get(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException {
		return this.hibernateTemplate.get(entityClass, id, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#get(java.lang.String, java.io.Serializable)
	 */
	public Object get(String entityName, Serializable id)
			throws DataAccessException {
		return this.hibernateTemplate.get(entityName, id);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#get(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException {
		return this.hibernateTemplate.get(entityName, id, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#load(java.lang.Class, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public Object load(Class entityClass, Serializable id)
			throws DataAccessException {
		return this.hibernateTemplate.load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#load(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	@SuppressWarnings("unchecked")
	public Object load(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException {
		return this.hibernateTemplate.load(entityClass, id, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#load(java.lang.String, java.io.Serializable)
	 */
	public Object load(String entityName, Serializable id)
			throws DataAccessException {
		return this.hibernateTemplate.load(entityName, id);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#load(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException {
		return this.hibernateTemplate.load(entityName, id, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#loadAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List loadAll(final Class entityClass) throws DataAccessException {
		return this.hibernateTemplate.loadAll(entityClass);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#load(java.lang.Object, java.io.Serializable)
	 */
	public void load(final Object entity, final Serializable id)
			throws DataAccessException {
		this.hibernateTemplate.load(entity, id);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#refresh(java.lang.Object)
	 */
	public void refresh(final Object entity) throws DataAccessException {
		this.hibernateTemplate.refresh(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#refresh(java.lang.Object, org.hibernate.LockMode)
	 */
	public void refresh(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		this.hibernateTemplate.refresh(entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#contains(java.lang.Object)
	 */
	public boolean contains(final Object entity) throws DataAccessException {
		return this.hibernateTemplate.contains(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#evict(java.lang.Object)
	 */
	public void evict(final Object entity) throws DataAccessException {
		this.hibernateTemplate.evict(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) throws DataAccessException {
		this.hibernateTemplate.initialize(proxy);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#enableFilter(java.lang.String)
	 */
	public Filter enableFilter(String filterName) throws IllegalStateException {
		return this.hibernateTemplate.enableFilter(filterName);
	}

	// -------------------------------------------------------------------------
	// Convenience methods for storing individual objects
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#lock(java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		this.hibernateTemplate.lock(entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#lock(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException {
		this.hibernateTemplate.lock(entityName, entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#save(java.lang.Object)
	 */
	@Transactional()
	public Object save(final Object entity) throws DataAccessException {
		return this.hibernateTemplate.save(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#save(java.lang.String, java.lang.Object)
	 */
	@Transactional()
	public Serializable save(final String entityName, final Object entity)
			throws DataAccessException {
		return this.hibernateTemplate.save(entityName, entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#update(java.lang.Object)
	 */
	@Transactional()
	public void update(Object entity) throws DataAccessException {
		this.hibernateTemplate.update(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#update(java.lang.Object, org.hibernate.LockMode)
	 */
	@Transactional()
	public void update(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		this.hibernateTemplate.update(entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#update(java.lang.String, java.lang.Object)
	 */
	@Transactional()
	public void update(String entityName, Object entity)
			throws DataAccessException {
		this.hibernateTemplate.update(entityName, entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#update(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	@Transactional()
	public void update(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException {
		this.hibernateTemplate.update(entityName, entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#saveOrUpdate(java.lang.Object)
	 */
	@Transactional()
	public void saveOrUpdate(final Object entity) throws DataAccessException {
		this.hibernateTemplate.saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#saveOrUpdate(java.lang.String, java.lang.Object)
	 */
	@Transactional()
	public void saveOrUpdate(final String entityName, final Object entity)
			throws DataAccessException {
		this.hibernateTemplate.saveOrUpdate(entityName, entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#saveOrUpdateAll(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Transactional()
	public void saveOrUpdateAll(final Collection entities)
			throws DataAccessException {
		this.hibernateTemplate.saveOrUpdateAll(entities);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#replicate(java.lang.Object, org.hibernate.ReplicationMode)
	 */
	@Transactional()
	public void replicate(final Object entity,
			final ReplicationMode replicationMode) throws DataAccessException {
		this.hibernateTemplate.replicate(entity, replicationMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#replicate(java.lang.String, java.lang.Object, org.hibernate.ReplicationMode)
	 */
	@Transactional()
	public void replicate(final String entityName, final Object entity,
			final ReplicationMode replicationMode) throws DataAccessException {
		this.hibernateTemplate.replicate(entityName, entity, replicationMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#persist(java.lang.Object)
	 */
	@Transactional()
	public void persist(final Object entity) throws DataAccessException {
		this.hibernateTemplate.persist(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#persist(java.lang.String, java.lang.Object)
	 */
	@Transactional()
	public void persist(final String entityName, final Object entity)
			throws DataAccessException {
		this.hibernateTemplate.persist(entityName, entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#merge(java.lang.Object)
	 */
	@Transactional()
	public Object merge(final Object entity) throws DataAccessException {
		return this.hibernateTemplate.merge(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#merge(java.lang.String, java.lang.Object)
	 */
	@Transactional()
	public Object merge(final String entityName, final Object entity)
			throws DataAccessException {
		return this.hibernateTemplate.merge(entityName, entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#delete(java.lang.Object)
	 */
	@Transactional()
	public void delete(Object entity) throws DataAccessException {
		this.hibernateTemplate.delete(entity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#delete(java.lang.Object, org.hibernate.LockMode)
	 */
	@Transactional()
	public void delete(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		this.hibernateTemplate.delete(entity, lockMode);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#deleteAll(java.util.Collection)
	 */
	@Transactional()
	@SuppressWarnings("unchecked")
	public void deleteAll(final Collection entities) throws DataAccessException {
		this.hibernateTemplate.deleteAll(entities);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#flush()
	 */
	@Transactional()
	public void flush() throws DataAccessException {
		this.hibernateTemplate.flush();
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#clear()
	 */
	@Transactional()
	public void clear() throws DataAccessException {
		this.hibernateTemplate.clear();
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for HQL strings
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#find(java.lang.String)
	 */
	@Transactional()
	@SuppressWarnings("unchecked")
	public List find(String queryString) throws DataAccessException {
		return this.hibernateTemplate.find(queryString);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#find(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List find(String queryString, Object value)
			throws DataAccessException {
		return this.hibernateTemplate.find(queryString, value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#find(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List find(final String queryString, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.find(queryString, values);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedParam(String queryString, String paramName,
			Object value) {
		return this.hibernateTemplate.findByNamedParam(queryString, paramName,
				value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.findByNamedParam(queryString, paramNames,
				values);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByValueBean(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByValueBean(final String queryString, final Object valueBean)
			throws DataAccessException {
		return this.hibernateTemplate.findByValueBean(queryString, valueBean);
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for named queries
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQuery(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQuery(String queryName) throws DataAccessException {
		return this.hibernateTemplate.findByNamedQuery(queryName);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQuery(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQuery(String queryName, Object value)
			throws DataAccessException {
		return this.hibernateTemplate.findByNamedQuery(queryName, value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQuery(final String queryName, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.findByNamedQuery(queryName, values);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException {
		return this.hibernateTemplate.findByNamedQueryAndNamedParam(queryName,
				paramName, value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.findByNamedQueryAndNamedParam(queryName,
				paramNames, values);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByNamedQueryAndValueBean(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DataAccessException {
		return this.hibernateTemplate.findByNamedQueryAndValueBean(queryName,
				valueBean);
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for detached criteria
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException {
		return this.hibernateTemplate.findByCriteria(criteria);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults)
			throws DataAccessException {
		return this.hibernateTemplate.findByCriteria(criteria, firstResult,
				maxResults);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByExample(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByExample(Object exampleEntity) throws DataAccessException {
		return this.hibernateTemplate.findByExample(exampleEntity);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#findByExample(java.lang.Object, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List findByExample(final Object exampleEntity,
			final int firstResult, final int maxResults)
			throws DataAccessException {
		return this.hibernateTemplate.findByExample(exampleEntity);
	}

	// -------------------------------------------------------------------------
	// Convenience query methods for iteration and bulk updates/deletes
	// -------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#iterate(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterate(String queryString) throws DataAccessException {
		return this.hibernateTemplate.iterate(queryString);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#iterate(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterate(String queryString, Object value)
			throws DataAccessException {
		return this.iterate(queryString, value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#iterate(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterate(final String queryString, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.iterate(queryString, values);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#closeIterator(java.util.Iterator)
	 */
	@SuppressWarnings("unchecked")
	public void closeIterator(Iterator it) throws DataAccessException {
		this.hibernateTemplate.closeIterator(it);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#bulkUpdate(java.lang.String)
	 */
	public int bulkUpdate(String queryString) throws DataAccessException {
		return this.hibernateTemplate.bulkUpdate(queryString);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#bulkUpdate(java.lang.String, java.lang.Object)
	 */
	public int bulkUpdate(String queryString, Object value)
			throws DataAccessException {
		return this.hibernateTemplate.bulkUpdate(queryString, value);
	}

	/* (non-Javadoc)
	 * @see cn.simple.dao.base.HibernateDao#bulkUpdate(java.lang.String, java.lang.Object[])
	 */
	public int bulkUpdate(final String queryString, final Object[] values)
			throws DataAccessException {
		return this.hibernateTemplate.bulkUpdate(queryString, values);
	}

}
