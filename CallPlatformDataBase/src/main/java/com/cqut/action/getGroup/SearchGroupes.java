package com.cqut.action.getGroup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONObject;

import net.sf.json.JSONArray;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class SearchGroupes {
	private String groupName;
	private int limit;
	private int currentPage;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		try {
			this.groupName = URLDecoder.decode(groupName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void exe() {
		if (groupName != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("select groupId,groupName,groupMasterId from groupes where groupName like '%");
			sb.append(groupName);
			sb.append("%' limit ");
			sb.append((currentPage - 1) * limit);
			sb.append(",");
			sb.append(limit);
			List<Object> list = commonDao.executeAndReturn(sb.toString());
			JSONArray ja = new JSONArray();
			if (list != null && list.size() > 0) {
				int length = list.size();
				for (int i = 0; i < length; i++) {
					JSONObject jo = new JSONObject();
					Object[] oo = (Object[]) list.get(i);
					jo.put("groupId", oo[0]);
					jo.put("groupName", oo[1]);
					jo.put("groupMasterId", oo[2]);
					ja.add(jo);
				}
			}
			JJCommon.sendMessageToJS(ja.toString());
		}
	}

}
