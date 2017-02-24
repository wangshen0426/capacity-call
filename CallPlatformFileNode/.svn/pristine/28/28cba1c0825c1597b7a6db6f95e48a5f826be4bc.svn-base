package com.cqut.action;

import java.io.File;
import java.net.URLDecoder;

import util.FindSendFile;
import util.GetFileName;
import util.HttpRequest;

public class FeedbackAcceptInfo {
	public String isFinished;

	public String getIsFinished() {
		return isFinished;
	}

	@SuppressWarnings("deprecation")
	public void setIsFinished(String isFinished) {
		this.isFinished = URLDecoder.decode(isFinished);
	}

	public void exe() {
		if (isFinished != null) {
			if (isFinished.equals("true")) {
				FindSendFile.deleteFile();// 删除文件
				FindSendFile.deleteEmptyDir(new File(GetFileName.BASEPATH));// 清空该服务器空文件夹
				String[][] param = { { "flag", "true" } };
//				HttpRequest.sendPost(GetFileName.getServerIp(
//						GetFileName.MAINSERVER, "receiveServerReturn.action"),
//						param);
			} else {
				String[][] param = { { "flag", "false" } };
//				HttpRequest.sendPost(GetFileName.getServerIp(
//						GetFileName.MAINSERVER, "receiveServerReturn.action"),
//						param);
			}
		} else {
			String[][] param = { { "flag", "false" } };
//			HttpRequest.sendPost(GetFileName.getServerIp(
//					GetFileName.MAINSERVER, "receiveServerReturn.action"),
//					param);
		}
	}
}
