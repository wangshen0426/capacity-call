package com.cqut.entity.user;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 用户实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class User extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] { "UserId",
			"UserName", "PhoneNumber", "AlternateNumber", "Password",
			"LogicNumber" };
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, Integer.class };

	public User() {

	}

	public User(Map<String, Object> data) {
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户编号
	 */
	@Id
	@Column(length = 50)
	public String getUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		return obj != null ? obj.toString() : null;
	}

	public void setUserId(String UserId) {
		getProperties().put(PROPERTICE_NAME[0], UserId);
	}

	@Transient
	@Override
	public String getEntityKey() {
		return getUserId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setUserId(key);
	}

	/**
	 * 用户名
	 */
	@Column(length = 50)
	public String getUserName() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		return obj != null ? obj.toString() : null;
	}

	public void setUserName(String UserName) {
		getProperties().put(PROPERTICE_NAME[1], UserName);
	}

	/**
	 * 电话号码
	 */
	@Column(length = 11)
	public String getPhoneNumber() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		return obj != null ? obj.toString() : null;
	}

	public void setPhoneNumber(String PhoneNumber) {
		getProperties().put(PROPERTICE_NAME[2], PhoneNumber);
	}

	/**
	 * 备用号码
	 */
	@Column(length = 11)
	public String getAlternateNumber() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		return obj != null ? obj.toString() : null;
	}

	public void setAlternateNumber(String AlternateNumber) {
		getProperties().put(PROPERTICE_NAME[3], AlternateNumber);
	}

	/**
	 * 密码
	 */
	@Column(length = 50)
	public String getPassword() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		return obj != null ? obj.toString() : null;
	}

	public void setPassword(String Password) {
		getProperties().put(PROPERTICE_NAME[4], Password);
	}

	/**
	 * 逻辑时钟值
	 */
	@Column(length = 11)
	public Integer getLogicNumber() {
		Object obj = getProperties().get(PROPERTICE_NAME[5]);
		return obj != null ? (Integer) obj : 0;
	}

	public void setLogicNumber(Integer LogicNumber) {
		getProperties().put(PROPERTICE_NAME[5], LogicNumber);
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
