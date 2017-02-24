package com.cqut.action.getGroup;

import java.net.URLDecoder;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetGroupData {
	private String userId;
	private int limit;
	private int currentPage;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (userId != null) {
			// 获取用户组ID、用户组Name、用户组成员、用户组成员Id
			// String sql =
			// "select mgl.groupesId, gr.groupName, u.userName from groupes gr, groupmember grm, user u, mygroupeslist mgl where u.userId = grm.userId and mgl.groupesId = grm.groupId and gr.groupId = mgl.groupesId and mgl.userId = ? ORDER BY userName DESC limit ?,?";
			String sql = "select mgl.groupesId, gr.groupName, lk.linkmanName from groupes gr, groupmember grm, linkman lk, mygroupeslist mgl where lk.linkUserId = grm.groupUserId and mgl.groupesId = grm.groupId and gr.groupId = mgl.groupesId and mgl.userId = ? ORDER BY linkmanName DESC limit ?,?";
			Object[] param = { userId, (currentPage - 1) * limit, limit };
			List<Object> list = commonDao.executeAndReturn(sql, param);
			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONObject all = new JSONObject();
				int showNum = 5;

				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);

					if (all.containsKey(obj[0]) == false) {
						JSONObject jo = new JSONObject();
						jo.put("groupId", obj[0]);
						jo.put("groupName", obj[1]);
						StringBuilder gmName = new StringBuilder();
						gmName.append(obj[2]);
						jo.put("groupMemberNameStr", gmName.toString());
						jo.put("length", 1);
						all.put(obj[0], jo);
					} else {
						JSONObject joo = all.getJSONObject(obj[0] + "");
						int gmLength = (Integer) (joo.get("length"));
						if (gmLength < showNum) {
							StringBuilder gmn = new StringBuilder();
							gmn.append(joo.get("groupMemberNameStr").toString());
							gmn.append(",");
							gmn.append(obj[2]);
							if (gmLength == 4) {
								gmn.append("...");
							}
							joo.put("groupMemberNameStr", gmn.toString());
							joo.put("length", gmLength + 1);
							all.put(obj[0], joo);
						}
					}
				}
				JJCommon.sendMessageToJS(all.toString());
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("userId is null!");
		}
	}
}
