package com.cqut.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import net.sf.json.JSONObject;
import util.FileNameToFile;
import util.JJCommon;

import com.loopj.android.http.Base64;

public class ReceiveAndroidFile {
	private byte[] fileByte;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	@SuppressWarnings("deprecation")
	public void setFileName(String fileName) {
		this.fileName = URLDecoder.decode(fileName);
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(String fileByte) {
		this.fileByte = Base64.decode(fileByte, Base64.DEFAULT);
	}

	public void exe() {
		JSONObject jo = new JSONObject();
		jo.put("flag", false);
		if (fileByte != null && fileName != null) {
			File file = FileNameToFile.fileNameToFile(fileName);
			if (file != null) {
				if (file.getParentFile().exists() == false) {
					file.getParentFile().mkdirs();
				}
				BufferedOutputStream bos = null;

				try {
					bos = new BufferedOutputStream(new FileOutputStream(file));
					bos.write(fileByte);
					jo.put("flag", true);
					JJCommon.sendMessageToJS(jo.toString());
				} catch (Exception e) {
					JJCommon.sendMessageToJS(jo.toString());
					e.printStackTrace();
				} finally {
					try {
						bos.flush();
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				JJCommon.sendMessageToJS(jo.toString());
			}

		} else {
			JJCommon.sendMessageToJS(jo.toString());
		}
	}
}
