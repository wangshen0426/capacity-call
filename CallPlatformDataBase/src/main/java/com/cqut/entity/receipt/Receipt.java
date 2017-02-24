package com.cqut.entity.receipt;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 回执实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class Receipt extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] { "ReceiptId",
			"TaskVoiceId", "ReceiptMan", "ReceiptKey", "ReceiptTime",
			"GetAllTime", "RecallTime", "VoiceRecallTime" };
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, String.class, Integer.class, String.class };

	public Receipt() {

	}

	public Receipt(Map<String, Object> data) {
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 毫秒数+任务编号+回执人编号
	 */
	@Id
	@Column(length = 255)
	public String getReceiptId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		return obj != null ? obj.toString() : null;
	}

	public void setReceiptId(String ReceiptId) {
		getProperties().put(PROPERTICE_NAME[0], ReceiptId);
	}

	@Transient
	@Override
	public String getEntityKey() {
		return getReceiptId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setReceiptId(key);
	}

	/**
	 * 任务语音编号
	 */
	@Column(length = 200)
	public String getTaskVoiceId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		return obj != null ? obj.toString() : null;
	}

	public void setTaskVoiceId(String TaskVoiceId) {
		getProperties().put(PROPERTICE_NAME[1], TaskVoiceId);
	}

	/**
	 * 回执人编号
	 */
	@Column(length = 50)
	public String getReceiptMan() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		return obj != null ? obj.toString() : null;
	}

	public void setReceiptMan(String ReceiptMan) {
		getProperties().put(PROPERTICE_NAME[2], ReceiptMan);
	}

	/**
	 * 回执按键编号
	 */
	@Column(length = 151)
	public String getReceiptKey() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		return obj != null ? obj.toString() : null;
	}

	public void setReceiptKey(String ReceiptKey) {
		getProperties().put(PROPERTICE_NAME[3], ReceiptKey);
	}

	/**
	 * 回执时间
	 */
	@Column(length = 50)
	public String getReceiptTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		return obj != null ? obj.toString() : null;
	}

	public void setReceiptTime(String ReceiptTime) {
		getProperties().put(PROPERTICE_NAME[4], ReceiptTime);
	}

	/**
	 * 本次接听总时间
	 */
	@Column(length = 50)
	public String getGetAllTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[5]);
		return obj != null ? obj.toString() : null;
	}

	public void setGetAllTime(String GetAllTime) {
		getProperties().put(PROPERTICE_NAME[5], GetAllTime);
	}

	/**
	 * 重播次数
	 */
	@Column(length = 2)
	public Integer getRecallTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[6]);
		return obj != null ? (Integer) obj : 0;
	}

	public void setRecallTime(Integer RecallTime) {
		getProperties().put(PROPERTICE_NAME[6], RecallTime);
	}

	/**
	 * 重播时间
	 */
	@Column(length = 50)
	public String getVoiceRecallTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[7]);
		return obj != null ? obj.toString() : null;
	}

	public void setVoiceRecallTime(String VoiceRecallTime) {
		getProperties().put(PROPERTICE_NAME[7], VoiceRecallTime);
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
