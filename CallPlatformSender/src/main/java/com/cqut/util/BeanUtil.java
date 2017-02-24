package com.cqut.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.cqut.util.StringUtil;
import com.cqut.util.entity.EntitySetting;
import com.cqut.util.entity.EntitySettingReader;
@RemoteProxy
public class BeanUtil {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final DecimalFormat treeCodeFormat = new DecimalFormat("0000");
	private static String preId = "";
	/**
	 * 实体参照关系
	 */
	private static Map<String, Map<String, EntitySetting>> entitySettings = new HashMap<String, Map<String, EntitySetting>>();
	
	public static void addEntitySetting(String entityName, Map<String, EntitySetting> entitySetting){
		entitySettings.put(entityName, entitySetting);
	}
	
	public static Map<String, EntitySetting> getEntitySettingByName(String entityName){
		if(!entitySettings.containsKey(entityName)){
			EntitySettingReader.readEntitySetting(entityName);
		}
		return entitySettings.get(entityName);
	}
	
	public static Class<?> getClassByName1(String name){
		if("C".equals(name)){
			return String.class;
		}else if("I".equals(name)){
			return Integer.class;
		}else if("B".equals(name)){
			return Boolean.class;
		}else if("D".equals(name)){
			return Date.class;
		}else if("S".equals(name)){
			return Short.class;
		}else if("L".equals(name)){
			return Long.class;
		}else if("T".equals(name)){
			return String.class;
		}else if("F".equals(name)){
			return Float.class;
		}else if("D".equals(name)){
			return Double.class;
		}
		return null;
	}
	
	public static int findIndex(String[] source, String name){
		int index = 0;
		for(String item : source){
			if(item.equals(name)){
				return index; 
			}
			index++;
		}
		return -1;
	}
	
	public static String getEntityName(Class<?> entity){
		return StringUtil.subStringByLastFlag(entity.getName(), ".");
	}
	
	@RemoteMethod
	public static synchronized String createId(){
		String id = format.format(Calendar.getInstance().getTime());
		while(id.equals(preId)){
			id = format.format(Calendar.getInstance().getTime());
		}
		preId = id;
		return id;
	}
	
	@RemoteMethod
	public static String getTreeCode(String parentCode){
		if(null == parentCode)
			return null;
		int code = Integer.parseInt(parentCode.substring(parentCode.length()-4, parentCode.length()));
		code++;
		return parentCode.substring(0,parentCode.length()-4)+treeCodeFormat.format(code);
	}
	
	/**
	 * 拼装数据
	 * @param source 数据源
	 * @param names 别名
	 * @return 拼装好的map对象
	 */
	public static Map<String, Object> assembling(Object[] source, String[] names){
		Map<String, Object> results = new HashMap<String, Object>(source.length);
		int index = 0;
		for(Object so : source){
			results.put(names[index++], so);
		}
		return results;
	}
}
