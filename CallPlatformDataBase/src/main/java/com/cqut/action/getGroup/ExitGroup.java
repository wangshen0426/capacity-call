package com.cqut.action.getGroup;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ExitGroup {
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
		if (userId != null && groupId != null) {
			String groupMemberSql = "delete from mygroupeslist where userId = ? and groupesId = ?";
			Object[] groupMemberParam = { userId, groupId };

			String mygroupSql = "delete from groupmember where groupId = ? and groupUserId = ?";
			Object[] mygroupParam = { groupId, userId };

			String[] sqls = { groupMemberSql, mygroupSql };
			Object[][] params = { groupMemberParam, mygroupParam };
			commonDao.executeForQueueParam(sqls, params);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
