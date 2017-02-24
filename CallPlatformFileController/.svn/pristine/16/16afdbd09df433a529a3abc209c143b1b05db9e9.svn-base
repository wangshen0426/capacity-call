package com.cqut.entity.fileSystem;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 文件系统实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class FileSystem extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"FileId",
			"ServerId",
			"IP"
	};
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, 
			String.class, 
			String.class 
	};

	public FileSystem(){
		
	}
	
	public FileSystem(Map<String, Object> data){
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 文件编号
	 */
	@Id
	public String getFileId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		    return obj != null ? obj.toString() : null;
	}

	public void setFileId(String FileId) {
		getProperties().put(PROPERTICE_NAME[0], FileId);
	}
	
	@Transient
	@Override
	public String getEntityKey() {
		return getFileId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setFileId(key);
	}
	
	/**
	 * 服务器编号
	 */
	@Column(length=50)
	public String getServerId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		    return obj != null ? obj.toString() : null;
	}

	public void setServerId(String ServerId) {
		getProperties().put(PROPERTICE_NAME[1], ServerId);
	}
	
	/**
	 * 服务器IP
	 */
	@Column(length=50)
	public String getIP() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		    return obj != null ? obj.toString() : null;
	}

	public void setIP(String IP) {
		getProperties().put(PROPERTICE_NAME[2], IP);
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
