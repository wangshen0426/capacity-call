package com.cqut.action.getLink;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ApplyJoinGroup {
	private String groupId;
	private String groupMasterId;
	private String messageContent;
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public String getGroupMasterId() {
		return groupMasterId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupMasterId(String groupMasterId) {
		this.groupMasterId = URLDecoder.decode(groupMasterId);
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		try {
			this.messageContent = URLDecoder.decode(messageContent, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (groupId != null && groupMasterId != null && userId != null) {
			Long currentTime = System.currentTimeMillis();
			StringBuilder informationesIdStb = new StringBuilder();
			informationesIdStb.append(currentTime);
			informationesIdStb.append(userId);
			informationesIdStb.append(groupMasterId);

			if (messageContent == null) {
				messageContent = "";
			}
			String sql = "insert into informationes (informationesId, goalUserId, originUserId, sendTime, messageContent, groupId) VALUES (?,?,?,?,?,?)";
			Object[] param = { informationesIdStb.toString(), groupMasterId,
					userId, currentTime, messageContent, groupId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
