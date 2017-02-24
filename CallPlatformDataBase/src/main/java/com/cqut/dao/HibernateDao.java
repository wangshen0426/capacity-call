package com.cqut.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Filter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

public interface HibernateDao {

	/**
	 * Set the Hibernate SessionFactory to be used by this DAO. Will
	 * automatically create a HibernateTemplate for the given SessionFactory.
	 * 
	 * @see #createHibernateTemplate
	 * @see #setHibernateTemplate
	 */
	@Resource(name = "sessionFactory")
	public abstract void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Return the Hibernate SessionFactory used by this DAO.
	 */
	public abstract SessionFactory getSessionFactory();

	/**
	 * Set the HibernateTemplate for this DAO explicitly, as an alternative to
	 * specifying a SessionFactory.
	 * 
	 * @see #setSessionFactory
	 */
	public abstract void setHibernateTemplate(
			HibernateTemplate hibernateTemplate);

	/**
	 * Return the HibernateTemplate for this DAO, pre-initialized with the
	 * SessionFactory or set explicitly.
	 * <p>
	 * <b>Note: The returned HibernateTemplate is a shared instance.</b> You
	 * may introspect its configuration, but not modify the configuration (other
	 * than from within an {@link #initDao} implementation). Consider creating a
	 * custom HibernateTemplate instance via
	 * <code>new HibernateTemplate(getSessionFactory())</code>, in which case
	 * you're allowed to customize the settings on the resulting instance.
	 */
	public abstract HibernateTemplate getHibernateTemplate();

	/**
	 * Set if a new {@link Session} should be created when no transactional
	 * <code>Session</code> can be found for the current thread. The default
	 * value is "<code>true</code>".
	 * <p>
	 * <code>HibernateTemplate</code> is aware of a corresponding
	 * <code>Session</code> bound to the current thread, for example when
	 * using {@link HibernateTransactionManager}. If <code>allowCreate</code>
	 * is <code>true</code>, a new non-transactional <code>Session</code>
	 * will be created if none is found, which needs to be closed at the end of
	 * the operation. If <code>false</code>, an {@link IllegalStateException}
	 * will get thrown in this case.
	 * 
	 * @see SessionFactoryUtils#getSession(SessionFactory, boolean)
	 */
	public abstract void setAllowCreate(boolean allowCreate);

	/**
	 * Return if a new Session should be created if no thread-bound found.
	 */
	public abstract boolean isAllowCreate();

	/**
	 * Set whether to always use a new Hibernate Session for this template.
	 * Default is "false"; if activated, all operations on this template will
	 * work on a new Hibernate Session even in case of a pre-bound Session (for
	 * example, within a transaction or OpenSessionInViewFilter).
	 * <p>
	 * Within a transaction, a new Hibernate Session used by this template will
	 * participate in the transaction through using the same JDBC Connection. In
	 * such a scenario, multiple Sessions will participate in the same database
	 * transaction.
	 * <p>
	 * Turn this on for operations that are supposed to always execute
	 * independently, without side effects caused by a shared Hibernate Session.
	 */
	public abstract void setAlwaysUseNewSession(boolean alwaysUseNewSession);

	/**
	 * Return whether to always use a new Hibernate Session for this template.
	 */
	public abstract boolean isAlwaysUseNewSession();

	/**
	 * Set whether to expose the native Hibernate Session to HibernateCallback
	 * code.
	 * <p>
	 * Default is "false": a Session proxy will be returned, suppressing
	 * <code>close</code> calls and automatically applying query cache
	 * settings and transaction timeouts.
	 * 
	 * @see HibernateCallback
	 * @see org.hibernate.Session
	 * @see #setCacheQueries
	 * @see #setQueryCacheRegion
	 * @see #prepareQuery
	 * @see #prepareCriteria
	 */
	public abstract void setExposeNativeSession(boolean exposeNativeSession);

	/**
	 * Return whether to expose the native Hibernate Session to
	 * HibernateCallback code, or rather a Session proxy.
	 */
	public abstract boolean isExposeNativeSession();

	/**
	 * Set whether to check that the Hibernate Session is not in read-only mode
	 * in case of write operations (save/update/delete).
	 * <p>
	 * Default is "true", for fail-fast behavior when attempting write
	 * operations within a read-only transaction. Turn this off to allow
	 * save/update/delete on a Session with flush mode NEVER.
	 * 
	 * @see #setFlushMode
	 * @see #checkWriteOperationAllowed
	 * @see org.springframework.transaction.TransactionDefinition#isReadOnly
	 */
	public abstract void setCheckWriteOperations(boolean checkWriteOperations);

	/**
	 * Return whether to check that the Hibernate Session is not in read-only
	 * mode in case of write operations (save/update/delete).
	 */
	public abstract boolean isCheckWriteOperations();

	/**
	 * Set whether to cache all queries executed by this template.
	 * <p>
	 * If this is "true", all Query and Criteria objects created by this
	 * template will be marked as cacheable (including all queries through find
	 * methods).
	 * <p>
	 * To specify the query region to be used for queries cached by this
	 * template, set the "queryCacheRegion" property.
	 * 
	 * @see #setQueryCacheRegion
	 * @see org.hibernate.Query#setCacheable
	 * @see org.hibernate.Criteria#setCacheable
	 */
	public abstract void setCacheQueries(boolean cacheQueries);

	/**
	 * Return whether to cache all queries executed by this template.
	 */
	public abstract boolean isCacheQueries();

	/**
	 * Set the name of the cache region for queries executed by this template.
	 * <p>
	 * If this is specified, it will be applied to all Query and Criteria
	 * objects created by this template (including all queries through find
	 * methods).
	 * <p>
	 * The cache region will not take effect unless queries created by this
	 * template are configured to be cached via the "cacheQueries" property.
	 * 
	 * @see #setCacheQueries
	 * @see org.hibernate.Query#setCacheRegion
	 * @see org.hibernate.Criteria#setCacheRegion
	 */
	public abstract void setQueryCacheRegion(String queryCacheRegion);

	/**
	 * Return the name of the cache region for queries executed by this
	 * template.
	 */
	public abstract String getQueryCacheRegion();

	/**
	 * Set the fetch size for this HibernateTemplate. This is important for
	 * processing large result sets: Setting this higher than the default value
	 * will increase processing speed at the cost of memory consumption; setting
	 * this lower can avoid transferring row data that will never be read by the
	 * application.
	 * <p>
	 * Default is 0, indicating to use the JDBC driver's default.
	 */
	public abstract void setFetchSize(int fetchSize);

	/**
	 * Return the fetch size specified for this HibernateTemplate.
	 */
	public abstract int getFetchSize();

	/**
	 * Set the maximum number of rows for this HibernateTemplate. This is
	 * important for processing subsets of large result sets, avoiding to read
	 * and hold the entire result set in the database or in the JDBC driver if
	 * we're never interested in the entire result in the first place (for
	 * example, when performing searches that might return a large number of
	 * matches).
	 * <p>
	 * Default is 0, indicating to use the JDBC driver's default.
	 */
	public abstract void setMaxResults(int maxResults);

	/**
	 * Return the maximum number of rows specified for this HibernateTemplate.
	 */
	public abstract int getMaxResults();

	public abstract Object execute(HibernateCallback action)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List executeFind(HibernateCallback action)
			throws DataAccessException;

	/**
	 * Execute the action specified by the given action object within a Session.
	 * 
	 * @param action
	 *            callback object that specifies the Hibernate action
	 * @param exposeNativeSession
	 *            whether to expose the native Hibernate Session to callback
	 *            code
	 * @return a result object returned by the action, or <code>null</code>
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 */
	public abstract Object execute(HibernateCallback action,
			boolean exposeNativeSession) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Object get(Class entityClass, Serializable id)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Object get(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	public abstract Object get(String entityName, Serializable id)
			throws DataAccessException;

	public abstract Object get(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Object load(Class entityClass, Serializable id)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Object load(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	public abstract Object load(String entityName, Serializable id)
			throws DataAccessException;

	public abstract Object load(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List loadAll(final Class entityClass)
			throws DataAccessException;

	public abstract void load(final Object entity, final Serializable id)
			throws DataAccessException;

	public abstract void refresh(final Object entity)
			throws DataAccessException;

	public abstract void refresh(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	public abstract boolean contains(final Object entity)
			throws DataAccessException;

	public abstract void evict(final Object entity) throws DataAccessException;

	public abstract void initialize(Object proxy) throws DataAccessException;

	public abstract Filter enableFilter(String filterName)
			throws IllegalStateException;

	public abstract void lock(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	public abstract void lock(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException;

	public abstract Object save(final Object entity)
			throws DataAccessException;

	public abstract Serializable save(final String entityName,
			final Object entity) throws DataAccessException;

	public abstract void update(Object entity) throws DataAccessException;

	public abstract void update(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	public abstract void update(String entityName, Object entity)
			throws DataAccessException;

	public abstract void update(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException;

	public abstract void saveOrUpdate(final Object entity)
			throws DataAccessException;

	public abstract void saveOrUpdate(final String entityName,
			final Object entity) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract void saveOrUpdateAll(final Collection entities)
			throws DataAccessException;

	public abstract void replicate(final Object entity,
			final ReplicationMode replicationMode) throws DataAccessException;

	public abstract void replicate(final String entityName,
			final Object entity, final ReplicationMode replicationMode)
			throws DataAccessException;

	public abstract void persist(final Object entity)
			throws DataAccessException;

	public abstract void persist(final String entityName, final Object entity)
			throws DataAccessException;

	public abstract Object merge(final Object entity)
			throws DataAccessException;

	public abstract Object merge(final String entityName, final Object entity)
			throws DataAccessException;

	public abstract void delete(Object entity) throws DataAccessException;

	public abstract void delete(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract void deleteAll(final Collection entities)
			throws DataAccessException;

	public abstract void flush() throws DataAccessException;

	public abstract void clear() throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List find(String queryString) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List find(String queryString, Object value)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List find(final String queryString, final Object[] values)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedParam(String queryString, String paramName,
			Object value);

	@SuppressWarnings("unchecked")
	public abstract List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByValueBean(final String queryString,
			final Object valueBean) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQuery(String queryName)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQuery(String queryName, Object value)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQuery(final String queryName,
			final Object[] values) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByExample(Object exampleEntity)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List findByExample(final Object exampleEntity,
			final int firstResult, final int maxResults)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Iterator iterate(String queryString)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Iterator iterate(String queryString, Object value)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract Iterator iterate(final String queryString,
			final Object[] values) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract void closeIterator(Iterator it) throws DataAccessException;

	public abstract int bulkUpdate(String queryString)
			throws DataAccessException;

	public abstract int bulkUpdate(String queryString, Object value)
			throws DataAccessException;

	public abstract int bulkUpdate(final String queryString,
			final Object[] values) throws DataAccessException;

}