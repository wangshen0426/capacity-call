package com.cqut.action;

import com.cqut.entity.ServerEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JJCommon;
import util.ServerCache;

public class SearchMoreFile {
	private String fileNames;

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public void exe() {
		if (fileNames != null) {
			JSONArray ja = JSONArray.fromObject(fileNames);
			if (ja != null && ja.size() > 0) {
				int length = ja.size();
				JSONObject jo = new JSONObject();
				for (int i = 0; i < length; i++) {
					String fileName = ja.getString(i);
					ServerEntity serverInfo = ServerCache
							.getServerInfo(fileName);
					if (serverInfo != null) {
						String ip = serverInfo.getLargeIp();
						if (jo.containsKey(ip) == false) {
							jo.put(ip, new JSONArray());
						}
						jo.getJSONArray(ip).add(fileName);
					}

				}
				JJCommon.sendMessageToJS(jo.toString());
			} else {
				JJCommon.sendMessageToJS("");
			}

		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
