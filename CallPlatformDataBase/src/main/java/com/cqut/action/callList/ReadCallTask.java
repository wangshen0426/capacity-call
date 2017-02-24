package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ReadCallTask {
	private String callTaskId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getCallTaskId() {
		return callTaskId;
	}

	@SuppressWarnings("deprecation")
	public void setCallTaskId(String callTaskId) {
		this.callTaskId = URLDecoder.decode(callTaskId);
	}

	public void exe() {
		if (callTaskId != null) {
			String masterSql = "select userId, userName from user u, calltask ct where u.userId = ct.createMan and callTaskId = ?";
			Object[] masterParam = {callTaskId};
			List<Object> masterList = commonDao.executeAndReturn(masterSql, masterParam);
			
			String memberSql = "select lk.linkUserId, lk.linkmanName from linkman lk, sessionman sm where lk.linkUserId = sm.userId and sm.callTaskId = ?";
			Object[] memberParam = {callTaskId};
			List<Object> memberList = commonDao.executeAndReturn(memberSql, memberParam);

			if(masterList != null && masterList.size() > 0){
				JSONObject all = new JSONObject();
				Object[] obj = (Object[]) masterList.get(0);
				all.put("createManId", obj[0]);
				all.put("createManName", obj[1]);

				if (memberList != null && memberList.size() > 0) {
					int length = memberList.size();
					JSONArray objArr = new JSONArray();
					for (int i = 0; i < length; i++) {
						Object[] object = (Object[]) memberList.get(i);
						JSONObject jo = new JSONObject();
						jo.put("sessionManId", object[0]);
						jo.put("sessionManName", object[1]);
						objArr.add(jo);
					}
					all.put("sessionManIdOrName", objArr);
				}
				JJCommon.sendMessageToJS(all.toString());
			}
			

		} else {
			JJCommon.sendMessageToJS("groupId is null!");
		}
	}
}
