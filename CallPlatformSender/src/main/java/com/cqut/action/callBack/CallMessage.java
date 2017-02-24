package com.cqut.action.callBack;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cqut.action.messageDeal.MessageDeal;
import com.cqut.util.DateUtil;
import com.cqut.util.HttpRequest;
import com.cqut.util.JJCommon;
import com.cqut.util.SystemUtil;

@Component
public class CallMessage {
	private String callKey;
	private String telnum;
	private String voicetaskcode;
	private String intime;

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public static final String updateDatabaseURL = SystemUtil
			.getSystemParameter("updateDatabaseURL");

	@Resource
	private MessageDeal messageDeal;

	public String getVoicetaskcode() {
		return voicetaskcode;
	}

	public void setVoicetaskcode(String voicetaskcode) {
		this.voicetaskcode = voicetaskcode;
	}

	public String getTelnum() {
		return telnum;
	}


	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getCallKey() {
		return callKey;
	}

	public void setCallKey(String callKey) {
		this.callKey = callKey;
	}

	public void getMessage() {
		JJCommon.sendMessageToJS("11|100|-1|");
		String[][] param;
		param = new String[4][2];
		param[0][0] = "callKey";
		param[0][1] = callKey;
		param[1][0] = "telnum";
		param[1][1] = telnum;
		param[2][0] = "voicetaskcode";
		param[2][1] = voicetaskcode;
		param[3][0] = "allTime";
		param[3][1] = ((System.currentTimeMillis() - DateUtil
				.dataString2Millis(intime)) / 1000) + "";
		while (true) {
			String result = HttpRequest.sendGet(updateDatabaseURL, param);
			if (result.equals(HttpRequest.NULL) == false) {
				break;
			} else {
				try {
					Thread.sleep(MessageDeal.oneceStopOtherGoTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
