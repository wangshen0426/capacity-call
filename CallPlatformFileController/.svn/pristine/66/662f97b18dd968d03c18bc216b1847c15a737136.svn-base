package com.cqut.action;

import java.net.URLDecoder;

import net.sf.json.JSONObject;

import com.cqut.entity.ServerEntity;

import util.JJCommon;
import util.ServerCache;

public class GetVoiceServerInfo {
	private String userId;

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public void exe() {
		if (userId != null) {
			StringBuilder fileIdStb = new StringBuilder();
			fileIdStb.append((int) (Math.random() * 90 + 10));
			fileIdStb.append("_");
			fileIdStb.append(userId);
			fileIdStb.append("_");
			fileIdStb.append(System.currentTimeMillis());
			String fileId = fileIdStb.toString();

			ServerEntity serverInfo = ServerCache.getServerInfo(fileId);
			if (serverInfo != null) {
				JSONObject jo = new JSONObject();
				jo.put("fileId", fileId);
				jo.put("Ip", serverInfo.getUpdateIp());
				JJCommon.sendMessageToJS(jo.toString());
			} else {
				JJCommon.sendMessageToJS("false");
			}
		} else {
			JJCommon.sendMessageToJS("false");
		}
	}

}
