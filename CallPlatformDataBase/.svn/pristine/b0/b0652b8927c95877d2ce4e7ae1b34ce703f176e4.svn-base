package com.cqut.action.handlePassword;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class Login {
	private String userName;
	private String password;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

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

	public String getPassword() {
		return password;
	}

	@SuppressWarnings("deprecation")
	public void setPassword(String password) {
		this.password = URLDecoder.decode(password);
	}

	public void exe() {
		if (userName == null || password == null) {
			JJCommon.sendMessageToJS("false");
		} else {
			String sql = "select userId, userName, logicNumber, phoneNumber from user where username = ? and password = ?";
			Object[] param = { userName, password };
			List<Object> list = commonDao.executeAndReturn(sql, param);

			if (list != null && list.size() > 0) {
				JSONObject jo = new JSONObject();
				jo.put("userId", ((Object[]) list.get(0))[0]);
				jo.put("userName", ((Object[]) list.get(0))[1]);
				jo.put("logicNumber", ((Object[]) list.get(0))[2]);
				jo.put("phoneNumber", ((Object[]) list.get(0))[3]);

				JJCommon.sendMessageToJS(jo.toString());
			} else {
				JJCommon.sendMessageToJS("false");
			}
		}
	}
}
