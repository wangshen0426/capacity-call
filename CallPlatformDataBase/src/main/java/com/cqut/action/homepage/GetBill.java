package com.cqut.action.homepage;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetBill {
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (userId != null) {
			String sql = "SELECT FROM_UNIXTIME(callTime / 1000, '%c') months,ck.callTaskId,callTaskName,callTime,COUNT(receiptMan) receiptNum,SUM(getAllTime) getAllTime FROM receipt r,calltask ck,taskvoice tv WHERE ck.createMan = ? AND tv.callTaskId = ck.callTaskId AND tv.taskVoiceId = r.taskVoiceId group BY ck.callTaskId ORDER BY callTaskName DESC";
			Object[] param = { userId };
			List<Object> list = commonDao.executeAndReturn(sql, param);
			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONObject all = new JSONObject();
				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					if (all.containsKey(obj[0]) == false) {
						JSONArray ja = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("month", obj[0]);
						jo.put("callTaskName", obj[2]);
						jo.put("callTime", obj[3]);
						jo.put("receiptNum", obj[4]);
						if (obj[5] != null) {
							jo.put("getAllTime", obj[5]);
						} else {
							jo.put("getAllTime", 0);
						}
						ja.add(jo);
						all.put(obj[0], ja);
					} else {
						JSONArray ja = all.getJSONArray(obj[0] + "");
						JSONObject jo = new JSONObject();
						jo.put("month", obj[0]);
						jo.put("callTaskName", obj[2]);
						jo.put("callTime", obj[3]);
						jo.put("receiptNum", obj[4]);
						if (obj[5] != null) {
							jo.put("getAllTime", obj[5]);
						} else {
							jo.put("getAllTime", 0);
						}
						ja.add(jo);
						all.put(obj[0], ja);
					}
				}
				JJCommon.sendMessageToJS(all.toString());
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
