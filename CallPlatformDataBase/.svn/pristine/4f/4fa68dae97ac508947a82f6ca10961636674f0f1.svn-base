package com.cqut.action.getGroup;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class DissolveGroup {
	private String groupId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public void exe() {
		if (groupId != null) {
			String deleteGroupes = "delete from groupes where groupId = ?";
			Object[] deleteGroupesParam = { groupId };

			String deleteGroupMember = "delete from groupmember where groupId = ?";
			Object[] deleteGroupMemberParam = { groupId };

			String deleteInfo = "delete from informationes where groupId = ?";
			Object[] deleteInfoParam = { groupId };

			String[] sqls = { deleteGroupes, deleteGroupMember, deleteInfo };
			Object[][] params = { deleteGroupesParam, deleteGroupMemberParam,
					deleteInfoParam };
			commonDao.executeForQueueParam(sqls, params);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
