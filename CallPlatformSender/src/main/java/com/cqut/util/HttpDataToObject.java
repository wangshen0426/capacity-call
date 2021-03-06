package com.cqut.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpDataToObject {
	public static <T> List<T> httpDataToObject(String data, Class<?> clazz) {
		if (data == null || data.equals("") == true) {
			return null;
		}
		try {
			JSONObject ja = JSONObject.fromObject(data);
			int length = ja.size();
			if (length == 0)
				return null;
			JSONObject jo;
			List<T> list = new ArrayList<T>(length);
			Field[] fields = clazz.getFields();
			int fieldsLength = fields.length;
			Method[] methods = new Method[fieldsLength];
			for (int i = 0; i < fieldsLength; i++) {
				try {
					methods[i] = clazz.getMethod(
							StringUtil.getSetMethodName(fields[i].getName()),
							String.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Iterator<Entry> iterator = ja.entrySet().iterator();
			while (iterator.hasNext()) {
				jo = (JSONObject) iterator.next().getValue();
				try {
					T t = (T) clazz.newInstance();
					for (int j = 0; j < fieldsLength; j++) {
						if (jo.get(fields[j].getName()) != null) {
							methods[j].invoke(t, jo.get(fields[j].getName())
									.toString());
						}
					}
					list.add(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}

	}
}
