package util;

import java.io.File;

import java.util.LinkedList;

public class FindFileNum {
	public static int findFileNames(String path) {
		int fileNum = 0;
		File file = new File(path);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					list.add(file2);
					
				}else{
					fileNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						list.add(file2);
						
					}else{
						fileNum++;
					}
				}
			}

			return fileNum;
		} else {
			return 0;
		}
	}
}
