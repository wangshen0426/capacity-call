package com.cqut.action;

import util.JJCommon;

import com.cqut.entity.ServerEntity;
import com.cqut.util.CollectMessage4FileNode;

public class ReceiveFileNames {
	private String minId;
	private String maxId;
	private String serverName;
	private String serverIp;
	private String serverLargeIp;
	private String updateIp;

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getMinId() {
		return minId;
	}

	public void setMinId(String minId) {
		this.minId = minId;
	}

	public String getMaxId() {
		return maxId;
	}

	public void setMaxId(String maxId) {
		this.maxId = maxId;
	}

	public String getServerLargeIp() {
		return serverLargeIp;
	}

	public void setServerLargeIp(String serverLargeIp) {
		this.serverLargeIp = serverLargeIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public void exe() {
		if (serverName != null && serverIp != null && serverLargeIp != null
				&& updateIp != null) {
			// FirstIntegrate.addMap(serverName, fileNum, serverIp);
			ServerEntity s = new ServerEntity(serverIp, minId, serverName,
					serverLargeIp, maxId, updateIp);
			CollectMessage4FileNode.add2Map(s);
			JJCommon.sendMessageToJS("true");
		}
	}
}
