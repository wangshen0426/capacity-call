package com.cqut.action;

import java.net.URLDecoder;

import net.sf.json.JSONObject;
import util.FileNameToFile;
import util.JJCommon;

public class ReadOneFile {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	@SuppressWarnings("deprecation")
	public void setFileName(String fileName) {
		this.fileName = URLDecoder.decode(fileName);
	}

	public void exe() {
		if (fileName != null) {
			JSONObject jo = new JSONObject();
			jo.put("filePath", FileNameToFile.fileNameToPath(fileName));
			JJCommon.sendMessageToJS(jo.toString());
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
