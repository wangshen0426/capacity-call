package util;

public class StringUtil {

	public static String getShortString(Object in, int length) {
		String temp = "";
		try {
			if (null != in) {
				temp = ((String) in);
				if (temp.length() > length) {
					return temp.substring(0, length);
				}
			}
		} catch (Exception e) {
			return "";
		}
		return temp;
	}

}
