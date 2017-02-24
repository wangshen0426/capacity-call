package com.cqut.action.memorial;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class DeleteMemorial {
	private String memorialId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getMemorialId() {
		return memorialId;
	}

	@SuppressWarnings("deprecation")
	public void setMemorialId(String memorialId) {
		this.memorialId = URLDecoder.decode(memorialId);
	}

	public void exe() {
		if (memorialId != null) {
			String sql = "delete from memorial where memorialId = ?";
			Object[] param = { memorialId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
