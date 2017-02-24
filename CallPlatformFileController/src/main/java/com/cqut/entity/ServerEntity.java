package com.cqut.entity;

public class ServerEntity implements Comparable<String>{
	private String ip;//单个文件访问地址
	private String largeIp;//多个文件访问地址
	private String minId;//该服务器存的最小的文件名
	private String maxId;//该服务器存的最大的文件名
	private String updateIp;//客户端想服务端发送的地址
	private String serverName;//服务器名字，一般为1,2,3等

	public ServerEntity(String ip,String minId,String serverName,String largeIp,String maxId,String updateIp) {
		this.ip = ip;
		this.minId = minId;
		this.serverName = serverName;
		this.largeIp = largeIp;
		this.maxId = maxId;
		this.updateIp = updateIp;
	}
	
	
	public String getUpdateIp() {
		return updateIp;
	}


	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}


	public String getMaxId() {
		return maxId;
	}


	public void setMaxId(String maxId) {
		this.maxId = maxId;
	}


	public String getLargeIp() {
		return largeIp;
	}


	public void setLargeIp(String largeIp) {
		this.largeIp = largeIp;
	}


	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMinId() {
		return minId;
	}

	public void setMinId(String minId) {
		this.minId = minId;
	}

	@Override
	public int compareTo(String o) {
		if(o.compareTo(this.minId) > 0) {
			return -1;
		}
		else if(o.compareTo(this.minId) < 0) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
