package com.cqut.action.handlePassword;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ChangeLogicNumber {
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (userId != null) {
			String updateSql = "update user set logicNumber = logicNumber + 1 where userId = ?";
			Object[] updateParam = { userId };
			commonDao.execute(updateSql, updateParam);

			String selectSql = "select logicNumber from user where userId = ?";
			Object[] selectParam = { userId };
			List<Object> list = commonDao.executeAndReturn(selectSql,
					selectParam);
			if (list != null && list.size() > 0) {
				JSONObject jo = new JSONObject();
				jo.put("logicNumber", (Object) (list.get(0)));
				JJCommon.sendMessageToJS(jo.toString());
			} else {
				JJCommon.sendMessageToJS("");
			}

		} else {
			JJCommon.sendMessageToJS("");
		}
	}

}
