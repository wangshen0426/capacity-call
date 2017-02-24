package com.cqut.entity.informationes;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 消息实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class Informationes extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"InformationesId",
			"OriginUserId",
			"GoalUserId",
			"GroupId",
			"ReceiptTime",
			"SendTime",
			"ReceiptType",
			"MessageContent"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			Integer.class,
			String.class
	};

	public Informationes(){
		
	}
	
	public Informationes(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 毫秒数+来源编号+目标编号
	 */
	@Id
	@Column(length=150)
	public String getInformationesId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setInformationesId(String InformationesId) {
		getProperties().put(PROPERTICE_NAME[0], InformationesId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getInformationesId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setInformationesId(key);
	}
	
	/**
	 * 来源用户编号
	 */
	@Column(length=50)
	public String getOriginUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setOriginUserId(String OriginUserId) {
		getProperties().put(PROPERTICE_NAME[1], OriginUserId);
	}
	
	/**
	 * 目标用户编号
	 */
	@Column(length=50)
	public String getGoalUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGoalUserId(String GoalUserId) {
		getProperties().put(PROPERTICE_NAME[2], GoalUserId);
	}
	
	/**
	 * 用户组编号
	 */
	@Column(length=100)
	public String getGroupId() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		    return obj != null ? obj.toString() : null;
	}

	public void setGroupId(String GroupId) {
		getProperties().put(PROPERTICE_NAME[3], GroupId);
	}
	
	/**
	 * 回执时间
	 */
	@Column(length=50)
	public String getReceiptTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		    return obj != null ? obj.toString() : null;
	}

	public void setReceiptTime(String ReceiptTime) {
		getProperties().put(PROPERTICE_NAME[4], ReceiptTime);
	}
	
	/**
	 * 发送时间
	 */
	@Column(length=50)
	public String getSendTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[5]);
		    return obj != null ? obj.toString() : null;
	}

	public void setSendTime(String SendTime) {
		getProperties().put(PROPERTICE_NAME[5], SendTime);
	}
	
	/**
	 * 回执情况
	 */
	@Column(length=1)
	public Integer getReceiptType() {
		Object obj = getProperties().get(PROPERTICE_NAME[6]);
		    return obj != null ? (Integer)obj : 0;
	}

	public void setReceiptType(Integer ReceiptType) {
		getProperties().put(PROPERTICE_NAME[6], ReceiptType);
	}
	
	/**
	 * 消息内容
	 */
	@Column(length=100)
	public String getMessageContent() {
		Object obj = getProperties().get(PROPERTICE_NAME[7]);
		    return obj != null ? obj.toString() : null;
	}

	public void setMessageContent(String MessageContent) {
		getProperties().put(PROPERTICE_NAME[7], MessageContent);
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
