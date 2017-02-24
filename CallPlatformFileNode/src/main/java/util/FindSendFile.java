package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindSendFile {
	public static int sendNum;
	public static int needDeleteNum;
	public static List<String> sendFile = new ArrayList<String>();
	public static boolean isSort = true;
	public static List<String> needDeleteFile = new ArrayList<String>();

	public static void intoFile(List<File> fileList) {
		int length = fileList.size();
		if (isSort) {
			positiveSort(fileList);
		} else {
			invertedSort(fileList);
		}
		for (int i = 0; i < length; i++) {
			if (fileList.get(i).isDirectory()) {
				if (sendNum <= 0) {
					break;
				} else if (FindFileNum.findFileNames(fileList.get(i)
						.getAbsolutePath()) >= sendNum
						&& fileList.get(i).isDirectory()) {
					intoFile(Arrays.asList(fileList.get(i).listFiles()));
					break;
				} else {
					sendNum -= FindFileNum.findFileNames(fileList.get(i)
							.getAbsolutePath());
					List<String> list = FindFileAbsolutePath
							.findFilePath(fileList.get(i));
					int len = list.size();
					for (int num = 0; num < len; num++) {
						sendFile.add(list.get(num));
					}
				}
			} else {
				File[] fl = fileList.get(i).getParentFile().listFiles();
				if (isSort) {
					positiveSort(Arrays.asList(fl));
				} else {
					invertedSort(Arrays.asList(fl));
				}
				int len = fl.length;
				for (int num = 0; num < len; num++) {
					sendFile.add(fl[num].getAbsolutePath());
				}
				break;
			}
		}
	}

	public static void positiveSort(List<File> fileList) {
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	public static void invertedSort(List<File> fileList) {
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o2, File o1) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	public static List<String> getSendFile() {
		return sendFile;
	}

	public static void clear() {
		needDeleteFile = sendFile;
		needDeleteNum = sendNum;
		sendNum = 0;
		sendFile = new ArrayList<String>();
		isSort = true;
	}

	public static void deleteFile() {
		for (int i = 0; i < needDeleteNum; i++) {
			File fl = new File(needDeleteFile.get(i));
			fl.delete();
		}
	}

	public static void deleteEmptyDir(File dir) {
		if (dir.isDirectory()) {
			File[] fs = dir.listFiles();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					File tmpFile = fs[i];
					if (tmpFile.isDirectory()) {
						deleteEmptyDir(tmpFile);
					}
					if (tmpFile.isDirectory()
							&& tmpFile.listFiles().length <= 0) {
						tmpFile.delete();
					}
				}
			}
			if (dir.isDirectory() && dir.listFiles().length == 0) {
				dir.delete();
			}
		}
	}
}
