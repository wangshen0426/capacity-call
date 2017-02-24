package com.cqut.entity.linkman;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 联系人实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class Linkman extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] { "LinkmanId",
			"UserId", "LinkUserId", "LinkmanName", "LinkmanPhoneNumber" };
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, String.class, String.class, String.class,
			String.class };

	public Linkman() {

	}

	public Linkman(Map<String, Object> data) {
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 毫秒数+用户编号+对方用户
	 */
	@Id
	@Column(length = 150)
	public String getLinkmanId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		return obj != null ? obj.toString() : null;
	}

	public void setLinkmanId(String LinkmanId) {
		getProperties().put(PROPERTICE_NAME[0], LinkmanId);
	}

	@Transient
	@Override
	public String getEntityKey() {
		return getLinkmanId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setLinkmanId(key);
	}

	/**
	 * 用户编号
	 */
	@Column(length = 50)
	public String getUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		return obj != null ? obj.toString() : null;
	}

	public void setUserId(String UserId) {
		getProperties().put(PROPERTICE_NAME[1], UserId);
	}

	/**
	 * 联系人用户编号
	 */
	@Column(length = 50)
	public String getLinkUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		return obj != null ? obj.toString() : null;
	}

	public void setLinkUserId(String LinkUserId) {
		getProperties().put(PROPERTICE_NAME[2], LinkUserId);
	}

	/**
	 * 联系人姓名
	 */
	@Column(length = 50)
	public String getLinkmanName() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		return obj != null ? obj.toString() : null;
	}

	public void setLinkmanName(String LinkmanName) {
		getProperties().put(PROPERTICE_NAME[3], LinkmanName);
	}

	/**
	 * 联系人电话号码
	 */
	@Column(length = 11)
	public String getLinkmanPhoneNumber() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		return obj != null ? obj.toString() : null;
	}

	public void setLinkmanPhoneNumber(String LinkmanPhoneNumber) {
		getProperties().put(PROPERTICE_NAME[4], LinkmanPhoneNumber);
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
