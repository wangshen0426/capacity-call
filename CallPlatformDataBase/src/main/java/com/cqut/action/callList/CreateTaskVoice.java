package com.cqut.action.callList;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cqut.dao.BaseDao;
import com.cqut.dao.IBaseDao;
import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CreateTaskVoice {
	private String fileId;// 最多为60个汉字
	private String callTaskId;
	private String type;
	private String voiceTime;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getVoiceTime() {
		return voiceTime;
	}

	@SuppressWarnings("deprecation")
	public void setVoiceTime(String voiceTime) {
		this.voiceTime = URLDecoder.decode(voiceTime);
	}

	public String getType() {
		return type;
	}

	@SuppressWarnings("deprecation")
	public void setType(String type) {
		this.type = URLDecoder.decode(type);
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		try {
			this.fileId = URLDecoder.decode(fileId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCallTaskId() {
		return callTaskId;
	}

	@SuppressWarnings("deprecation")
	public void setCallTaskId(String callTaskId) {
		this.callTaskId = URLDecoder.decode(callTaskId);
	}

	public void exe() {
		if (fileId != null && callTaskId != null) {
			String userSql = "SELECT userId FROM sessionman where callTaskId = ?";
			Object[] userParam = { callTaskId };
			List<Object> linkUserList = commonDao.executeAndReturn(userSql,
					userParam);

			String levelSql = "select max(level0) from taskvoice where callTaskId = ?";
			Object[] levelParam = { callTaskId };
			List<Object> levelList = commonDao.executeAndReturn(levelSql,
					levelParam);

			String test2Sql = "select max(recallTime) from taskvoice tv, receipt r where tv.taskVoiceId = r.taskVoiceId and tv.callTaskId = ?";
			Object[] test2Param = { callTaskId };
			List<Object> test2 = commonDao.executeAndReturn(test2Sql,
					test2Param);
			int max2 = 0;
			if (test2.get(0) != null) {
				max2 += (Integer) test2.get(0);
			}

			if (linkUserList != null && linkUserList.size() > 0) {
				int length = linkUserList.size();
				int max = 1;
				if (levelList != null && levelList.size() > 0
						&& levelList.get(0) != null) {
					max += Integer.parseInt(levelList.get(0) + "");
				}
				Long currentTime = System.currentTimeMillis();
				StringBuilder taskVoiceIdStb = new StringBuilder();
				taskVoiceIdStb.append(currentTime);
				taskVoiceIdStb.append(callTaskId);

				String taskVoiceSql = "insert into taskvoice (taskVoiceId, callTaskId, fileId, taskVoiceTime, type, sendState, level0, voiceTime) VALUES (?,?,?,?,?,?,?,?)";
				Object[] taskVoiceParam = { taskVoiceIdStb.toString(),
						callTaskId, fileId, currentTime,
						Integer.parseInt(type), 1, max, voiceTime };

				StringBuilder receiptStb = new StringBuilder();
				receiptStb
						.append("insert into receipt (receiptId, recallTime, receiptMan, taskvoiceId, voiceRecallTime) VALUES ");
				Object[] receiptParam = new Object[length * 5];
				int receiptIndex = 0;
				for (int i = 0; i < length; i++) {
					StringBuilder receiptIdStb = new StringBuilder();
					receiptIdStb.append(currentTime);
					receiptIdStb.append(taskVoiceIdStb.toString());
					receiptIdStb.append(linkUserList.get(i));

					receiptStb.append("(?,?,?,?,?),");
					receiptParam[receiptIndex++] = receiptIdStb.toString();
					receiptParam[receiptIndex++] = max2;
					receiptParam[receiptIndex++] = linkUserList.get(i);
					receiptParam[receiptIndex++] = taskVoiceIdStb.toString();
					receiptParam[receiptIndex++] = currentTime;
				}
				receiptStb.setCharAt(receiptStb.length() - 1, ' ');

				String[] sqls = { taskVoiceSql, receiptStb.toString() };
				Object[][] params = { taskVoiceParam, receiptParam };
				commonDao.executeForQueueParam(sqls, params);
				JJCommon.sendMessageToJS(taskVoiceIdStb.toString());
			} else {
				JJCommon.sendMessageToJS("{}");
			}
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
