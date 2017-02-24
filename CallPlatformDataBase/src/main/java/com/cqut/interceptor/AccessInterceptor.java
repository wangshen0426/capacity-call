package com.cqut.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AccessInterceptor implements Interceptor{

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		HttpServletResponse hsr = ServletActionContext.getResponse();
		hsr.setHeader("Access-Control-Allow-Origin", "*");
		return arg0.invoke();
	}

}
