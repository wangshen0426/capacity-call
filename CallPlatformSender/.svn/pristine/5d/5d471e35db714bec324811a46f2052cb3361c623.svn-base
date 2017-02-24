package com.cqut.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.cqut.entity.ExchangeMessage;
import com.cqut.util.SystemUtil;

public class WriteFile {
	private static String basePath = SystemUtil.getSystemParameter("voiceBasePath");
	private static  String mainBasePath  = SystemUtil.getSystemParameter("customizedBasePath");
	public static String[] file(List<ExchangeMessage> ems){
		int size = ems.size();
		ExchangeMessage em;
		StringBuilder sb;
		String[] fileNameList = new String[size];
		for(int i = 0;i < size;i++) {
			em = ems.get(i);
			if(em.type.equals(FileRequest.VOICETYPE)) {
				sb = new StringBuilder();
				sb.append(em.taskVoiceCode);
				sb.append("_");
				sb.append(System.nanoTime());
				sb.append("_sound.wav");
				String fileName = sb.toString();
				StringBuilder all = new StringBuilder();
				all.append(basePath);
				all.append(File.separatorChar);
				all.append(fileName);
				writeFile(em.getFile(),all.toString());
				fileNameList[i] = fileName;
			}
			else {
				fileNameList[i] = em.fileName;
			}
		}
		
		return fileNameList;
	}
	
	
	public static byte[] toBytes(String str) {
		String[] temps = str.split(",");
		int length = temps.length;
		byte[] bytes = new byte[length];
		bytes[0] = Byte.parseByte(temps[0].substring(1));
		bytes[length-1] = Byte.parseByte(temps[length-1].substring(0,temps[length-1].length()-1));
		for(int i = 1;i < length-1;i++) {
			bytes[i] =  Byte.parseByte(temps[i]);
		}
		return bytes;
	}
	
	private static void writeFile(byte[] file,String fileName) {
		BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File files = null;  
        try {  
            files = new File(fileName);  
            if(files.getParentFile().exists()==false)
            	files.getParentFile().mkdirs();
            fos = new FileOutputStream(files);  
            bos = new BufferedOutputStream(fos);  
            bos.write(file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
	}
	
	public static void writeContentFile(String fileName,String[][] keyMap,String voiceFileName,String taskCode,String type){
		BufferedWriter bw = null;
		try {
			StringBuilder filesNameSB = new StringBuilder();
			filesNameSB.append(mainBasePath);
			filesNameSB.append(File.separatorChar);
			filesNameSB.append(fileName);
			File files = new File(filesNameSB.toString());
			 if(files.getParentFile().exists()==false)
	            	files.getParentFile().mkdirs();
			bw = new BufferedWriter(new FileWriter(files));
			StringBuilder line1 = new StringBuilder();
			if(type.equals(FileRequest.VOICETYPE)) {
				line1.append("0,1,");
				line1.append(voiceFileName);
			}
			else{
				line1.append("0,2,");
				if(voiceFileName.length() > 64) {
					line1.append(voiceFileName.subSequence(0, 64));
				}
				else {
					line1.append(voiceFileName);
				}
			}
			int keyMapLength = keyMap.length;
			if(keyMap!=null && keyMapLength > 0) {
				line1.append(",AUTO,0-1,0");
				StringBuilder keyAllBuffer = new StringBuilder();
				String[] keyStrArrTemp;
				for(int i = 0; i < keyMapLength;i++) {
					keyStrArrTemp = keyMap[i];
					if(keyStrArrTemp!=null) {
						keyAllBuffer.append(keyStrArrTemp[1]);
						keyAllBuffer.append("请按");
						keyAllBuffer.append(keyStrArrTemp[0]);
					}
				}
				int keyAllBufferSize = keyAllBuffer.length();
				List<String> keyWord = new ArrayList<String>(keyAllBufferSize/64+1); 
				int index = 0;
				int diviSize = 64;
				while(true) {
					if(index+diviSize>keyAllBufferSize) {
						keyWord.add(keyAllBuffer.substring(index, keyAllBufferSize));
						break;
					}
					keyWord.add(keyAllBuffer.substring(index, index+diviSize));
					index+=diviSize;
				}
				int keyRealSize = keyWord.size();
				List<String> ivrList = new ArrayList<String>(keyRealSize);  
				StringBuilder process = new StringBuilder();
				process.append("0-1");
				for(int i = 0;i < keyRealSize-1;i++) {
					StringBuilder sb = new StringBuilder();
					sb.append(process);
					sb.append(",2,");
					sb.append(keyWord.get(i));
					process.append("-1");
					sb.append(",AUTO,");
					sb.append(process.toString());
					sb.append(",0");
					ivrList.add(sb.toString());
				}
				StringBuilder sbEnd = new StringBuilder();
				sbEnd.append(process);
				sbEnd.append(",2,");
				sbEnd.append(keyWord.get(keyRealSize-1));
				process.append("-1");
				sbEnd.append(",IVR,");
				sbEnd.append(process.toString());
				sbEnd.append(",1");
				ivrList.add(sbEnd.toString());
				StringBuilder line3 = new StringBuilder();
				line3.append(process.toString()+",5,");
				line3.append(SystemUtil.getSystemParameter("recallAdress"));
				line3.append("&voicetaskcode=");
				line3.append(taskCode);
				line3.append(",");
				line3.append(SystemUtil.getSystemParameter("recallParam"));
				line3.append(",,");
				bw.write(line1.toString());
				int sizeMain = ivrList.size();
				for(int i = 0;i < sizeMain;i++) {
					bw.newLine();
					bw.write(ivrList.get(i));
				}
				bw.newLine();
				bw.write(line3.toString());
			}
			else {
				line1.append(",AUTO,-1,0");
				bw.write(line1.toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void writeReceiveCSV(String fileName,String[] popReceive) {
		BufferedWriter bw = null;
		try {
			File tempFile = new File(fileName);
			if(tempFile.getParentFile().exists()==false)
				tempFile.getParentFile().mkdirs();
			bw =new BufferedWriter(new FileWriter(fileName));
			bw.write("number,receiver");
			if(popReceive!=null) {
				StringBuilder sb;
				for(int i = 0;i < popReceive.length;i++) {
					bw.newLine();
					sb = new StringBuilder();
					sb.append(i);
					sb.append(",");
					sb.append(popReceive[i]);
					bw.write(sb.toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
