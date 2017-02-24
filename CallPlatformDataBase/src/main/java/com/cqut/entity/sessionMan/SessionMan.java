package com.cqut.entity.sessionMan;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 会话人员实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class SessionMan extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"SessionManId",
			"CallTaskId",
			"UserId"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class 
	};

	public SessionMan(){
		
	}
	
	public SessionMan(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 毫秒数+任务编号+人员编号
	 */
	@Id
	@Column(length=255)
	public String getSessionManId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setSessionManId(String SessionManId) {
		getProperties().put(PROPERTICE_NAME[0], SessionManId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getSessionManId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setSessionManId(key);
	}
	
	/**
	 * 任务编号
	 */
	@Column(length=200)
	public String getCallTaskId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallTaskId(String CallTaskId) {
		getProperties().put(PROPERTICE_NAME[1], CallTaskId);
	}
	
	/**
	 * 人员编号
	 */
	@Column(length=50)
	public String getUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setUserId(String UserId) {
		getProperties().put(PROPERTICE_NAME[2], UserId);
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
