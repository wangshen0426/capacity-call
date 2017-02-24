package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class JJCommon {
	// 将后台处理的数据输往前台
	/**
	 * @Title: sendMessageToJS
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param text
	 *            描述
	 * @return void 返回类型
	 */
	public static void sendMessageToJS(Object text) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(text);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception ee) {
			}

		}

	}

	public static void sendStreamToJS(byte[] text, String fileName) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("audio/wav");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName + ".wav");
		// response.setContentType("multipart/form-data");
		// response.setHeader("Content-disposition", "attachment;filename="
		// + fileName);
		ServletOutputStream so = null;
		try {
			so = response.getOutputStream();
			so.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (so != null) {
					so.flush();
					so.close();
				}
			} catch (Exception ee) {
			}
		}
	}

}
