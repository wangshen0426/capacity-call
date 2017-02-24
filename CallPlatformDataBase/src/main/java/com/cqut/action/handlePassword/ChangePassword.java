package com.cqut.action.handlePassword;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ChangePassword {
	private String userId;
	private String password;
	private String newPassword;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getPassword() {
		return password;
	}

	@SuppressWarnings("deprecation")
	public void setPassword(String password) {
		this.password = URLDecoder.decode(password);
	}

	public String getNewPassword() {
		return newPassword;
	}

	@SuppressWarnings("deprecation")
	public void setNewPassword(String newPassword) {
		this.newPassword = URLDecoder.decode(newPassword);
	}

	public void exe() {
		if (password == null || newPassword == null || userId == null) {
			JJCommon.sendMessageToJS("false");
		} else {
			String searchSql = "select password from user where userId = ? and password = ?";
			Object[] searchParam = { userId, password };
			List<Object> flag = commonDao.executeAndReturn(searchSql,
					searchParam);
			if (flag != null && flag.size() > 0) {
				String updateSql = "update user SET password = ? where userId = ?";
				Object[] updateParam = { newPassword, userId };
				commonDao.execute(updateSql, updateParam);
				JJCommon.sendMessageToJS("true");
			} else {
				JJCommon.sendMessageToJS("false");
			}
		}
	}
}
