package com.cqut.util.entity;

/**
 * 用于保存一对多查询的相关信息
 * 
 * @author cy
 * 
 */
public class OneToManySetting {

	/**
	 * 要处理的Map里面的KEY的值
	 */
	private String mapSource;
	/**
	 * 要保存的Map里面的KEY的值
	 */
	private String mapTarget;
	/**
	 * 查询的实体名
	 */
	private String entity;
	/**
	 * 查询值对应的字段
	 */
	private String dbSource;
	/**
	 * 要查询结果的值，如department.name
	 */
	private String dbTarget;

	public OneToManySetting(String mapSource, String mapTarget, String entity,
			String dbSource, String dbTarget) {
		this.mapSource = mapSource;
		this.mapTarget = mapTarget;
		this.entity = entity;
		this.dbSource = dbSource;
		this.dbTarget = dbTarget;
	}

	public String getMapSource() {
		return mapSource;
	}

	public void setMapSource(String mapSource) {
		this.mapSource = mapSource;
	}

	public String getMapTarget() {
		return mapTarget;
	}

	public void setMapTarget(String mapTarget) {
		this.mapTarget = mapTarget;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getDbSource() {
		return dbSource;
	}

	public void setDbSource(String dbSource) {
		this.dbSource = dbSource;
	}

	public String getDbTarget() {
		return dbTarget;
	}

	public void setDbTarget(String dbTarget) {
		this.dbTarget = dbTarget;
	}

}
