package util;

import java.io.File;

public class FindFileName {
	public static String[] findSmallestOrBigestFileName() {
		File file = new File(GetFileName.BASEPATH);
		File[] fileListMax = file.listFiles();
		File[] fileListMin = fileListMax;
		if (fileListMax == null || fileListMax.length == 0)
			return null;
		File tempName = null;
		int lengthMax = 0;
		int lengthMin = 0;
		File max = null;
		File min = null;
		String[] returnStrArr = new String[2];
		while (true) {
			lengthMax = fileListMax.length-1;
			max = fileListMax[0];
			for (int i = 1; i < lengthMax; i++) {
				tempName = fileListMax[i];
				if (max.getName().compareTo(tempName.getName()) < 0) {
					max = tempName;
				}
			}

			lengthMin = fileListMin.length;
			min = fileListMin[0];
			for (int i = 1; i < lengthMin; i++) {
				tempName = fileListMin[i];
				if (min.getName().compareTo(tempName.getName()) > 0) {
					min = tempName;
				}
			}
			if (max.isDirectory()) {
				fileListMax = max.listFiles();
				fileListMin = min.listFiles();
			} else {
				returnStrArr[0] = max.getName();
				returnStrArr[1] = min.getName();
				break;
			}

		}
		return returnStrArr;
	}
}
