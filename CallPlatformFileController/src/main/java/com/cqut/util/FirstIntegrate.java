package com.cqut.util;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import util.GetSystemParam;
import util.HttpRequest;

public class FirstIntegrate {
	private static volatile boolean isFirst = false;
	private static volatile boolean isAdd = true;
	private static ConcurrentMap<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();
	private static ConcurrentMap<String, Integer> sendMap = new ConcurrentHashMap<String, Integer>();
	private static ArrayList<String> keyArr;
	private static List<String> originalServer = new ArrayList<String>();
	private static List<String> sendServer = new ArrayList<String>();
	private static ConcurrentMap<String, String> serverIpOrName = new ConcurrentHashMap<String, String>();

	private final static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			isAdd = false;
			// 整合
			integrate();

			// 发送
			send("false");
		}
	};

	public static void addMap(String serverName, int fileNum, String serverIp) {
		if (isAdd) {
			if (isFirst == false) {
				isFirst = true;

				ScheduledExecutorService service = Executors
						.newSingleThreadScheduledExecutor();
				service.schedule(runnable,
						GetSystemParam.FIRSTINTEGRATETIMEDELAY,
						TimeUnit.MINUTES);
			}

			serverIpOrName.put(serverName, serverIp);
			countMap.put(serverName, fileNum);
		}
	}

	private static void clear() {
		isFirst = false;
		isAdd = true;
		countMap = new ConcurrentHashMap<String, Integer>();
		sendMap = new ConcurrentHashMap<String, Integer>();
		keyArr.clear();
		originalServer.clear();
		sendServer.clear();
	}

	@SuppressWarnings("unchecked")
	private static void integrate() {
		// keyArr = countMap.keySet().toArray();
		// Arrays.sort(keyArr);
		keyArr = new ArrayList(countMap.keySet());
		Collections.sort(keyArr, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {

				// 按照value的值降序排列，若要升序，则这里小于号换成大于号
				if (Double.parseDouble(countMap.get(o1).toString()) < Double
						.parseDouble(countMap.get(o2).toString()))
					return 1;

				else if (Double.parseDouble(countMap.get(o1).toString()) == Double
						.parseDouble(countMap.get(o2).toString()))
					return 0;

				else
					return -1;
			}
		});

		int length = keyArr.size();
		int countNum = 0;
		int[] valArr = new int[length];
		for (int i = 0; i < length; i++) {
			valArr[i] = countMap.get(keyArr.get(i));
			countNum += valArr[i];
		}

		int avg = (int) Math.ceil(countNum / length);

		for (int i = 0; i < countMap.size(); i++) {
			if (countMap.get(keyArr.get(i)) > avg) {

				if (i != countMap.size() - 1) {
					sendMap.put(keyArr.get(i + 1), countMap.get(keyArr.get(i))
							- avg);
					originalServer.add(keyArr.get(i));
					sendServer.add(keyArr.get(i + 1));
					countMap.put(keyArr.get(i + 1), countMap.get(keyArr.get(i))
							- avg + countMap.get(keyArr.get(i + 1)));
					countMap.put(keyArr.get(i), avg);
				}
			}
		}

		for (int i = countMap.size() - 1; i >= 0; i--) {
			if (countMap.get(keyArr.get(i)) > avg) {

				if (i != 0) {
					sendMap.put(keyArr.get(i - 1), countMap.get(keyArr.get(i))
							- avg);
					originalServer.add(keyArr.get(i));
					sendServer.add(keyArr.get(i - 1));

					countMap.put(keyArr.get(i - 1), countMap.get(keyArr.get(i))
							- avg + countMap.get(keyArr.get(i - 1)));
					countMap.put(keyArr.get(i), avg);
				}
			}
		}
	}

	public static void send(String isSend) {
		if (isSend.equals("true")) {
			sendMap.remove(sendServer.get(0));
			originalServer.remove(0);
			sendServer.remove(0);
			keyArr.remove(0);
			if (sendMap != null && sendMap.size() > 0) {
				String[][] param = {
						{ "sendServerIp", serverIpOrName.get(sendServer.get(0)) },
						{ "sendNum", sendMap.get(sendServer.get(0)) + "" },
						{ "sendServerName", sendServer.get(0) } };
				HttpRequest.sendPost(serverIpOrName.get(originalServer.get(0)),
						param);
			} else {
				clear();
			}
		} else if (isSend.equals("false")) {
			if (sendMap != null && sendMap.size() > 0) {
				String[][] param = {
						{ "sendServerIp", serverIpOrName.get(sendServer.get(0)) },
						{ "sendNum", sendMap.get(sendServer.get(0)) + "" },
						{ "sendServerName", sendServer.get(0) } };
				HttpRequest.sendPost(serverIpOrName.get(originalServer.get(0)),
						param);
			}
		}
	}
}
