package util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class JJCommon {
	//将后台处理的数据输往前台
		/** 
		* @Title: sendMessageToJS 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param text    描述 
		* @return void    返回类型 
		*/ 
		public static void sendMessageToJS(Object text) {
			HttpServletResponse response=ServletActionContext.getResponse();
	  	    response.setContentType("text/plain;charset=UTF-8");
	  		PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(text);
		  		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if(out!=null){
						out.flush();
				  		out.close();
					}
				}
				catch(Exception ee) {
					
				}
			}
	  		
		}
}

