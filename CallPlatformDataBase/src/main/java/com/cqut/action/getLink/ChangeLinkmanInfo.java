package com.cqut.action.getLink;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ChangeLinkmanInfo {
	private String linkmanId;
	private String linkmanName;
	private String linkmanPhoneNum;
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getLinkmanId() {
		return linkmanId;
	}

	@SuppressWarnings("deprecation")
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = URLDecoder.decode(linkmanId);
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		try {
			this.linkmanName = URLDecoder.decode(linkmanName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getLinkmanPhoneNum() {
		return linkmanPhoneNum;
	}

	@SuppressWarnings("deprecation")
	public void setLinkmanPhoneNum(String linkmanPhoneNum) {
		this.linkmanPhoneNum = URLDecoder.decode(linkmanPhoneNum);
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (linkmanId != null && linkmanName != null && linkmanPhoneNum != null) {
			String sql = "update linkman set linkmanName = ?, linkmanPhoneNumber = ? where linkUserId = ? and userId = ?";
			Object[] param = { linkmanName, linkmanPhoneNum, linkmanId, userId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
