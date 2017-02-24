package com.cqut.action;

import java.net.URLDecoder;

import com.cqut.util.FirstIntegrate;

public class ReceiveServerReturn {
	private String flag;

	public String getFlag() {
		return flag;
	}

	@SuppressWarnings("deprecation")
	public void setFlag(String flag) {
		this.flag = URLDecoder.decode(flag);
	}

	public void exe() {
		if (flag != null) {
			FirstIntegrate.send(flag);
		} else {
			FirstIntegrate.send("false");
		}
	}
}
