package com.cqut.action.getGroup;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetMasterOrMember {
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
			// String sql =
			// "(select u.userId, userName from user u, groupes gr WHERE u.userId = gr.groupMasterId and groupId = ?) UNION (select u.userId,u.userName from user u, groupmember grm where u.userId = grm.userId and grm.groupId = ?)";
//			String sql = "(select u.userId, userName from user u, groupes gr WHERE u.userId = gr.groupMasterId and groupId = ?) UNION (select lk.linkUserId,lk.linkmanName from linkman lk, groupmember grm where lk.linkUserId = grm.groupUserId and grm.groupId = ?)";
//			Object[] param = { groupId, groupId };
//			List<Object> list = commonDao.executeAndReturn(sql, param);
			
			String masterSql = "select u.userId, userName, phoneNumber from user u, groupes gr WHERE u.userId = gr.groupMasterId and groupId = ?";
			Object[] masterParam = {groupId};
			List<Object> masterList = commonDao.executeAndReturn(masterSql, masterParam);
			
			String memberSql = "select lk.linkUserId,lk.linkmanName,lk.linkmanPhoneNumber from linkman lk, groupmember grm where lk.linkUserId = grm.groupUserId and grm.groupId = ?";
			Object[] memberParam = {groupId};
			List<Object> memberList = commonDao.executeAndReturn(memberSql, memberParam);
			
			if(masterList != null && masterList.size() > 0){
				JSONObject all = new JSONObject();
				Object[] obj = (Object[]) masterList.get(0);
				all.put("groupMasterId", obj[0]);
				all.put("groupMasterName", obj[1]);
				all.put("phoneNumber", obj[2]);

				if (memberList != null && memberList.size() > 0) {
					int length = memberList.size();
					JSONArray ja = new JSONArray();
					for (int i = 0; i < length; i++) {
						Object[] object = (Object[]) memberList.get(i);
						JSONObject jo = new JSONObject();
						jo.put("groupMemberId", object[0]);
						jo.put("groupMemberName", object[1]);
						jo.put("phoneNumber", object[2]);
						ja.add(jo);
					}
					all.put("groupMemberNum", length);
					all.put("groupMemberIdOrName", ja);
				}
				JJCommon.sendMessageToJS(all.toString());
			}

			
		} else {
			JJCommon.sendMessageToJS("groupId is null");
		}

	}
}
