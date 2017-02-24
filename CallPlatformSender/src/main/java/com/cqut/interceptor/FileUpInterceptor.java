package com.cqut.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.cqut.util.SystemUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
@SuppressWarnings("serial")
public class FileUpInterceptor implements Interceptor {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext ctx = arg0.getInvocationContext();  
		Map<String,Object> map = ctx.getParameters();
		/*Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry =        iterator.next();
			System.out.println(entry.getKey()+ "     " +((String[])entry.getValue())[0]);
		}*/
		Object passKeyObject = map.get("pass");
		if(passKeyObject != null) {
			String pass = ((String[])passKeyObject)[0];
			if(SystemUtil.getSystemParameter("pass").equals(pass)) {
				return arg0.invoke();
			}
			else {
				return "error";			
			}
		}
		else {
			return "error";			
		}
	
	}

}
