package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetNotReceiptVoice {
	private String json;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getJson() {
		return json;
	}

	@SuppressWarnings("deprecation")
	public void setJson(String json) {
		this.json = URLDecoder.decode(json);
	}

	@SuppressWarnings("rawtypes")
	public void exe() {
		if (json != null) {
			JSONObject jo = JSONObject.fromObject(json);
			int length = jo.size();
			String[] taskvoiceId = new String[length];
			String[] allTime = new String[length];
			Iterator iterator = jo.keys();
			int index = 0;
			while (iterator.hasNext()) {
				taskvoiceId[index] = iterator.next() + "";
				allTime[index] = jo.getString(taskvoiceId[index]);
				index++;
			}

			StringBuilder sql = new StringBuilder();
			StringBuilder updateStateSql = new StringBuilder();
			Object[] param = new Object[3 * length];
			sql.append("UPDATE receipt SET getAllTime = CASE taskVoiceId ");
			updateStateSql
					.append("update taskvoice set sendstate = 0 where ( ");
			Object[] updateStateParam = new Object[length];
			int num = 0;
			for (int i = 0; i < length; i++) {
				sql.append("WHEN ? THEN ? ");
				updateStateSql.append("taskVoiceId = ? OR");
				updateStateParam[i] = taskvoiceId[i];
				param[num++] = taskvoiceId[i];
				param[num++] = allTime[i];
			}
			updateStateSql.setCharAt(updateStateSql.length() - 1, ' ');
			updateStateSql.setCharAt(updateStateSql.length() - 2, ')');
			sql.append("END WHERE taskVoiceId in (");
			for (int i = 0; i < length; i++) {
				sql.append("?,");
				param[num++] = taskvoiceId[i];
			}
			sql.setCharAt(sql.length() - 1, ')');
			sql.append(" and receiptKey is null and getAllTime is null ");
			String[] sqls = { sql.toString(), updateStateSql.toString() };
			Object[][] params = { param, updateStateParam };
			commonDao.executeForQueueParam(sqls, params);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
