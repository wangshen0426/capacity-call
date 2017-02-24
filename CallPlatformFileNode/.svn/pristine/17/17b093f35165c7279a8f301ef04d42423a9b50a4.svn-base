package util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FindFileAbsolutePath {
	public static List<String> findFilePath(File file) {
		List<String> list = new ArrayList<String>();
		if (file.exists()) {
			LinkedList<File> linkedList = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					linkedList.add(file2);

				} else {
					list.add(file2.getAbsolutePath());
				}
			}
			File temp_file;
			while (!linkedList.isEmpty()) {
				temp_file = linkedList.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						linkedList.add(file2);
					} else {
						list.add(file2.getAbsolutePath());
					}
				}
			}

			return list;
		} else {
			return null;
		}
	}
}
