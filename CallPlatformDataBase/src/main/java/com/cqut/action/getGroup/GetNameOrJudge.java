package com.cqut.action.getGroup;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetNameOrJudge {
	private String userId;
	private String groupId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public void exe() {
		// 获取群主姓名
		if (userId != null && groupId != null) {
			// String sql =
			// "(select u.userName from user u, groupes gr where u.userId = ? and gr.groupId = ?) UNION (select myGroupesListId from mygroupeslist where userId = ? and groupesId = ?)";
			// Object[] param = { userId, groupId, userId, groupId };
			// List<Object> list = commonDao.executeAndReturn(sql, param);

			String masterSql = "select u.userName from user u, groupes gr where u.userId = ? and gr.groupId = ?";
			Object[] masterParam = { userId, groupId };
			List<Object> masterList = commonDao.executeAndReturn(masterSql,
					masterParam);

			String memberSql = "select myGroupesListId from mygroupeslist where userId = ? and groupesId = ?";
			Object[] memberParam = { userId, groupId };
			List<Object> memberList = commonDao.executeAndReturn(memberSql,
					memberParam);

			JSONObject jo = new JSONObject();
			jo.put("groupMasterName", masterList.get(0) + "");
			if (memberList != null && memberList.size() > 0) {
				jo.put("flag", "true");
			} else {
				jo.put("flag", "false");
			}

			JJCommon.sendMessageToJS(jo.toString());
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
