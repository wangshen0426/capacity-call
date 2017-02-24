package com.cqut.action.callList;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class RecallVoice {
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
//			String selectSql = "(SELECT userId from sessionman sm, taskvoice tv where tv.callTaskId = sm.callTaskId and tv.taskvoiceId = ?) UNION (select MAX(recallTime) userId from receipt where taskvoiceId = ?)";
//			Object[] selectParam = { taskvoiceId, taskvoiceId };
//			List<Object> list = commonDao.executeAndReturn(selectSql,
//					selectParam);
			String userIdSql = "SELECT userId from sessionman sm, taskvoice tv where tv.callTaskId = sm.callTaskId and tv.taskvoiceId = ?";
			Object[] userIdParam = {taskvoiceId};
			List<Object> userIdList = commonDao.executeAndReturn(userIdSql, userIdParam);
			
			String recallTimeSql = "select MAX(recallTime) userId from receipt where taskvoiceId = ?";
			Object[] recallTimeParam = {taskvoiceId};
			List<Object> recallTimeList = commonDao.executeAndReturn(recallTimeSql, recallTimeParam);
			
			if (userIdList != null && userIdList.size() > 0) {
				int length = userIdList.size();
				int maxRecallTime = 0;
				if(recallTimeList != null && recallTimeList.size() > 0){
					maxRecallTime = Integer.parseInt(recallTimeList.get(0) + "") + 1;
				}else{
					maxRecallTime = 1;
				}
				 
				StringBuilder insertSql = new StringBuilder();
				insertSql
						.append("INSERT INTO receipt (receiptId, recallTime, receiptMan, taskVoiceId, voiceRecallTime) VALUES ");
				Object[] insertParam = new Object[length * 5];
				int index = 0;
				for (int i = 0; i < length; i++) {
					StringBuilder receiptId = new StringBuilder();
					Long currentTime = System.currentTimeMillis();
					receiptId.append(currentTime);
					receiptId.append(taskvoiceId);
					receiptId.append(userIdList.get(i));

					insertSql.append("(?,?,?,?,?),");
					insertParam[index++] = receiptId.toString();
					insertParam[index++] = maxRecallTime;
					insertParam[index++] = userIdList.get(i);
					insertParam[index++] = taskvoiceId;
					insertParam[index++] = currentTime;
				}
				insertSql.setCharAt(insertSql.length() - 1, ' ');

				String updateSql = "update taskvoice set sendState = 1 where taskVoiceId = ?";
				Object[] updateParam = { taskvoiceId };

				String[] sqls = { insertSql.toString(), updateSql };
				Object[][] params = { insertParam, updateParam };
				commonDao.executeForQueueParam(sqls, params);
				JJCommon.sendMessageToJS("true");
			}
			JJCommon.sendMessageToJS("{}");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
