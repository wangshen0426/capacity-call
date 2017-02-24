package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CallTaskMessage {
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

	@SuppressWarnings("unchecked")
	public void exe() {
		if (callTaskId != null) {
			String sql = "select tv.taskVoiceId, tv.fileId, r.receiptMan, tv.taskVoiceTime, tv.type, r.receiptKey, voiceTime from taskvoice tv, receipt r where tv.taskVoiceId = r.taskVoiceId and tv.callTaskId = ?";
			Object[] param = { callTaskId };
			List<Object> list = commonDao.executeAndReturn(sql, param);
			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONObject all = new JSONObject();
				Map<Object, Object> temp = new HashMap<Object, Object>();
				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);

					if (all.containsKey(obj[0]) == false) {
						JSONObject jo = new JSONObject();
						Map<Object, Object> tp = new HashMap<Object, Object>();

						jo.put("fileId", obj[1]);
						jo.put("voiceTime", obj[6]);
						if (obj[5] != null) {
							jo.put("receiptNum", 1);
							tp.put(obj[2], true);
						} else {
							jo.put("receiptNum", 0);
							tp.put(obj[2], false);
						}
						jo.put("taskVoiceTime", obj[3]);
						jo.put("type", obj[4]);
						jo.put("callNum", 1);
						jo.put("taskvoiceId", obj[0]);
						all.put(obj[0], jo);
						temp.put(obj[0], tp);
					} else {
						JSONObject joo = all.getJSONObject(obj[0] + "");
						Map<Object, Object> tpp = (Map<Object, Object>) temp
								.get(obj[0]);

						if (tpp.containsKey(obj[2]) == false) {
							if (obj[5] != null) {
								joo.put("receiptNum",
										(Integer) (joo.get("receiptNum")) + 1);
								tpp.put(obj[2], true);
							} else {
								tpp.put(obj[2], false);
							}
							joo.put("callNum",
									(Integer) (joo.get("callNum")) + 1);
						} else {
							if ((Boolean) tpp.get(obj[2]) == false) {
								if (obj[5] != null) {
									joo.put("receiptNum",
											(Integer) (joo.get("receiptNum")) + 1);
									tpp.put(obj[2], true);
								}
							}
						}

						all.put(obj[0], joo);
						temp.put(obj[0], tpp);
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
