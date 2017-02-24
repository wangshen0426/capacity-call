package util;

import com.cqut.util.SystemUtil;

public class GetSystemParam {
	public final static int FIRSTINTEGRATETIMEDELAY = Integer
			.parseInt(SystemUtil.getSystemParameter("firstIntegrateTimeDelay"));
	public final static int FILENODENUM = Integer.parseInt(SystemUtil
			.getSystemParameter("fileNodeNum"));

	public static String getServerIp(String serverIp, String actionName) {
		String[] arr = serverIp.split("/");
		int length = arr.length;
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < length - 1; i++) {
			stb.append(arr[i]);
			stb.append("/");
		}
		return stb.append(actionName).toString();
	}
}
