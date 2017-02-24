package com.cqut.action.add;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CreateGroupes {
	private String userId;// 此用户ID同时为群主ID
	private String groupName;// 用户组名
	private String linkmanIdStr;// 联系人ID
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getLinkmanIdStr() {
		return linkmanIdStr;
	}

	public void setLinkmanIdStr(String linkmanIdStr) {
		try {
			this.linkmanIdStr = URLDecoder.decode(linkmanIdStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

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

	public void exe() {
		if (userId != null && groupName != null) {
			JSONObject jo = JSONObject.fromObject(linkmanIdStr);
			int length = jo.size();
			String[] linkmanId = new String[length];
			int index = 0;
			Iterator iterator = jo.keys();
			while (iterator.hasNext()) {
				linkmanId[index++] = (String) iterator.next();
			}

			String groupId;
			// 创建用户组表
			Long createTime = System.currentTimeMillis();
			StringBuilder groupStf = new StringBuilder();
			groupStf.append(createTime);
			groupStf.append(userId);
			groupId = groupStf.toString();

			String groupesSql = "INSERT INTO groupes (groupId, createTime, groupMasterId, groupName) VALUES (?,?,?,?)";
			Object[] groupesParam = { groupId, createTime, userId, groupName };

			if (linkmanId != null && linkmanId.length > 0) {
				// 创建用户组成员表
				String groupMemberId;

				StringBuilder groupMemberSbf = new StringBuilder();
				groupMemberSbf
						.append("insert into groupmember (groupMemberId, groupId, groupUserId) VALUES ");
				Object[] groupMemberParam = new Object[length * 3];
				int groupMemberIndex = 0;
				for (int i = 0; i < length; i++) {
					createTime = System.currentTimeMillis();
					StringBuilder groupMemberIdStf = new StringBuilder();
					groupMemberIdStf.append(createTime);
					groupMemberIdStf.append(groupId);
					groupMemberIdStf.append(linkmanId[i]);
					groupMemberId = groupMemberIdStf.toString();

					groupMemberSbf.append("(?,?,?),");
					groupMemberParam[groupMemberIndex++] = groupMemberId;
					groupMemberParam[groupMemberIndex++] = groupId;
					groupMemberParam[groupMemberIndex++] = linkmanId[i];
				}

				groupMemberSbf.setCharAt(groupMemberSbf.length() - 1, ' ');

				// 创建我的用户组成员列表表
				String myGroupSql = "insert into mygroupeslist (myGroupesListId, groupesId, userId) VALUES (?,?,?)";
				StringBuilder myGroupIdSbf = new StringBuilder();
				myGroupIdSbf.append(System.currentTimeMillis());
				myGroupIdSbf.append(userId);
				Object[] myGroupParam = { myGroupIdSbf.toString(), groupId,
						userId };

				String[] sqls = { groupesSql, groupMemberSbf.toString(),
						myGroupSql };
				Object[][] params = { groupesParam, groupMemberParam,
						myGroupParam };
				commonDao.executeForQueueParam(sqls, params);
				JJCommon.sendMessageToJS(groupId);
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("{}");
		}

	}
}
