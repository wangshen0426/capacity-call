package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class FeedBackDetails {
	private String taskvoiceId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getTaskvoiceId() {
		return taskvoiceId;
	}

	@SuppressWarnings("deprecation")
	public void setTaskvoiceId(String taskvoiceId) {
		this.taskvoiceId = URLDecoder.decode(taskvoiceId);
	}

	public void exe() {
		if (taskvoiceId != null) {
			String sql = "SELECT recallTime, receiptKey, receiptMan, keyText, receiptTime, level0, linkmanName, voiceRecallTime FROM ( ( SELECT MAX(recallTime) level0, MIN(recallTime) recallTime, receiptKey, receiptMan, receiptTime, MIN(voiceRecallTime) voiceRecallTime FROM receipt WHERE taskVoiceId = ? AND receiptKey IS NOT NULL GROUP BY receiptMan ) UNION ( SELECT MAX(recallTime) level0, MAX(recallTime) recallTime, receiptKey, receiptMan, receiptTime, MAX(voiceRecallTime) voiceRecallTime FROM receipt WHERE taskVoiceId = ? AND receiptMan NOT IN ( SELECT receiptMan FROM ( SELECT MAX(recallTime), MIN(recallTime), recallTime, receiptMan, MIN(voiceRecallTime) voiceRecallTime FROM receipt WHERE taskVoiceId = ? AND receiptKey IS NOT NULL GROUP BY receiptMan ) table1 ) GROUP BY receiptMan ORDER BY level0 DESC ) ) table2 LEFT JOIN calltaskkey ON table2.receiptKey = calltaskkey.taskKeyId LEFT JOIN linkman on linkUserId = receiptMan";
			Object[] param = { taskvoiceId, taskvoiceId, taskvoiceId };
			List<Object> list = commonDao.executeAndReturn(sql, param);

			String keyContentSql = "select keyText from taskvoice tv, calltaskkey ck where tv.callTaskId = ck.callTaskId and tv.callTaskId = ?";
			Object[] keyContentParam = { taskvoiceId };
			List<Object> keyContentList = commonDao.executeAndReturn(
					keyContentSql, keyContentParam);

			if (list != null && list.size() > 0) {
				int length = list.size();

				JSONObject all = new JSONObject();
				Object[] o = (Object[]) list.get(0);
				all.put("allTime", o[5]);

				JSONArray ja = new JSONArray();
				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					JSONObject jo = new JSONObject();
					jo.put("linkmanId", obj[2]);
					jo.put("linkmanName", obj[6]);
					jo.put("receiptTime", obj[4]);
					jo.put("recallTime", obj[0]);
					if (obj[3] == null) {
						obj[3] = "null";
					}

					jo.put("receiptContent", obj[3]);
					jo.put("voiceRecallTime", obj[7]);
					ja.add(jo);
				}
				all.put("linkmanMessage", ja);

				if (keyContentList != null && keyContentList.size() > 0) {
					int keyContentLength = keyContentList.size();
					JSONArray jaa = new JSONArray();
					for (int i = 0; i < keyContentLength; i++) {
						jaa.add(keyContentList.get(i));
					}

					all.put("allKeyContent", jaa);
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
