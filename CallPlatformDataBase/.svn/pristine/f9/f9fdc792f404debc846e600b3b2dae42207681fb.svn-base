package com.cqut.action.callList;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class CancelCall {
	private String callTaskId;
	private String deleteManId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getCallTaskId() {
		return callTaskId;
	}

	@SuppressWarnings("deprecation")
	public void setCallTaskId(String callTaskId) {
		this.callTaskId = URLDecoder.decode(callTaskId);
	}

	public String getDeleteManId() {
		return deleteManId;
	}

	@SuppressWarnings("deprecation")
	public void setDeleteManId(String deleteManId) {
		this.deleteManId = URLDecoder.decode(deleteManId);
	}

	public void exe() {
		if (callTaskId != null && deleteManId != null) {
			String sql = "DELETE FROM sessionman where userId = ? and callTaskId = ?";
			Object[] param = { deleteManId, callTaskId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
