package com.cqut.util;

import java.util.Iterator;

import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cqut.entity.ExchangeMessage;

public class FileRequest {
	
	public static final String VOICETYPE = "0";
	
	public static final String TEXTTYPE = "1";
	
	 public static void sendGet(String url,List<ExchangeMessage> list) {
		 String result = HttpRequest.sendGet(url, getParam(list));
		 if(result.equals(HttpRequest.NULL)==false) {
			 JSONObject jo = JSONObject.fromObject(result);
			 Iterator<Entry> iterator = jo.entrySet().iterator();
			 while(iterator.hasNext()) {
				 Entry entry = iterator.next();
				 String resultTemp = HttpRequest.sendGet(entry.getKey().toString(), getFileParam(entry.getValue().toString()));
				 if(resultTemp.equals(HttpRequest.NULL)==false) {
					 dealFile(resultTemp,list);
				 }
			 }
		 }
		
	 }
	 
	 private static void dealFile(String result,List<ExchangeMessage> list) {
		 JSONObject jo = JSONObject.fromObject(result);
		 int length = list.size();
		 for(int i = 0;i < length;i++) {
			 list.get(i).setFile(jo.getJSONArray(list.get(i).fileName).toString());
		 }
	 }
	 
	 private static String[][] getFileParam(String params) {
		 String[][] param = new String[1][2];
		 param[0][0] = "fileNames";
		 param[0][1] = params;
		 return param;
	 }
	 
	 private static String[][] getParam(List<ExchangeMessage> list) {
		 int length = list.size();
		 JSONArray ja = new JSONArray();
		 for(int i = 0;i < length;i++) {
			 if(list.get(i).type.equals(VOICETYPE)) {
				 ja.add(list.get(i).fileName);
			 }
		 }
		 String[][] param = new String[1][2];
		 param[0][0] = "fileNames";
		 param[0][1] = ja.toString();
		 return param;
	 }
	 
	  public static void sendPost(String url,List<ExchangeMessage> list) {
		  String result = HttpRequest.sendGet(url, getParam(list));
			 if(result.equals(HttpRequest.NULL)==false) {
				 System.out.println(result);
				 JSONObject jo = JSONObject.fromObject(result);
				 Iterator<Entry> iterator = jo.entrySet().iterator();
				 while(iterator.hasNext()) {
					 Entry entry = iterator.next();
					 String resultTemp = HttpRequest.sendPost(entry.getKey().toString(), getFileParam(entry.getValue().toString()));
					 if(resultTemp.equals(HttpRequest.NULL)==false) {
						 dealFile(resultTemp,list);
					 }
				 }
			 }
	  }
}
