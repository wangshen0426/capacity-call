package com.cqut.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.cqut.entity.AbstractEntity;
import com.cqut.util.BeanUtil;
import com.cqut.util.StringUtil;
import com.cqut.util.entity.EntitySetting;
import com.cqut.util.entity.OneToManySetting;

public abstract class BaseDao extends HibernateDaoSupportWithTemplate implements
		IBaseDao {

	private String[] specialSql = new String[] { "order", "group" };

	public abstract Class<?> getEntity();

	@Override
	public Object merge(Object entity) throws DataAccessException {
		AbstractEntity ae = (AbstractEntity) entity;
		if (StringUtil.isEmpty(ae.getEntityKey())) {
			// 如果没有ID为他们统一添加
			ae.setEntityKey(BeanUtil.createId());
		}
		Object result = super.merge(ae);
		return result;
	}

	/**
	 * 获取数据 properties 需要获取的字段 condition 查询条件 needLink 是否需要链接外键 index
	 * 获取结果从第index条数据开始，如果不使用此属性，则设置为-1 limit 需要取出的记录数
	 */
	public List<Map<String, Object>> get(String[] properties, String condition,
			boolean needLink, int index, int limit) {
		List<String> propertiesName = new ArrayList<String>(properties.length);
		for (String name : properties) {
			propertiesName.add(name.replace("_", "."));
		}
		List<OneToManySetting> oms = new ArrayList<OneToManySetting>();
		String sql = createSQL(propertiesName, oms, condition, needLink, false);
		return loadData(sql, propertiesName, oms, index, limit);
	}

	public List<Map<String, Object>> get(String[] properties, String condition,
			boolean needLink) {
		List<String> propertiesName = new ArrayList<String>(properties.length);
		for (String name : properties) {
			propertiesName.add(name.replace("_", "."));
		}
		List<OneToManySetting> oms = new ArrayList<OneToManySetting>();
		String sql = createSQL(propertiesName, oms, condition, needLink, false);
		return loadData(sql, propertiesName, oms, -1, -1);
	}

	@SuppressWarnings("unchecked")
	public int getCount(String[] properties, String condition, boolean needLink) {
		List<String> propertiesName = new ArrayList<String>(properties.length);
		for (String name : properties) {
			propertiesName.add(name.replace("_", "."));
		}
		List<OneToManySetting> oms = new ArrayList<OneToManySetting>();
		final String sql = createSQL(propertiesName, oms, condition, needLink,
				true);
		try {
			Object objData = this.execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					SQLQuery query = session.createSQLQuery(sql).addScalar("e",
							Hibernate.INTEGER);
					List<Object> data = query.list();
					return data;
				}
			});
			// Session session = this.getSession();
			// SQLQuery query = session.createSQLQuery(sql).addScalar("e",
			// Hibernate.INTEGER);
			// List list = query.list();
			// session.close();
			List list = (List) objData;

			return list.size() > 0 ? Integer.parseInt(list.get(0).toString())
					: 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Map<String, Object>> get(String[] properties, String condition,
			String sortField, String order, boolean needLink, int index,
			int limit) {
		if (StringUtil.notEmpty(sortField) && StringUtil.notEmpty(order)) {
			// 处理排序
			condition += createSort(sortField, order, needLink);
		}
		return get(properties, condition, needLink, index, limit);
	}

	/**
	 * 生成排序SQL
	 * 
	 * @param sortField
	 *            排序的字段
	 * @param order
	 *            排序的类型
	 * @return
	 */
	private String createSort(String sortField, String order, boolean needLink) {
		String sortSQL = " ORDER BY ";
		String curEntityName = BeanUtil.getEntityName(getEntity());// 当前实体的名称
		if (sortField.indexOf(".") != -1 && needLink) {
			String[] temps = StringUtil.split(sortField, ".");
			int sum = temps.length;
			String classAlias = null;// 别名
			String preClassAlias = null;// 前一个的别名
			Map<String, EntitySetting> settings = null;
			EntitySetting curSetting = null;

			for (int i = 0; i < sum; i++) {
				if (i == sum - 1) {// 处理最后一个
					sortSQL += classAlias + "." + temps[i];
					break;
				} else {
					settings = BeanUtil.getEntitySettingByName(curEntityName);// 获取当前的所有配置

					// curSetting = findFieldSetting(settings,
					// temps[i]);//获取当前字段的配置
					curSetting = settings.get(temps[i]);// 获取当前字段的配置
					classAlias = preClassAlias
							+ "_"
							+ (curSetting.getClassAlias() != null ? curSetting
									.getClassAlias() : curSetting
									.getSourceEntity());

					// 判断是否是一对多情况
					if (curSetting.getLinkType().equals("1")) {
						// 添加查询字段
						sortSQL += preClassAlias + "."
								+ curSetting.getSourceFiled();
						break;
					}
					preClassAlias = classAlias;
				}
				curEntityName = curSetting.getTargetEntity();
			}
		} else {
			sortSQL += curEntityName + "." + sortField + " "
					+ order.toUpperCase();
		}
		return sqlSafeHandler(sortSQL);
	}

	private String sqlSafeHandler(String sql) {
		if (StringUtil.notEmpty(sql)) {
			return sql.replaceAll("(?i)update", "**update**").replaceAll(
					"(?!)delete", "**delete**");
		}
		return sql;
	}

	private String createSQL(List<String> properties,
			List<OneToManySetting> oms, String condition, boolean needLink,
			boolean isCount) {
		return createSQL(BeanUtil.getEntityName(getEntity()), properties, oms,
				condition, needLink, isCount);
	}

	/**
	 * 生成SQL
	 * 
	 * @param entityName
	 * @param properties
	 * @param oms
	 *            储存one to many 的List
	 * @param condition
	 * @param needLink
	 * @return
	 */
	private String createSQL(String entityName, List<String> properties,
			List<OneToManySetting> oms, String condition, boolean needLink,
			boolean isCount) {
		propertiesFielter(entityName, properties, needLink);// 处理*号

		String curEntityName;// 当前实体的名称

		Map<String, EntitySetting> settings = null;

		/**
		 * 查询的字段
		 */
		Set<String> queryProperties = new LinkedHashSet<String>();
		Set<String> queryTables = new HashSet<String>();
		Set<String> queryConditions = new HashSet<String>();

		EntitySetting curSetting = null;
		String[] temps = null;
		String classAlias = null;// 别名
		// String preClassAlias = null;//前一个的别名

		for (String item : properties) {
			if (item.indexOf(".") != -1 && needLink) {
				curEntityName = entityName;
				
				temps = StringUtil.split(item, ".");
				int sum = temps.length;
				// preClassAlias = null;
				for (int i = 0; i < sum; i++) {
					if (i == sum - 1) {// 处理最后一个
						queryProperties.add(classAlias + "." + temps[i]);
					} else {
						settings = BeanUtil
								.getEntitySettingByName(curEntityName);// 获取当前的所有配置
						curSetting = settings.get(temps[i]);// 获取当前字段的配置
						// 判断是否是一对多情况
						if (curSetting.getLinkType().equals("1")) {
							// 获取中间状态的输属性名称
							String mapSource = temps[i];
							// 获取用中间字段要查询的值
							String dbTarget = temps[i + 1];
							for (int j = 1; j < sum; j++) {
								if (j <= i) {
									mapSource += "." + temps[j];
								} else if (j >= i + 2) {
									dbTarget += "." + temps[j];
								}
							}
							mapSource += "." + curSetting.getTargetField();// 中间字段

							oms.add(new OneToManySetting(mapSource, item,
									curSetting.getTargetEntity(), curSetting
											.getTargetField(), dbTarget));
							// 将属性列表中的源属性替换
							properties.set(properties.indexOf(item), mapSource);

							// 添加查询字段
							// queryProperties.add((preClassAlias != null ?
							// preClassAlias + "." : "") +
							// curSetting.getSourceFiled());
							queryProperties.add(curSetting.getSourceFiled());
							queryTables.add(curSetting.getTargetEntity()
									+ " AS " + temps[i]);
							break;
						} else {
							// 获取别名
							classAlias = (curSetting.getClassAlias() != null ? curSetting
									.getClassAlias()
									: curSetting.getSourceEntity());
							// if(preClassAlias != null){
							// classAlias = preClassAlias + "_" + classAlias;
							// }
							// 保存要查询的表
							queryTables.add(curSetting.getTargetEntity()
									+ " AS " + classAlias);
							// 添加判断条件
							// queryConditions.add((preClassAlias != null ?
							// preClassAlias+"." : "") +
							// curSetting.getSourceFiled()+"="+
							// classAlias+"."+curSetting.getTargetField());
							queryConditions.add(curSetting.getSourceFiled()
									+ "=" + classAlias + "."
									+ curSetting.getTargetField());
						}
						// preClassAlias = classAlias;
					}
					curEntityName = curSetting.getTargetEntity();
				}
			} else {
				queryProperties.add(/* entityName + "." + */item);
			}
		}
		queryTables.add(entityName);

		String sql = "SELECT ";
		String temp = "";
		// 处理查询属性
		if (isCount) {
			for (String item : queryProperties) {
				sql += "count(DISTINCT " + item + ") AS e ";
				break;
			}
		} else {
			for (String item : queryProperties) {
					temp += "," + item;
			}
			sql += "DISTINCT " + temp.substring(1) + " ";
		}
		// 处理查询表
		temp = "";
		for (String item : queryTables) {
			temp += "," + item;
		}
		sql += "FROM " + temp.substring(1) + " ";
		// 处理查询条件
		temp = "";
		for (String item : queryConditions) {
			temp += "AND " + item + " ";
		}
		if (temp.length() > 0) {
			sql += "WHERE " + temp.substring(4);
		}
		// 查询数据(自定义查询条件)
		if (StringUtil.notEmpty(condition)) {
			if (temp.length() == 0) {
				sql += "WHERE " + condition;
			} else if (!StringUtil.startWith(condition, specialSql)) {
				sql += "AND " + condition;
			} else {
				sql += condition;
			}
		}
		return sqlSafeHandler(sql);
	}

	/**
	 * 过滤查询属性中的*号
	 * 
	 * @param entityName
	 * @param properties
	 */
	private void propertiesFielter(String entityName, List<String> properties,
			boolean needLink) {
		int index = -1;
		for (String item : properties) {
			if (item.equals("*")) {
				index = properties.indexOf(item);
				break;
			}
		}
		if (index != -1) {// 有*号，处理
			Map<String, EntitySetting> settings = BeanUtil
					.getEntitySettingByName(getEntity().getSimpleName());
			EntitySetting curSetting = null;
			try {
				properties.remove(index);
				String[] entityProperties = ((AbstractEntity) getEntity()
						.newInstance()).getEntityPropertiesName();
				for (String field : entityProperties) {
					curSetting = findEntitySettingByFieldName(settings, field);
					if (needLink && curSetting != null
							&& !StringUtil.isEmpty(curSetting.getSimpleText())) {
						properties.add(index, curSetting.getClassAlias() + "."
								+ curSetting.getSimpleText());
					} else {
						properties.add(index, field);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从字段配置集合里面获取source等于属性名的配置
	 * 
	 * @param settings
	 * @param fieldName
	 * @return
	 */
	private EntitySetting findEntitySettingByFieldName(
			Map<String, EntitySetting> settings, String fieldName) {
		for (EntitySetting item : settings.values()) {
			if (item.getSourceFiled().equals(fieldName)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 获取一对多的数据
	 * 
	 * @param property
	 *            查询的属性，也就是原属性的后半段
	 * @param curEntityname
	 * @param dbsource
	 *            源限制条件的字段
	 * @param dbValue
	 *            源限制条件的值
	 * @return
	 */
	private Object findOneToManyData(String property, String curEntityname,
			String dbsource, String dbValue) {
		List<String> properties = new ArrayList<String>();
		properties.add(property);
		// 添加限制条件
		String[] values = dbValue.split(",");
		String condition = "";
		for (String value : values) {
			condition += ",'" + value + "'";
		}
		condition = curEntityname + "." + dbsource + " IN ("
				+ condition.substring(1) + ")";
		final String sql = createSQL(curEntityname, properties,
				new ArrayList<OneToManySetting>(), condition, true, false);

		Object objData = this.execute(new HibernateCallback() {

			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				List<Object> data = query.list();
				return data;
			}
		});
		// Session session = this.getSession();
		// SQLQuery query = session.createSQLQuery(sql);
		// List<?> list = query.list();
		// session.close();
		List<?> list = (List<?>) objData;

		String value = "";
		for (Object item : list) {
			value += "," + item.toString();
		}
		return value.substring(1);
	}

	/**
	 * 用SQL查询并加载数据
	 * 
	 * @param <T>
	 * @param entity
	 * @param sql
	 * @param properties
	 * @param index
	 *            不要的就写-1
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> loadData(final String sql,
			List<String> properties, List<OneToManySetting> oms,
			final int index, final int limit) {
		Object objData = this.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				///System.out.println(sql);
				SQLQuery query = session.createSQLQuery(sql);
				if (index != -1 && limit != -1) {
					query.setFirstResult(index);
					query.setMaxResults(limit);
				}
				List<Object> data = query.list();
				return data;
			}
		});
		// Session session = this.getSession();
		// System.out.println(sql);
		// SQLQuery query = session.createSQLQuery(sql);
		// if(index != -1 && limit != -1){
		// query.setFirstResult(index);
		// query.setMaxResults(limit);
		// }
		// List<Object[]> data = query.list();
		// session.close();
		List<Object> data = (List<Object>) objData;

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>(
				data.size());
		Map<String, Object> result = null;
		Object[] temp = null;
		// 装载数据
		for (Object item : data) {
			if (null == item || !item.getClass().isArray()) {
				result = new LinkedHashMap<String, Object>(1);
				for (int i = 0; i < properties.size(); i++) {
					result.put(properties.get(i).replace(".", "_"), item);
				}
				results.add(result);
			} else {
				temp = (Object[]) item;
				if (temp != null) {
					result = new LinkedHashMap<String, Object>(temp.length);
					for (int i = 0; i < properties.size(); i++) {
						result.put(properties.get(i).replace(".", "_"),
										temp[i]);
					}
					results.add(result);
				}
			}
		}
		// 在这里处理多对一的数据的情况
		if (oms.size() > 0) {
			String mapSource = null;
			String mapTarget = null;
			String tempKey = null;
			Map<String, Object> cache = null;
			for (OneToManySetting om : oms) {
				cache = new HashMap<String, Object>();
				for (Map<String, Object> item : results) {
					mapSource = om.getMapSource().replace(".", "_");
					mapTarget = om.getMapTarget().replace(".", "_");
					tempKey = item.get(mapSource).toString();
					if (cache.get(tempKey) == null) {
						cache.put(tempKey, findOneToManyData(om.getDbTarget(),
								om.getEntity(), om.getDbSource(), tempKey));
					}
					item.put(mapTarget, cache.get(tempKey));
				}
			}
		}
		return results;
	}

	public int updateProperties(final Map<String, Object> data, String condition) {
		if (data.size() == 0) {
			return 0;
		}
		return updateProperties(getEntity(), data, condition);
	}

	public int updateProperties(Class<?> entity,
			final Map<String, Object> data, String condition) {
		if (data.size() == 0) {
			return 0;
		}
		StringBuffer tempSql = new StringBuffer("UPDATE ");
		tempSql.append(BeanUtil.getEntityName(entity));
		tempSql.append(" SET ");
		String key = null;
		Iterator<String> keys = data.keySet().iterator();
		while (keys.hasNext()) {
			key = keys.next();
			tempSql.append(key + "=?");
			if (keys.hasNext()) {
				tempSql.append(",");
			}
		}
		if (StringUtil.notEmpty(condition)) {
			tempSql.append(" WHERE " + condition);
		}

		final String sql = tempSql.toString();

		// 执行sql
		Object result = this.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				Object temp = null;
				int index = 0;
				Iterator<String> keys = data.keySet().iterator();
				while (keys.hasNext()) {
					temp = data.get(keys.next());
					query.setParameter(index++, temp == null ? "" : temp);
				}
				int result = query.executeUpdate();
				return result;
			}
		});
		return (Integer) result;
	}

	public int execute(final String sql) {
		Object result = this.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				int result = query.executeUpdate();
				return result;
			}
		});
		return (Integer) result;
	}
	
	
	private String createSqlWithAlias(String entityName, List<String> properties,
			List<OneToManySetting> oms, String condition, boolean needLink,
			boolean isCount) {
		propertiesFielter(entityName, properties, needLink);// 处理*号

		String curEntityName;// 当前实体的名称

		Map<String, EntitySetting> settings = null;

		/**
		 * 查询的字段
		 */
		Set<String> queryProperties = new LinkedHashSet<String>();
		Set<String> queryTables = new HashSet<String>();
		Set<String> queryConditions = new HashSet<String>();

		EntitySetting curSetting = null;
		String[] temps = null;
		String classAlias = null;// 别名
		// String preClassAlias = null;//前一个的别名

		for (String item : properties) {
			if (item.indexOf(".") != -1 && needLink) {
				curEntityName = entityName;
				
				temps = StringUtil.split(item, ".");
				int sum = temps.length;
				// preClassAlias = null;
				for (int i = 0; i < sum; i++) {
					if (i == sum - 1) {// 处理最后一个
						queryProperties.add(classAlias + "." + temps[i]);
					} else {
						settings = BeanUtil
								.getEntitySettingByName(curEntityName);// 获取当前的所有配置
						curSetting = settings.get(temps[i]);// 获取当前字段的配置
						// 判断是否是一对多情况
						if (curSetting.getLinkType().equals("1")) {
							// 获取中间状态的输属性名称
							String mapSource = temps[i];
							// 获取用中间字段要查询的值
							String dbTarget = temps[i + 1];
							for (int j = 1; j < sum; j++) {
								if (j <= i) {
									mapSource += "." + temps[j];
								} else if (j >= i + 2) {
									dbTarget += "." + temps[j];
								}
							}
							mapSource += "." + curSetting.getTargetField();// 中间字段

							oms.add(new OneToManySetting(mapSource, item,
									curSetting.getTargetEntity(), curSetting
											.getTargetField(), dbTarget));
							// 将属性列表中的源属性替换
							properties.set(properties.indexOf(item), mapSource);

							// 添加查询字段
							// queryProperties.add((preClassAlias != null ?
							// preClassAlias + "." : "") +
							// curSetting.getSourceFiled());
							queryProperties.add(curSetting.getSourceFiled());
							queryTables.add(curSetting.getTargetEntity()
									+ " AS " + temps[i]);
							break;
						} else {
							// 获取别名
							classAlias = (curSetting.getClassAlias() != null ? curSetting
									.getClassAlias()
									: curSetting.getSourceEntity());
							// if(preClassAlias != null){
							// classAlias = preClassAlias + "_" + classAlias;
							// }
							// 保存要查询的表
							queryTables.add(curSetting.getTargetEntity()
									+ " AS " + classAlias);
							// 添加判断条件
							// queryConditions.add((preClassAlias != null ?
							// preClassAlias+"." : "") +
							// curSetting.getSourceFiled()+"="+
							// classAlias+"."+curSetting.getTargetField());
							queryConditions.add(curSetting.getSourceFiled()
									+ "=" + classAlias + "."
									+ curSetting.getTargetField());
						}
						// preClassAlias = classAlias;
					}
					curEntityName = curSetting.getTargetEntity();
				}
			} else {
				queryProperties.add(/* entityName + "." + */item);
			}
		}
		queryTables.add(entityName);

		String sql = "SELECT ";
		String temp = "";
		// 处理查询属性
		if (isCount) {
			for (String item : queryProperties) {
				sql += "count(DISTINCT " + item + ") AS e ";
				break;
			}
		} else {
			int index = 0;
			for (String item : queryProperties) {
					temp += "," + item+" _mm"+(index++);
			}
			sql += "DISTINCT " + temp.substring(1) + " ";
		}
		// 处理查询表
		temp = "";
		for (String item : queryTables) {
			temp += "," + item;
		}
		sql += "FROM " + temp.substring(1) + " ";
		// 处理查询条件
		temp = "";
		for (String item : queryConditions) {
			temp += "AND " + item + " ";
		}
		if (temp.length() > 0) {
			sql += "WHERE " + temp.substring(4);
		}
		// 查询数据(自定义查询条件)
		if (StringUtil.notEmpty(condition)) {
			if (temp.length() == 0) {
				sql += "WHERE " + condition;
			} else if (!StringUtil.startWith(condition, specialSql)) {
				sql += "AND " + condition;
			} else {
				sql += condition;
			}
		}
		return sqlSafeHandler(sql);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private List<Map<String, Object>> loadDataWithType(final String sql,
			List<String> properties,final List<Type> types ,List<OneToManySetting> oms,
			final int index, final int limit) {
		Object objData = this.execute(new HibernateCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//system.out.println(sql);
				SQLQuery query = session.createSQLQuery(sql);
				for(int i=0;i<types.size();i++){
					//System.out.println("_mm"+i+" "+types.get(i));
					query.addScalar("_mm"+i,types.get(i));
				}
				if (index != -1 && limit != -1) {
					query.setFirstResult(index);
					query.setMaxResults(limit);
				}
				List<Object> data = query.list();
				return data;
			}
		});
		List<Object> data = (List<Object>) objData;

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>(
				data.size());
		Map<String, Object> result = null;
		Object[] temp = null;
		// 装载数据
		for (Object item : data) {
			if (null == item || !item.getClass().isArray()) {
				result = new LinkedHashMap<String, Object>(1);
				for (int i = 0; i < properties.size(); i++) {
					result.put(properties.get(i).replace(".", "_"), item);
				}
				results.add(result);
			} else {
				temp = (Object[]) item;
				if (temp != null) {
					result = new LinkedHashMap<String, Object>(temp.length);
					for (int i = 0; i < properties.size(); i++) {
						result.put(properties.get(i).replace(".", "_"),
										temp[i]);
					}
					//System.out.println(result);
					results.add(result);
				}
			}
		}
		// 在这里处理多对一的数据的情况
		if (oms.size() > 0) {
			String mapSource = null;
			String mapTarget = null;
			String tempKey = null;
			Map<String, Object> cache = null;
			for (OneToManySetting om : oms) {
				cache = new HashMap<String, Object>();
				for (Map<String, Object> item : results) {
					mapSource = om.getMapSource().replace(".", "_");
					mapTarget = om.getMapTarget().replace(".", "_");
					tempKey = item.get(mapSource).toString();
					if (cache.get(tempKey) == null) {
						cache.put(tempKey, findOneToManyData(om.getDbTarget(),
								om.getEntity(), om.getDbSource(), tempKey));
					}
					item.put(mapTarget, cache.get(tempKey));
				}
			}
		}
		return results;
	}
	
	public List<Map<String, Object>> getWithInType(String[] properties,String[] types,
			String condition, String sortField, String order, boolean needLink, int index, int limit) {
		if (StringUtil.notEmpty(sortField) && StringUtil.notEmpty(order)) {
			// 处理排序
			condition += createSort(sortField, order, needLink);
		}
		List<String> propertiesName = new ArrayList<String>(properties.length);
		for (String name : properties) {
			propertiesName.add(name.replace("_", "."));
		}
		final List<Type> typeList = new ArrayList<Type>(types.length);
		for(String temp : types){
			typeList.add(getType(temp));
		}
		List<OneToManySetting> oms = new ArrayList<OneToManySetting>();
		String sql = createSqlWithAlias(BeanUtil.getEntityName(getEntity()),propertiesName, oms, condition, needLink, false);
		return loadDataWithType(sql, propertiesName, typeList,oms, index, limit);
	}
	
	private Type getType(String type){
		if(type.equals("Integer")){
			return Hibernate.INTEGER;
		}else if(type.endsWith("Date")){
			return Hibernate.DATE;
		}else if(type.equals("String")){
			return Hibernate.STRING;
		}else if(type.equals("Boolean")){
			return Hibernate.BOOLEAN;
		}else if(type.equals("Double")){
			return Hibernate.DOUBLE;
		}else if(type.equals("Long")){
			return Hibernate.LONG;
		}else if(type.equals("Short")){
			return Hibernate.SHORT;
		}
		return null;
	}
}
