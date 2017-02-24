package com.cqut.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.cqut.util.BeanUtil;

public abstract class AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -54178036849378832L;
	public static final short SUCCESS = 1;
	public static final short NOTFOUND = 0;
	
	private Map<String, Object> properties = null;
	
	public Map<String,Object> getProperties(){
		if(properties == null){
			properties = new LinkedHashMap<String, Object>();
		}
		return properties;
	}
	/**
	 * 给指定名称的属性存入值(子类的属性)
	 * @param name
	 * @param obj
	 * @return
	 */
	protected short setEntityPropertyByName(String name,Object obj) throws Exception{
		if(obj == null){//返回的是空值，不用保存
			return SUCCESS;
		}
		String[] entityPropertyName = getEntityPropertiesName();
		Class<?>[] entityPropertyType = getEntityPropertiesType();
		int index = BeanUtil.findIndex(entityPropertyName, name);
		if(index != -1){
			Class<?> temp = entityPropertyType[index];
			Class<?> so = obj.getClass();
			if(!so.equals(temp)){
				throw new Exception("Type_Error PropertyName:"+name+" type must be "+entityPropertyType[index]);
//				return TYPEERROR;
			}else{
				getProperties().put(name, obj);
				return SUCCESS;
			}
		}else{
			getProperties().put(name, obj);
			return SUCCESS;
		}
	}
	/**
	 * 给指定的属性存入值，若该属性在子类中不存在则作为扩展属性
	 * @param name
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public short setPropertyByName(String name,Object obj) throws Exception{
		short temp = setEntityPropertyByName(name,obj);
		if(temp == NOTFOUND){
			getProperties().put(name, obj);
			return SUCCESS;
		}
		return temp;
	}
	/**
	 * 获取指定名称的属性的值
	 * @param name
	 * @return
	 */
	public Object getPropertyByName(String name){
		return getProperties().get(name);
	}
	
	/**
	 * 清空扩展属性集合
	 */
	public void clearPropertys(){
		getProperties().clear();
	}

	/**
	 * 获取所有属性的名称
	 * @return
	 */
	public String[] getPropertiesName() {
		return getProperties().keySet().toArray(new String[getProperties().size()]);
	}
	/**
	 * 获取子类属性的名称
	 * @return
	 */
	public abstract String[] getEntityPropertiesName();
	/**
	 * 获取子类属性的名称
	 * @return
	 */
	public abstract Class<?>[] getEntityPropertiesType();
	/**
	 * 将子集和父级的Map集合输出
	 * @return
	 */
	public Map<String, Object> toMap(){
		return getProperties();
	}
	
	public abstract String getEntityKey();
	
	public abstract void setEntityKey(String key);
	
	/**
	 * 设置实体的值
	 * @param properties
	 * @throws Exception 
	 */
	public void setProperties(Map<String, Object> properties) throws Exception{
		if(properties != null){
			for(String key : properties.keySet()){
				this.setPropertyByName(key, properties.get(key));
			}
		}
	}
	public String getStringProperty(String key){
		Object obj=this.getProperties().get(key);
		if(obj!=null)return obj.toString();
		return null;
	}
	public boolean getBooleanProperty(String key){
		Object obj=this.getProperties().get(key);
		if(obj!=null)return (Boolean)obj;
		return false;
	}
	public java.util.Date getDateProperty(String key){
		Object obj=this.getProperties().get(key);
		if(obj!=null)return (java.util.Date)obj;
		return null;
	}
	public int getIntProperty(String key){
		Object obj=this.getProperties().get(key);
		if(obj!=null)return ((Integer)obj).intValue();
		return 0;
	}
	
}
