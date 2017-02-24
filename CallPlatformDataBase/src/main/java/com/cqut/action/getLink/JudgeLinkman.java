package com.cqut.action.getLink;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class JudgeLinkman {
	private String userId;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@SuppressWarnings("deprecation")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = URLDecoder.decode(phoneNumber);
	}

	public void exe() {
		if (userId != null && phoneNumber != null) {
			String sql = "select linkUserId from linkman where userId = ? and linkmanPhoneNumber = ? ";
			Object[] param = { userId, phoneNumber };
			List<Object> list = commonDao.executeAndReturn(sql, param);
			if(list != null && list.size() > 0){
				JJCommon.sendMessageToJS("false");
			}else{
				JJCommon.sendMessageToJS("true");
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
