package com.cqut.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import util.FileNameToFile;
import util.GetFileName;
import util.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReceiveIntegrateFile {
	private String JSON;
	private String originalServerIp;

	public String getOriginalServerIp() {
		return originalServerIp;
	}

	@SuppressWarnings("deprecation")
	public void setOriginalServerIp(String originalServerIp) {
		this.originalServerIp = URLDecoder.decode(originalServerIp);
	}

	public String getJSON() {
		return JSON;
	}

	@SuppressWarnings("deprecation")
	public void setJSON(String jSON) {
		JSON = URLDecoder.decode(jSON);
	}

	public void exe() {
		if (JSON != null) {
			JSONArray ja = JSONArray.fromObject(JSON);
			int length = ja.size();
			List<byte[]> fileByteList = new ArrayList<byte[]>();
			String[] fileNameArr = new String[length];
			for (int i = 0; i < length; i++) {
				JSONObject jo = ja.getJSONObject(i);
				fileNameArr[i] = jo.getString("fileName");
				JSONArray jaa = jo.getJSONArray("fileByte");
				byte[] bytes = new byte[jaa.size()];
				for (int j = 0; j < jaa.size(); j++) {
					bytes[j] = Byte.valueOf(jaa.getString(j));
				}
				fileByteList.add(bytes);
			}

			for (int i = 0; i < length; i++) {
				File file = FileNameToFile.fileNameToFile(fileNameArr[i]);
				if (file != null) {
					if (file.getParentFile().exists() == false) {
						file.getParentFile().mkdirs();
					}

					BufferedOutputStream bos = null;
					try {
						bos = new BufferedOutputStream(new FileOutputStream(
								file));
						bos.write(fileByteList.get(i));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							bos.flush();
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			String[][] param = { { "isFinished", "true" } };
			//HttpRequest.sendPost(GetFileName.getServerIp(originalServerIp,
				//	"feedbackAcceptInfo.action"), param);
		} else {
			String[][] param = { { "isFinished", "false" } };
			//HttpRequest.sendPost(GetFileName.getServerIp(originalServerIp,
				//	"feedbackAcceptInfo.action"), param);
		}
	}
}
