package com.cqut.action.getLink;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ReadGroupData {
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
			String sql = "select groupId, groupName from groupes where groupMasterId = ?";
			Object[] param = { userId };
			List<Object> groupesList = commonDao.executeAndReturn(sql, param);
			int length = groupesList.size();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < length; i++) {
				Object[] obj = (Object[]) groupesList.get(i);
				JSONObject json = new JSONObject();
				json.put("groupId", obj[0]);
				json.put("groupName", obj[1]);
				ja.add(json);
			}

			JJCommon.sendMessageToJS(ja.toString());
		} else {
			JJCommon.sendMessageToJS("userId is null!");
		}

	}
}
