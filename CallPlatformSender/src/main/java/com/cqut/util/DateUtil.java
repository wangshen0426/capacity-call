package com.cqut.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private final static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private final static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static long dataString2Millis(String str) {
		try {
			Date date = format.parse(str);
			return date.getTime();
		} catch (ParseException e) {
			return System.currentTimeMillis();

		}
	}

	@SuppressWarnings("deprecation")
	public static String dataString2Seconds(String str) {
		try {
			Date date = format2.parse(str);
			return String.valueOf(date.getMinutes() * 60 + date.getSeconds());
		} catch (ParseException e) {
			return "0";
		}
	}
	
}
