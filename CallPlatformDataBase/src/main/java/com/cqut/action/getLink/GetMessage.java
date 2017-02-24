package com.cqut.action.getLink;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetMessage {
	private String userId;
	private int limit;
	private int currentPage;

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
			// 获取消息列表数据
			String sql = "select originUserId, u.userName, gr.groupId, gr.groupName, messageContent,receiptType, sendTime, receiptTime, u.phoneNumber, informationesId from informationes inf, groupes gr, user u where inf.groupId = gr.groupId and inf.originUserId = u.userId and inf.goalUserId = ? ORDER BY inf.sendTime DESC LIMIT ?,?";
			Object[] param = { userId, (currentPage - 1) * limit, limit };

			List<Object> list = commonDao.executeAndReturn(sql, param);
			if (list != null && list.size() > 0) {
				int length = list.size();
				JSONArray ja = new JSONArray();
				for (int i = 0; i < length; i++) {
					Object[] obj = (Object[]) list.get(i);
					JSONObject jo = new JSONObject();
					jo.put("originUserId", obj[0]);
					jo.put("originUserName", obj[1]);
					jo.put("groupId", obj[2]);
					jo.put("groupName", obj[3]);
					jo.put("messageContent", obj[4]);
					if (obj[5] == null) {
						jo.put("receiptType", -1);// -1表示未回复
						jo.put("time", obj[6]);// 发送时间
					} else {
						jo.put("receiptType", obj[5]);// 0表示拒绝 1表示接受
						jo.put("time", obj[7]);// 回执时间
					}
					jo.put("phoneNumber", obj[8]);
					jo.put("informationesId", obj[9]);

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
