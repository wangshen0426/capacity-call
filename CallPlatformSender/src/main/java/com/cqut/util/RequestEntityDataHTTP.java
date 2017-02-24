package com.cqut.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class RequestEntityDataHTTP {
	public static final Object NULL = null;
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static<T> List<T> sendGet(String url, String[][] param,Class<?> clazz) {
        
        return HttpDataToObject.httpDataToObject(HttpRequest.sendGet(url, param),clazz);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static<T> List<T> sendPost(String url,String[][] param,Class<?> clazz) {
        return HttpDataToObject.httpDataToObject(HttpRequest.sendPost(url, param),clazz);
    }    
}
