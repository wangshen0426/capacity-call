package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetCallHistory {
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
			// String sql =
			// "select level0, taskVoiceTime, keyText from taskvoice tv, receipt r, calltaskkey ck where tv.taskVoiceId = r.taskVoiceId and r.receiptKey = taskKeyId and tv.callTaskId = ? ORDER BY tv.taskVoiceTime DESC limit ?,?";
			String sql = "SELECT recallTime,receiptKey,receiptMan,keyText,voiceRecallTime FROM receipt r LEFT JOIN calltaskkey ON calltaskkey.taskKeyId = receiptKey WHERE r.taskVoiceId = ? ORDER BY recallTime";
			Object[] param = { taskvoiceId };
			List<Object> list = commonDao.executeAndReturn(sql, param);

			String keyContentSql = "select keyText from taskvoice tv, calltaskkey ck where tv.callTaskId = ck.callTaskId and tv.taskvoiceId = ?";
			Object[] keyContentParam = { taskvoiceId };
			List<Object> keyContentList = commonDao.executeAndReturn(
					keyContentSql, keyContentParam);

			if (list != null && keyContentList != null && list.size() > 0
					&& keyContentList.size() > 0) {
				int length = list.size();
				int keyContentLength = keyContentList.size();
				String[] keyContent = new String[keyContentLength];
				Map<String, Object> allMap = new HashMap<String, Object>();
				for (int i = 0; i < keyContentLength; i++) {
					keyContent[i] = keyContentList.get(i).toString();
				}

				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					if (allMap.containsKey(obj[0] + "") == false) {
						Map<String, Object> tp = new HashMap<String, Object>();
						tp.put("callTime", obj[4]);
						for (int num = 0; num < keyContentLength; num++) {
							tp.put(keyContent[num], "0");
							if (keyContent[num].equals(obj[3])) {
								tp.put(keyContent[num], 1);
								tp.put("未回复", 0);
							} else if (obj[3] == null) {
								tp.put("未回复", 1);
							}
						}
						allMap.put(obj[0] + "", tp);
					} else {
						Map<String, Object> tpp = (Map<String, Object>) allMap
								.get(obj[0] + "");
						for (int num = 0; num < keyContentLength; num++) {
							if (keyContent[num].equals(obj[3] + "")) {
								tpp.put(keyContent[num], Integer.parseInt(tpp
										.get(keyContent[num] + "") + "") + 1);
								break;
							} else if (obj[3] == null) {
								tpp.put("未回复",
										Integer.parseInt(tpp.get("未回复") + "") + 1);
								break;
							}
						}
						allMap.put(obj[0] + "", tpp);
					}

				}
				int mapLength = allMap.size();
				JSONArray ja = new JSONArray();
				for (int index = 0; index < mapLength; index++) {
					JSONObject jo = new JSONObject();
					StringBuilder stb = new StringBuilder();
					Map<String, Object> temp = (Map<String, Object>) (allMap
							.get(index + ""));
					for (int m = 0; m < keyContentLength; m++) {
						stb.append(temp.get(keyContent[m]));
						stb.append("人");
						stb.append(keyContent[m]);
						stb.append(",");
					}
					stb.append(temp.get("未回复"));
					stb.append("人");
					stb.append("未回复");
					jo.put("callTime", temp.get("callTime"));
					jo.put("keyContentStr", stb.toString());
					ja.add(jo);
				}

				JJCommon.sendMessageToJS(ja.toString());
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
