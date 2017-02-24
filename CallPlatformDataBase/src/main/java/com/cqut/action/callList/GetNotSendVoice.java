package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetNotSendVoice {
	private static String T = "0";
	private String limit;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getLimit() {
		return limit;
	}

	@SuppressWarnings("deprecation")
	public void setLimit(String limit) {
		this.limit = URLDecoder.decode(limit);
	}

	public void exe() {
		if (limit != null) {
			// String sql =
			// "select st.fileId, phoneNumber, keyNumber, keyText, st.taskVoiceId, st.type, st.taskVoiceTime from calltaskkey ck, sessionman sm, user u, (select taskVoiceId, callTaskId, fileId, type, taskVoiceTime from taskvoice where sendState = 0 and taskVoiceTime > ? ORDER BY taskVoiceTime LIMIT ? ) st where st.callTaskId = ck.callTaskId and st.callTaskId = sm.callTaskId and u.userId = sm.userId";
			String firstSql = "select taskVoiceId, callTaskId, fileId, type, taskVoiceTime from taskvoice where sendState = 1 and taskVoiceTime > ? ORDER BY taskVoiceTime LIMIT ? ";
			Object[] firstParam = { T, Integer.parseInt(limit) };
			List<Object> firstList = commonDao.executeAndReturn(firstSql,
					firstParam);

			String sql = "select st.fileId, lk.linkmanPhoneNumber, keyNumber, keyText, st.taskVoiceId, st.type, st.taskVoiceTime from calltaskkey ck, sessionman sm, linkman lk, (select taskVoiceId, callTaskId, fileId, type, taskVoiceTime from taskvoice where sendState = 1 and taskVoiceTime > ? ORDER BY taskVoiceTime LIMIT ? ) st where st.callTaskId = ck.callTaskId and st.callTaskId = sm.callTaskId and lk.linkUserId = sm.userId";
			Object[] param = { T, Integer.parseInt(limit) };
			List<Object> list = commonDao.executeAndReturn(sql, param);

			if (firstList != null && firstList.size() > 0) {
				if (list != null && list.size() > 0) {
					int length = list.size();
					JSONObject all = new JSONObject();
					synchronized (T) {
						String taskVoiceTime = (String) ((Object[]) (list
								.get(length - 1)))[6];
						if (T.compareTo(taskVoiceTime) < 0) {
							// T = taskVoiceTime;
						}
					}

					for (int i = 0; i < length; i++) {
						Object[] obj = (Object[]) list.get(i);

						if (all.containsKey(obj[4]) == false) {
							JSONObject jo = new JSONObject();
							jo.put("fileName", obj[0]);

							JSONArray receiverJa = new JSONArray();
							receiverJa.add(obj[1]);
							jo.put("receiver", receiverJa);

							JSONArray keysJa = new JSONArray();
							JSONArray keysJo = new JSONArray();
							keysJo.add(obj[2] + "");
							keysJo.add(obj[3]);
							keysJa.add(keysJo);
							jo.put("keys", keysJa);
							jo.put("taskVoiceCode", obj[4]);
							jo.put("type", obj[5] + "");
							all.put(obj[4], jo);
						} else {
							JSONObject joo = all.getJSONObject(obj[4] + "");

							JSONArray receiverJa = joo.getJSONArray("receiver");
							receiverJa.add(obj[1]);

							JSONArray keysJa = joo.getJSONArray("keys");
							JSONArray keysJo = new JSONArray();
							keysJo.add(obj[2] + "");
							keysJo.add(obj[3]);
							keysJa.add(keysJo);
							all.put(obj[4], joo);
						}
					}

					JJCommon.sendMessageToJS(all.toString());
				} else {
					synchronized (T) {
						Object[] obj = (Object[]) firstList.get(0);
						String taskVoiceTime = obj[4] + "";
						if (T.compareTo(taskVoiceTime) < 0) {
							T = taskVoiceTime;
						}
					}
					JJCommon.sendMessageToJS("{}");
				}

			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
