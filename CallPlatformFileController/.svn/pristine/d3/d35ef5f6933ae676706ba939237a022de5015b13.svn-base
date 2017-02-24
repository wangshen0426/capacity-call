package com.cqut.action;

import java.net.URLDecoder;

import net.sf.json.JSONObject;

import com.cqut.entity.ServerEntity;
import com.cqut.util.StringUtil;

import util.JJCommon;
import util.ServerCache;

public class SearchOneFile {
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	@SuppressWarnings("deprecation")
	public void setFileId(String fileId) {
		this.fileId = URLDecoder.decode(fileId);
	}

	public void exe() {
		if (fileId != null) {
			ServerEntity serverInfo = ServerCache.getServerInfo(fileId);
			JSONObject jo = new JSONObject();
			jo.put("serverIp", StringUtil.getServerIp(serverInfo.getIp(),
					"readOneFile.action"));
			jo.put("fileName", fileId);
			JJCommon.sendMessageToJS(jo.toString());
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
