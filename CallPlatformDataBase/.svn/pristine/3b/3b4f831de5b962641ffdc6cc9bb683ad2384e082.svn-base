package com.cqut.action.getLink;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class DeleteLinkman {
	private String userId;
	private String linkUserId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getLinkUserId() {
		return linkUserId;
	}

	@SuppressWarnings("deprecation")
	public void setLinkUserId(String linkUserId) {
		this.linkUserId = URLDecoder.decode(linkUserId);
	}

	public void exe() {
		if (userId != null && linkUserId != null) {
			String sql = "DELETE from linkman where userId = ? and linkUserId = ?";
			Object[] param = { userId, linkUserId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
