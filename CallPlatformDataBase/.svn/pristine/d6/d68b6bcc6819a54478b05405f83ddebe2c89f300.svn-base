package com.cqut.action.getGroup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class DeleteGroupMember {
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

	public void exe() {
		if (groupId != null && groupMemberIdStr != null) {
			JSONObject jo = JSONObject.fromObject(groupMemberIdStr);
			int length = jo.size();
			String[] groupUserId = new String[length];
			Iterator iterator = jo.keys();
			int index = 0;
			while (iterator.hasNext()) {
				groupUserId[index++] = (String) iterator.next();
			}
			StringBuilder sql = new StringBuilder();
			sql.append("delete from groupmember where groupId = ? and groupUserId in (");
			Object[] param = new Object[length + 1];
			param[0] = groupId;
			for (int i = 0; i < length; i++) {
				sql.append("?,");
				param[i + 1] = groupUserId[i];
			}
			sql.setCharAt(sql.length() - 1, ')');
			commonDao.execute(sql.toString(), param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
