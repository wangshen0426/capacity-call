package com.cqut.entity.groupMember;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 用户组成员实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class GroupMember extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"GroupMemberId", "GroupId", "GroupUserId" };
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, String.class, String.class };

	public GroupMember() {

	}

	public GroupMember(Map<String, Object> data) {
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 毫秒数+用户组编号+成员编号
	 */
	@Id
	@Column(length = 200)
	public String getGroupMemberId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		return obj != null ? obj.toString() : null;
	}

	public void setGroupMemberId(String GroupMemberId) {
		getProperties().put(PROPERTICE_NAME[0], GroupMemberId);
	}

	@Transient
	@Override
	public String getEntityKey() {
		return getGroupMemberId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setGroupMemberId(key);
	}

	/**
	 * 用户组编号
	 */
	@Column(length = 100)
	public String getGroupId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		return obj != null ? obj.toString() : null;
	}

	public void setGroupId(String GroupId) {
		getProperties().put(PROPERTICE_NAME[1], GroupId);
	}

	/**
	 * 用户组成员编号
	 */
	@Column(length = 50)
	public String getGroupUserId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		return obj != null ? obj.toString() : null;
	}

	public void setGroupUserId(String GroupUserId) {
		getProperties().put(PROPERTICE_NAME[2], GroupUserId);
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
