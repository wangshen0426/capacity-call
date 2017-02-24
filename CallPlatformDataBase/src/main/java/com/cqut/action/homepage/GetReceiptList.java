package com.cqut.action.homepage;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetReceiptList {
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
		// 获取callTaskId、callTaskName、receiptContent、time
		if (userId != null) {
			String sql = "SELECT callTaskId, callTaskName, keyText, taskVoiceTime, callTime, COUNT(recallTime) recallTime, level0 from (SELECT callTaskName, keyText, keyNumber, taskVoiceTime, callTime, level0, taskVoiceId, c.callTaskId from (SELECT calltask.callTaskId, callTime, callTaskName, taskVoiceTime, level0, taskVoiceId FROM (SELECT callTaskId, MAX(level0) level0, taskVoiceTime, taskVoiceId FROM taskvoice WHERE taskVoiceId IN (SELECT taskVoiceId FROM (SELECT taskVoiceId, MAX(recallTime) FROM receipt WHERE receiptMan IN(SELECT linkUserId FROM user u,linkman lk WHERE u.phoneNumber = lk.linkmanPhoneNumber AND u.userId = ?) GROUP BY taskVoiceId) a) GROUP BY callTaskId) b LEFT JOIN calltask ON b.callTaskId = calltask.callTaskId) c LEFT JOIN calltaskkey ON c.callTaskId = calltaskkey.callTaskId WHERE keyNumber IN (SELECT receiptKey FROM receipt WHERE receiptMan IN(SELECT linkUserId FROM user u, linkman lk WHERE u.phoneNumber = lk.linkmanPhoneNumber AND u.userId = ?))) e LEFT JOIN receipt ON e.taskVoiceId = receipt.taskVoiceId GROUP BY receipt.taskVoiceId";
			Object[] param = { userId, userId };

			List<Object> list = commonDao.executeAndReturn(sql, param);
			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONObject all = new JSONObject();
				JSONArray ja = new JSONArray();
				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					JSONObject jo = new JSONObject();
					jo.put("callTaskId", obj[0]);
					jo.put("callTaskName", obj[1]);
					if (obj[2] == null) {
						jo.put("receiptContent", 0);
					} else {
						jo.put("receiptContent", obj[2]);
					}
					jo.put("receiptTime", obj[3]);// 最新回复时间
					jo.put("callTaskTime", obj[4]);// 任务发布时间
					jo.put("recallTime", obj[5]);// 重播次数
					jo.put("allRecallTime", obj[6]);// 任务重播次数
					ja.add(jo);
				}
				all.put("json", ja);
				JJCommon.sendMessageToJS(all.toString());
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
