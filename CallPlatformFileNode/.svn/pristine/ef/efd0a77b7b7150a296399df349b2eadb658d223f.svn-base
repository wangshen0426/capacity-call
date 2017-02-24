package com.cqut.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.FileNameToFile;
import util.JJCommon;

public class ReadMoreFile {
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
			int length = ja.size();
			JSONObject jo = new JSONObject();
			for (int i = 0; i < length; i++) {
				String fileName = ja.getString(i);
				jo.put(fileName, FileNameToFile.fileToByteArr(FileNameToFile
						.fileNameToFile(fileName)));
			}
			JJCommon.sendMessageToJS(jo.toString());
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
