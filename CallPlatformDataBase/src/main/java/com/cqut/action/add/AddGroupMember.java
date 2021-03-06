package com.cqut.action.add;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class AddGroupMember {
	private String groupId;
	private String groupMemberIdStr;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public String getGroupMemberIdStr() {
		return groupMemberIdStr;
	}

	public void setGroupMemberIdStr(String groupMemberIdStr) {
		try {
			this.groupMemberIdStr = URLDecoder
					.decode(groupMemberIdStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void exe() {
		if (groupId != null && groupMemberIdStr != null) {
			JSONObject jo = JSONObject.fromObject(groupMemberIdStr);
			int length = jo.size();
			String[] groupUserId = new String[length];
			int index = 0;
			Iterator iterator = jo.keys();
			while (iterator.hasNext()) {
				groupUserId[index++] = (String) iterator.next();
			}
			StringBuilder sql = new StringBuilder();
			sql.append("insert into groupmember (groupMemberId, groupId, groupUserId) VALUES ");
			Object[] param = new Object[length * 3];
			int in = 0;
			for (int i = 0; i < length; i++) {
				sql.append("(?,?,?),");
				StringBuilder groupMemberId = new StringBuilder();
				groupMemberId.append(System.currentTimeMillis());
				groupMemberId.append(groupId);
				groupMemberId.append(groupUserId[i]);

				param[in++] = groupMemberId.toString();
				param[in++] = groupId;
				param[in++] = groupUserId[i];
			}
			sql.setCharAt(sql.length() - 1, ' ');

			commonDao.execute(sql.toString(), param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
