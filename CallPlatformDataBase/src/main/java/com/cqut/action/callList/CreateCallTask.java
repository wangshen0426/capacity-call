package com.cqut.action.callList;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CreateCallTask {
	private String userId;
	private String callTaskName;
	private String linkmanIdStr;
	private String keys;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getCallTaskName() {
		return callTaskName;
	}

	public void setCallTaskName(String callTaskName) {
		try {
			this.callTaskName = URLDecoder.decode(callTaskName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

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

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		try {
			this.keys = URLDecoder.decode(keys, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void exe() {
		if (userId != null && callTaskName != null && keys != null) {
			JSONObject linkmanIdJo = JSONObject.fromObject(linkmanIdStr);
			int linkmanIdLength = linkmanIdJo.size();
			String[] linkmanId = new String[linkmanIdLength];
			int in = 0;
			Iterator linkmanIterator = linkmanIdJo.keys();
			while (linkmanIterator.hasNext()) {
				linkmanId[in++] = (String) linkmanIterator.next();
			}

			JSONObject jo = JSONObject.fromObject(keys);
			int length = jo.size();
			String[] keyText = new String[length];
			String[] keyNumber = new String[length];
			int index = 0;
			Iterator iterator = jo.keys();
			while (iterator.hasNext()) {
				keyNumber[index] = (String) iterator.next();
				keyText[index] = jo.getString(keyNumber[index]);
				index++;
			}

			// 新增到callTask表
			StringBuilder callTaskIdStb = new StringBuilder();
			Long currentTime = System.currentTimeMillis();
			callTaskIdStb.append(currentTime);
			callTaskIdStb.append(userId);

			String callTaskSql = "insert into calltask (callTaskId, callTaskName, callTime, createMan) VALUES (?,?,?,?)";
			Object[] callTaskParam = { callTaskIdStb.toString(), callTaskName,
					currentTime, userId };

			// 新增到calltaskKey表
			StringBuilder callTaskKeyStb = new StringBuilder();
			callTaskKeyStb
					.append("insert into calltaskkey (taskKeyId, callTaskId, keyNumber, keyText) VALUES ");
			Object[] callTaskKeyParam = new Object[length * 4];
			int callTaskKeyIndex = 0;
			for (int i = 0; i < length; i++) {
				StringBuilder taskKeyIdStb = new StringBuilder();
				taskKeyIdStb.append(callTaskIdStb.toString());
				taskKeyIdStb.append(keyNumber[i]);

				callTaskKeyStb.append("(?,?,?,?),");
				callTaskKeyParam[callTaskKeyIndex++] = taskKeyIdStb.toString();
				callTaskKeyParam[callTaskKeyIndex++] = callTaskIdStb.toString();
				callTaskKeyParam[callTaskKeyIndex++] = Integer
						.parseInt(keyNumber[i]);
				callTaskKeyParam[callTaskKeyIndex++] = keyText[i];
			}
			callTaskKeyStb.setCharAt(callTaskKeyStb.length() - 1, ' ');

			if (linkmanIdLength != 0) {
				// 新增到sessionMan表
				StringBuilder sessionManStb = new StringBuilder();
				sessionManStb
						.append("insert into sessionman (sessionManId, callTaskId, userId) VALUES ");
				Object[] sessionManParam = new Object[linkmanIdLength * 3];
				int sessionManIndex = 0;
				for (int i = 0; i < linkmanIdLength; i++) {
					StringBuilder sessionManIdStb = new StringBuilder();
					sessionManIdStb.append(currentTime);
					sessionManIdStb.append(callTaskIdStb.toString());
					sessionManIdStb.append(linkmanId[i]);

					sessionManStb.append("(?,?,?),");
					sessionManParam[sessionManIndex++] = sessionManIdStb
							.toString();
					sessionManParam[sessionManIndex++] = callTaskIdStb
							.toString();
					sessionManParam[sessionManIndex++] = linkmanId[i];
				}
				sessionManStb.setCharAt(sessionManStb.length() - 1, ' ');

				String[] sqls = { callTaskSql, callTaskKeyStb.toString(),
						sessionManStb.toString() };
				Object[][] param = { callTaskParam, callTaskKeyParam,
						sessionManParam };
				commonDao.executeForQueueParam(sqls, param);
			} else {
				String[] sqls = { callTaskSql, callTaskKeyStb.toString() };
				Object[][] param = { callTaskParam, callTaskKeyParam };
				commonDao.executeForQueueParam(sqls, param);
			}
			
			JSONObject json = new JSONObject();
			json.put("callTaskId", callTaskIdStb.toString());
			json.put("callTime", currentTime);
			JJCommon.sendMessageToJS(json.toString());
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
