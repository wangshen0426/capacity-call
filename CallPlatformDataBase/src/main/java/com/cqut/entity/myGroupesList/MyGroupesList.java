package com.cqut.entity.myGroupesList;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 我的用户组列表实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class MyGroupesList extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"MyGroupesListId",
			"UserId",
			"GroupesId"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class 
	};

	public MyGroupesList(){
		
	}
	
	public MyGroupesList(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 毫秒数+用户编号
	 */
	@Id
	@Column(length=100)
	public String getMyGroupesListId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setMyGroupesListId(String MyGroupesListId) {
		getProperties().put(PROPERTICE_NAME[0], MyGroupesListId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getMyGroupesListId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setMyGroupesListId(key);
	}
	
	/**
	 * 用户编号
	 */
	@Column(length=50)
	public String getUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setUserId(String UserId) {
		getProperties().put(PROPERTICE_NAME[1], UserId);
	}
	
	/**
	 * 用户组编号
	 */
	@Column(length=100)
	public String getGroupesId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGroupesId(String GroupesId) {
		getProperties().put(PROPERTICE_NAME[2], GroupesId);
	}
	

	@Transient
	@Override
	public String[] getEntityPropertiesName() {
		return PROPERTICE_NAME;
	}

	@Transient
	@Override
	public Class<?>[] getEntityPropertiesType() {
		return PROPERTICE_TYPE;
	}

}
