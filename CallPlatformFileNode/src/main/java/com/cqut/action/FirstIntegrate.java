package com.cqut.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.FileNameToFile;
import util.FindSendFile;
import util.GetFileName;
import util.HttpClientPost;
import util.HttpRequest;

public class FirstIntegrate {
	private String sendServerIp;
	private String sendNum;
	private String sendServerName;

	public String getSendServerName() {
		return sendServerName;
	}

	@SuppressWarnings("deprecation")
	public void setSendServerName(String sendServerName) {
		this.sendServerName = URLDecoder.decode(sendServerName);
	}

	public String getSendServerIp() {
		return sendServerIp;
	}

	@SuppressWarnings("deprecation")
	public void setSendServerIp(String sendServerIp) {
		this.sendServerIp = URLDecoder.decode(sendServerIp);
	}

	public String getSendNum() {
		return sendNum;
	}

	@SuppressWarnings("deprecation")
	public void setSendNum(String sendNum) {
		this.sendNum = URLDecoder.decode(sendNum);
	}

	public void exe() {
		if (sendServerIp != null && sendNum != null && sendServerName != null) {
			if (GetFileName.SERVERNAME.compareTo(sendServerName) > 0) {
				FindSendFile.isSort = true;
			} else {
				FindSendFile.isSort = false;
			}
			int sendNumInteger = Integer.parseInt(sendNum);
			FindSendFile.sendNum = sendNumInteger;
			File file = new File(GetFileName.BASEPATH);
			List<File> fileList = Arrays.asList(file.listFiles());
			FindSendFile.intoFile(fileList);
			List<String> sendFileName = FindSendFile.getSendFile();

			String[] fileName = new String[sendNumInteger];
			List<byte[]> fileByteArr = new ArrayList<byte[]>();
			for (int i = 0; i < sendNumInteger; i++) {
				File fl = new File(sendFileName.get(i));
				fileByteArr.add(FileNameToFile.fileToByteArr(fl));
				fileName[i] = fl.getName().substring(0,
						fl.getName().lastIndexOf("."));
			}

			FindSendFile.clear();
//			HttpClientPost.SubmitPost(GetFileName.getServerIp(sendServerIp,
//					"receiveIntegrateFile.action"), fileByteArr, fileName,
//					GetFileName.SERVERIP);
		} else {
			String[][] param = { { "flag", "false" } };
//			HttpRequest.sendPost(GetFileName.getServerIp(
//					GetFileName.MAINSERVER, "receiveServerReturn.action"),
//					param);
		}
	}
}
