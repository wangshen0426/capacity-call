package com.cqut.action.getLink;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetLinkman {
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (userId != null) {
			// 获取联系人姓名和ID
			// String linkmanSql =
			// "select u.userName, u.userId, u.phoneNumber from user u, linkman lk where u.userId = lk.linkUserId and lk.userId = ?";
			String linkmanSql = "select lk.linkmanName, lk.linkUserId, lk.linkmanPhoneNumber from linkman lk where lk.userId = ?";
			Object[] linkmanParam = { userId };
			List<Object> list = commonDao.executeAndReturn(linkmanSql,
					linkmanParam);
			JSONArray ja = new JSONArray();
			if (list != null && list.size() > 0) {
				int length = list.size();
				for (int i = 0; i < length; i++) {
					JSONObject jsonObj = new JSONObject();
					Object[] obj = (Object[]) list.get(i);
					jsonObj.put("linkmanName", obj[0]);
					jsonObj.put("linkmanId", obj[1]);
					jsonObj.put("phoneNumber", obj[2]);
					ja.add(jsonObj);
				}
			}
			JJCommon.sendMessageToJS(ja.toString());
		} else {
			JJCommon.sendMessageToJS("userId is null!");
		}
	}

}
