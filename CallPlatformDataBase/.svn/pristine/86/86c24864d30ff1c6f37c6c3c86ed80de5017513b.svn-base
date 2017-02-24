package com.cqut.entity.callTaskKey;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 任务按键实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class CallTaskKey extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"TaskKeyId",
			"CallTaskId",
			"KeyNumber",
			"KeyText"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			Integer.class, 
			String.class 
	};

	public CallTaskKey(){
		
	}
	
	public CallTaskKey(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 任务编号+按键号码
	 */
	@Id
	@Column(length=151)
	public String getTaskKeyId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setTaskKeyId(String TaskKeyId) {
		getProperties().put(PROPERTICE_NAME[0], TaskKeyId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getTaskKeyId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setTaskKeyId(key);
	}
	
	/**
	 * 任务编号
	 */
	@Column(length=150)
	public String getCallTaskId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallTaskId(String CallTaskId) {
		getProperties().put(PROPERTICE_NAME[1], CallTaskId);
	}
	
	/**
	 * 按键号码
	 */
	@Column(length=1)
	public Integer getKeyNumber() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? (Integer)obj : 0;
	}

	public void setKeyNumber(Integer KeyNumber) {
		getProperties().put(PROPERTICE_NAME[2], KeyNumber);
	}
	
	/**
	 * 按键内容
	 */
	@Column(length=50)
	public String getKeyText() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		    return obj != null ? obj.toString() : null;
	}

	public void setKeyText(String KeyText) {
		getProperties().put(PROPERTICE_NAME[3], KeyText);
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
