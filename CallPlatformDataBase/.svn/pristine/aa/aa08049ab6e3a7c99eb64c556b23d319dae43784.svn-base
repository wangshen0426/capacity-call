package com.cqut.entity.taskVoice;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.directwebremoting.annotations.DataTransferObject;

import com.cqut.entity.AbstractEntity;

/**
 * 任务语音实体
 * 
 * @author wangshen
 * 
 */
@Entity
@DataTransferObject
public class TaskVoice extends AbstractEntity {

	private static final String[] PROPERTICE_NAME = new String[] {
			"TaskVoiceId", "CallTaskId", "FileId", "TaskVoiceTime", "Level0",
			"Type", "SendState", "VoiceTime" };
	private static final Class<?>[] PROPERTICE_TYPE = new Class[] {
			String.class, String.class, String.class, String.class,
			Integer.class, Integer.class, Integer.class, String.class };

	public TaskVoice() {

	}

	public TaskVoice(Map<String, Object> data) {
		try {
			this.setProperties(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 毫秒数+任务编号
	 */
	@Id
	@Column(length = 200)
	public String getTaskVoiceId() {
		Object obj = getProperties().get(PROPERTICE_NAME[0]);
		return obj != null ? obj.toString() : null;
	}

	public void setTaskVoiceId(String TaskVoiceId) {
		getProperties().put(PROPERTICE_NAME[0], TaskVoiceId);
	}

	@Transient
	@Override
	public String getEntityKey() {
		return getTaskVoiceId();
	}

	@Transient
	@Override
	public void setEntityKey(String key) {
		setTaskVoiceId(key);
	}

	/**
	 * 任务编号
	 */
	@Column(length = 150)
	public String getCallTaskId() {
		Object obj = getProperties().get(PROPERTICE_NAME[1]);
		return obj != null ? obj.toString() : null;
	}

	public void setCallTaskId(String CallTaskId) {
		getProperties().put(PROPERTICE_NAME[1], CallTaskId);
	}

	/**
	 * 文件编号
	 */
	@Column(length = 255)
	public String getFileId() {
		Object obj = getProperties().get(PROPERTICE_NAME[2]);
		return obj != null ? obj.toString() : null;
	}

	public void setFileId(String FileId) {
		getProperties().put(PROPERTICE_NAME[2], FileId);
	}

	/**
	 * 时间
	 */
	@Column(length = 50)
	public String getTaskVoiceTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[3]);
		return obj != null ? obj.toString() : null;
	}

	public void setTaskVoiceTime(String TaskVoiceTime) {
		getProperties().put(PROPERTICE_NAME[3], TaskVoiceTime);
	}

	/**
	 * 等级
	 */
	@Column(length = 10)
	public Integer getLevel0() {
		Object obj = getProperties().get(PROPERTICE_NAME[4]);
		return obj != null ? (Integer) obj : 0;
	}

	public void setLevel0(Integer Level0) {
		getProperties().put(PROPERTICE_NAME[4], Level0);
	}

	/**
	 * 类型
	 */
	@Column(length = 1)
	public Integer getType() {
		Object obj = getProperties().get(PROPERTICE_NAME[5]);
		return obj != null ? (Integer) obj : 0;
	}

	public void setType(Integer Type) {
		getProperties().put(PROPERTICE_NAME[5], Type);
	}

	/**
	 * 发送状态
	 */
	@Column(length = 1)
	public Integer getSendState() {
		Object obj = getProperties().get(PROPERTICE_NAME[6]);
		return obj != null ? (Integer) obj : 0;
	}

	public void setSendState(Integer SendState) {
		getProperties().put(PROPERTICE_NAME[6], SendState);
	}

	@Column(length = 50)
	public String getVoiceTime() {
		Object obj = getProperties().get(PROPERTICE_NAME[7]);
		return obj != null ? obj.toString() : null;
	}

	public void setVoiceTime(String VoiceTime) {
		getProperties().put(PROPERTICE_NAME[7], VoiceTime);
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
