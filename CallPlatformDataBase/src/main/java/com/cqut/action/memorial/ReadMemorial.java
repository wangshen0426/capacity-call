package com.cqut.action.memorial;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class ReadMemorial {
	private String userId;
	// private int currentPage;
	// private int limit;

	// public int getCurrentPage() {
	// return currentPage;
	// }
	//
	// public void setCurrentPage(int currentPage) {
	// this.currentPage = currentPage;
	// }
	//
	// public int getLimit() {
	// return limit;
	// }
	//
	// public void setLimit(int limit) {
	// this.limit = limit;
	// }

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
			String sql = "select memorialTime, memorialPosition, memorialText, memorialDate, memorialId from memorial where userId = ? ORDER BY memorialTime DESC";
			Object[] param = { userId };// , (currentPage - 1) * limit, limit
			List<Object> list = commonDao.executeAndReturn(sql, param);

			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONArray ja = new JSONArray();

				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					JSONObject jo = new JSONObject();
					jo.put("memorialTime", obj[0]);
					jo.put("memorialPosition", obj[1]);
					jo.put("memorialText", obj[2]);
					jo.put("memorialDate", obj[3]);
					jo.put("memorialId", obj[4]);
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
