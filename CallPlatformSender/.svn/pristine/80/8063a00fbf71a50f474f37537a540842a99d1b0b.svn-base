package com.cqut.entity.YZ_GroupCall;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * YZ_GroupCall实体
 * 
 * @author zhangmiao
 * 
 */
@Entity
@DataTransferObject
public class YZ_GroupCall extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"ID",
			"Name",
			"TheDate",
			"TheTime",
			"CallType",
			"Content",
			"Status1",
			"Status2",
			"Date1",
			"Date2",
			"TelAtt",
			"RepAtt"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class 
	};

	public YZ_GroupCall(){
		
	}
	
	public YZ_GroupCall(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ID
	 */
	@Id
	public String getID() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setID(String ID) {
		getProperties().put(PROPERTICE_NAME[0], ID);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getID();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setID(key);
	}
	
	@Column
	public String getName() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setName(String Name) {
		getProperties().put(PROPERTICE_NAME[1], Name);
	}
	
	@Column
	public String getTheDate() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setTheDate(String TheDate) {
		getProperties().put(PROPERTICE_NAME[2], TheDate);
	}
	
	@Column
	public String getTheTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		    return obj != null ? obj.toString() : null;
	}

	public void setTheTime(String TheTime) {
		getProperties().put(PROPERTICE_NAME[3], TheTime);
	}
	
	@Column
	public String getCallType() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		    return obj != null ? obj.toString() : null;
	}

	public void setCallType(String CallType) {
		getProperties().put(PROPERTICE_NAME[4], CallType);
	}
	
	@Column
	public String getContent() {
		Object obj = getProperties().get(PROPERTICE_NAME[5]);
		    return obj != null ? obj.toString() : null;
	}

	public void setContent(String Content) {
		getProperties().put(PROPERTICE_NAME[5], Content);
	}
	
	@Column
	public String getStatus1() {
		Object obj = getProperties().get(PROPERTICE_NAME[6]);
		    return obj != null ? obj.toString() : null;
	}

	public void setStatus1(String Status1) {
		getProperties().put(PROPERTICE_NAME[6], Status1);
	}
	
	@Column
	public String getStatus2() {
		Object obj = getProperties().get(PROPERTICE_NAME[7]);
		    return obj != null ? obj.toString() : null;
	}

	public void setStatus2(String Status2) {
		getProperties().put(PROPERTICE_NAME[7], Status2);
	}
	
	@Column
	public String getDate1() {
		Object obj = getProperties().get(PROPERTICE_NAME[8]);
		    return obj != null ? obj.toString() : null;
	}

	public void setDate1(String Date1) {
		getProperties().put(PROPERTICE_NAME[8], Date1);
	}
	
	@Column
	public String getDate2() {
		Object obj = getProperties().get(PROPERTICE_NAME[9]);
		    return obj != null ? obj.toString() : null;
	}

	public void setDate2(String Date2) {
		getProperties().put(PROPERTICE_NAME[9], Date2);
	}
	
	@Column
	public String getTelAtt() {
		Object obj = getProperties().get(PROPERTICE_NAME[10]);
		    return obj != null ? obj.toString() : null;
	}

	public void setTelAtt(String TelAtt) {
		getProperties().put(PROPERTICE_NAME[10], TelAtt);
	}
	
	@Column
	public String getRepAtt() {
		Object obj = getProperties().get(PROPERTICE_NAME[11]);
		    return obj != null ? obj.toString() : null;
	}

	public void setRepAtt(String RepAtt) {
		getProperties().put(PROPERTICE_NAME[11], RepAtt);
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
