package com.cqut.action.add;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class AddLinkman {
	private String userId;
	private String linkmanName;
	private String phoneNumber;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		try {
			this.linkmanName = URLDecoder.decode(linkmanName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@SuppressWarnings("deprecation")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = URLDecoder.decode(phoneNumber);
	}

	public void exe() {
		if (userId != null && linkmanName != null && phoneNumber != null) {
			// 创建联系人表
			StringBuilder linkUserId = new StringBuilder();
			linkUserId.append(userId);
			linkUserId.append(phoneNumber);

			StringBuilder linkmanId = new StringBuilder();
			linkmanId.append(System.currentTimeMillis());
			linkmanId.append(userId);
			linkmanId.append(linkUserId.toString());

			String sql = "insert into linkman (linkmanId, linkUserId, userId, linkmanName, linkmanPhoneNumber) VALUES (?,?,?,?,?)";
			Object[] param = { linkmanId.toString(), linkUserId.toString(),
					userId, linkmanName, phoneNumber };

			commonDao.execute(sql, param);

			JJCommon.sendMessageToJS(linkUserId.toString());
		} else {
			JJCommon.sendMessageToJS("false");
		}
	}

}
