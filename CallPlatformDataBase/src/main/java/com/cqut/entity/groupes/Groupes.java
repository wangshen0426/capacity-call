package com.cqut.entity.groupes;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 用户组实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class Groupes extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"GroupId",
			"GroupName",
			"CreateTime",
			"GroupMasterId"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class 
	};

	public Groupes(){
		
	}
	
	public Groupes(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 毫秒数+群主编号
	 */
	@Id
	@Column(length=100)
	public String getGroupId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGroupId(String GroupId) {
		getProperties().put(PROPERTICE_NAME[0], GroupId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getGroupId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setGroupId(key);
	}
	
	/**
	 * 用户组名
	 */
	@Column(length=50)
	public String getGroupName() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGroupName(String GroupName) {
		getProperties().put(PROPERTICE_NAME[1], GroupName);
	}
	
	/**
	 * 创建时间
	 */
	@Column(length=50)
	public String getCreateTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCreateTime(String CreateTime) {
		getProperties().put(PROPERTICE_NAME[2], CreateTime);
	}
	
	/**
	 * 群主编号
	 */
	@Column(length=50)
	public String getGroupMasterId() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGroupMasterId(String GroupMasterId) {
		getProperties().put(PROPERTICE_NAME[3], GroupMasterId);
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
