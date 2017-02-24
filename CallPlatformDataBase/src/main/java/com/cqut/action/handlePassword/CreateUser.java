package com.cqut.action.handlePassword;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CreateUser {
	private String alternateNumber;
	private String password;
	private String phoneNumber;
	private String userName;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getAlternateNumber() {
		return alternateNumber;
	}

	@SuppressWarnings("deprecation")
	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = URLDecoder.decode(alternateNumber);
	}

	public String getPassword() {
		return password;
	}

	@SuppressWarnings("deprecation")
	public void setPassword(String password) {
		this.password = URLDecoder.decode(password);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@SuppressWarnings("deprecation")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = URLDecoder.decode(phoneNumber);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		try {
			this.userName = URLDecoder.decode(userName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void exe() {
		if (password != null && phoneNumber != null && userName != null) {
			String judgeNameSql = "select userId from user where userName = ?";
			Object[] judgeNameParam = { userName };
			List<Object> userNameList = commonDao.executeAndReturn(
					judgeNameSql, judgeNameParam);
			if (userNameList != null && userNameList.size() > 0) {
				JJCommon.sendMessageToJS("0");//表示用户名重复
			} else {
				String judgeNumSql = "select userId from user where phoneNumber = ?";
				Object[] judgeNumParam = { phoneNumber };
				List<Object> userNumList = commonDao.executeAndReturn(
						judgeNumSql, judgeNumParam);
				if (userNumList != null && userNumList.size() > 0) {
					JJCommon.sendMessageToJS("1");//表示电话号码重复
				} else {
					Long currentTime = System.currentTimeMillis();
					String sql = "insert into user (userId, alternateNumber, logicNumber, password, phoneNumber, userName) VALUES (?,?,?,?,?,?)";
					Object[] param = { currentTime, alternateNumber, 0,
							password, phoneNumber, userName };

					commonDao.execute(sql, param);
					JJCommon.sendMessageToJS("true");
				}
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
