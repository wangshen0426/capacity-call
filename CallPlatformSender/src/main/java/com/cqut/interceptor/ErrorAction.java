package com.cqut.interceptor;

import com.cqut.util.JJCommon;



public class ErrorAction {
	public void exe() {
		JJCommon.sendMessageToJS("error");
	}
}
