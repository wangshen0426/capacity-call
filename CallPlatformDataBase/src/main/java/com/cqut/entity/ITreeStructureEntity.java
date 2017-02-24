package com.cqut.entity;

/**
 * 树形结构的实体
 * @author 谌毅
 *
 */
public interface ITreeStructureEntity {

	/**
	 * parent字段的值对应的字段
	 * @return
	 */
	public String getRelationPropertiesName();
	
	/**
	 * 获取实体中存储父节点的属性名
	 * @return
	 */
	public String getParentPropertyName();
	
	/**
	 * 获取实体中父节点的值
	 * @return
	 */
	public String getParentPropertyValue();
	
	/**
	 * 获取实体中存储是否有子结点的属性名
	 * @return
	 */
	public String getHasChildrenParpertyName();
}
