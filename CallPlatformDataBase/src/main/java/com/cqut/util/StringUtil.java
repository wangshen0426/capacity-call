package com.cqut.util;

import java.util.ArrayList;

import java.util.List;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class StringUtil {

	public static String[] split(String source, String str){
		if(source == null){
			return new String[0];
		}
		List<String> temp = new ArrayList<String>();
		int index = source.indexOf(str);
		while(index != -1){
			temp.add(source.substring(0, index));
			source = source.substring(index + str.length());
			index = source.indexOf(str);
		}
		temp.add(source);
		return temp.toArray(new String[temp.size()]);
	}
	
	/**
	 * 以某字符开头
	 * @return
	 */
	public static boolean startWith(String source, String str){
		String temp = source + "";
		return temp.trim().toLowerCase().startsWith(str.toLowerCase());
	}
	
	/**
	 * 以某些字符开头
	 * @return
	 */
	public static boolean startWith(String source, String[] strs){
		for(String str : strs){
			if(startWith(source, str)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 截取最后一个出现该字符的字符串段
	 * @param source
	 * @param str
	 * @return
	 */
	public static String subStringByLastFlag(String source, String str){
		if(source == null){
			return source;
		}
		int index = source.lastIndexOf(str);
		if(index != -1){
			return source.substring(index + str.length());
		}
		return source;
	}
	
	public static boolean notEmpty(String value){
		return value != null && !value.replace("\"", "").toLowerCase().equals("null") && value.length() > 0 ? true : false;
	}
	
	public static boolean isEmpty(String value){
		return !notEmpty(value);
	}
	
	public static boolean notEmpty(Object obj){
		return obj!=null && obj.toString().length() > 0 && !obj.toString().replace("\"", "").toLowerCase().equals("null") ? true:false; 
	}
	
	public static boolean isEmpty(Object obj){
		return !notEmpty(obj); 
	}
	
	/**
	 * 将浮点型数据保留小数点后K位
	 */
	public static String decimal(String numStr,int k){
		int point=numStr.indexOf(".");
		//
		if(point==-1||point+k+1>numStr.length()){//小数点不存在或者本来就满足条件
			//
			return numStr;
		}
		return numStr.substring(0, point+k+1);
	}
	
	public static String htmlspecialchars(String str) {
		if(str==null){
			return "";
		}
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		//
		return str;
	}
	
	/**
	 * 中文字符串转拼音
	 * @param inputString
	 * @return
	 */
	public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
 
        char[] input = inputString.trim().toCharArray();
        String output = "";
 
        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }
}
