package com.cqut.action.getGroup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ChangeGroupName {
	private String groupName;
	private String groupId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		try {
			this.groupName = URLDecoder.decode(groupName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public void exe() {
		if (groupName != null && groupId != null) {
			String sql = "update groupes set groupName = ? where groupId = ?";
			Object[] param = { groupName, groupId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
