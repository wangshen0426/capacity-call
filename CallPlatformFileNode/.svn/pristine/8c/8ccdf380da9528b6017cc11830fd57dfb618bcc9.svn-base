package util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

public class FileNameToFile {
	public static File fileNameToFile(String fileName) {
		String[] fileNameStr = fileName.split("_");
		if (fileNameStr.length == 3) {
			long currentTime = Long.parseLong(fileNameStr[2]);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(currentTime);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			StringBuilder fileNameStb = new StringBuilder();
			fileNameStb.append(GetFileName.BASEPATH);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(fileNameStr[0]);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(fileNameStr[1]);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(year);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(month);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(day);
			fileNameStb.append(File.separatorChar);
			fileNameStb.append(fileName);
			fileNameStb.append(".wav");
			File file = new File(fileNameStb.toString());
			return file;
		}

		return null;
	}

	public static byte[] fileToByteArr(File file) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));

			byte[] temp = new byte[in.available()];
			while (in.read(temp) != -1)
				;

			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ByteArrayInputStream fileToStream(File file) {
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(fileToByteArr(file));

			while (in.read() != -1)
				;

			return in;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String fileNameToPath(String fileName) {
		String[] fileNameStr = fileName.split("_");
		if (fileNameStr.length == 3) {
			long currentTime = Long.parseLong(fileNameStr[2]);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(currentTime);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			StringBuilder fileNameStb = new StringBuilder();
			fileNameStb.append(GetFileName.READFILEIP);
			fileNameStb.append(fileNameStr[0]);
			//fileNameStb.append(File.separatorChar);
			fileNameStb.append("/");
			fileNameStb.append(fileNameStr[1]);
			//fileNameStb.append(File.separatorChar);
			fileNameStb.append("/");
			fileNameStb.append(year);
			//fileNameStb.append(File.separatorChar);
			fileNameStb.append("/");
			fileNameStb.append(month);
			//fileNameStb.append(File.separatorChar);
			fileNameStb.append("/");
			fileNameStb.append(day);
			//fileNameStb.append(File.separatorChar);
			fileNameStb.append("/");
			fileNameStb.append(fileName);
			fileNameStb.append(".wav");
			return fileNameStb.toString();
		}

		return null;
	}
	
}
