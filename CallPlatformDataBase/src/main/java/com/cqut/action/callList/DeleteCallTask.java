package com.cqut.action.callList;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class DeleteCallTask {
	private String callTaskId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getCallTaskId() {
		return callTaskId;
	}

	public void setCallTaskId(String callTaskId) {
		this.callTaskId = callTaskId;
	}

	public void exe() {
		if (callTaskId != null) {
			String deleteCallTaskSql = "DELETE FROM calltask where callTaskId = ?";
			Object[] deleteCallTaskParam = { callTaskId };

			String deleteCallTaskKeySql = "DELETE FROM calltaskkey where callTaskId = ?";
			Object[] deleteCallTaskKeyParam = { callTaskId };

			// String deleteReceiptSql =
			// "DELETE tv.*, r.* FROM taskvoice tv, receipt r where r.taskvoiceId = tv.taskvoiceId and callTaskId = ?";
			// Object[] deleteReceiptParam = { callTaskId };
			//
			// String deleteTaskVoiceSql =
			// "DELETE FROM taskvoice where callTaskId = ?";
			// Object[] deleteTaskVoiceParam = { callTaskId };

			String deleteSessionManSql = "DELETE FROM sessionman where callTaskId = ?";
			Object[] deleteSessionManParam = { callTaskId };

			String[] sqls = { deleteCallTaskSql, deleteCallTaskKeySql,
					deleteSessionManSql };
			Object[][] params = { deleteCallTaskParam, deleteCallTaskKeyParam,
					deleteSessionManParam };
			commonDao.executeForQueueParam(sqls, params);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
