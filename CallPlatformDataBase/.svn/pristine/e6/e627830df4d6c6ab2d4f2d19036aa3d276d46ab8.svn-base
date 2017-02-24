package com.cqut.entity.callTask;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 呼叫任务实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class CallTask extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"CallTaskId",
			"CallTaskName",
			"CallTime",
			"CreateMan"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class 
	};

	public CallTask(){
		
	}
	
	public CallTask(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 毫秒数+创建人编号
	 */
	@Id
	@Column(length=100)
	public String getCallTaskId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallTaskId(String CallTaskId) {
		getProperties().put(PROPERTICE_NAME[0], CallTaskId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getCallTaskId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setCallTaskId(key);
	}
	
	/**
	 * 呼叫任务名称
	 */
	@Column(length=50)
	public String getCallTaskName() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallTaskName(String CallTaskName) {
		getProperties().put(PROPERTICE_NAME[1], CallTaskName);
	}
	
	/**
	 * 呼叫时间
	 */
	@Column(length=50)
	public String getCallTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallTime(String CallTime) {
		getProperties().put(PROPERTICE_NAME[2], CallTime);
	}
	
	/**
	 * 创建人
	 */
	@Column(length=50)
	public String getCreateMan() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCreateMan(String CreateMan) {
		getProperties().put(PROPERTICE_NAME[3], CreateMan);
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
