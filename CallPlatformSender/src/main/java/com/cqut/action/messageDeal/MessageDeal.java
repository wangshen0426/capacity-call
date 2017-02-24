package com.cqut.action.messageDeal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cqut.entity.ExchangeMessage;
import com.cqut.entity.PortPerTask;
import com.cqut.service.DBAction.DateBaseService;
import com.cqut.service.YZ_GroupCall.customInterface.IY_GroupCallService;
import com.cqut.util.DateUtil;
import com.cqut.util.FileRequest;
import com.cqut.util.HttpRequest;
import com.cqut.util.RequestEntityDataHTTP;
import com.cqut.util.SystemUtil;
import com.cqut.util.WriteFile;

@Component
public class MessageDeal {
	private static volatile ConcurrentMap<String, String> taskCount = new ConcurrentHashMap<String, String>();
	private static volatile boolean isSelecting = false;
	public static final String databaseURL = SystemUtil
			.getSystemParameter("databaseServerURL");
	public static final String fileURL = SystemUtil
			.getSystemParameter("fileServerURL");
	public static final long oneceStopOtherGoTime = Long.parseLong(SystemUtil
			.getSystemParameter("oneceStopOtherGoTime"));
	public static final int getNotReceiptTaskTime = Integer.parseInt(SystemUtil
			.getSystemParameter("getNotReceiptTaskTime"));
	public static final String notReceiptServerURL = SystemUtil
			.getSystemParameter("notReceiptServerURL");

	@Resource
	private DateBaseService dateBaseService;
	@Resource(name = "groupCallService")
	private IY_GroupCallService groupCallService;
	private static final int parallelMaxNumber = Integer.parseInt(SystemUtil
			.getSystemParameter("parallelMaxNumber"));

	public void deal(List<ExchangeMessage> emList, int[] portList) {
		String[] fileNameList = WriteFile.file(emList);
		dateBaseService.DBOperation(emList, fileNameList, portList);
	}

	private void addToCountMap(List<ExchangeMessage> emList, int[] portList) {
		if (emList != null && emList.size() > 0) {
			int length = emList.size();
			for (int i = 0; i < length; i++) {
				taskCount.put(emList.get(i).getCsvFileName(), emList.get(i)
						.getTaskVoiceCode());
			}
		}

	}

	private void reMain() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(isSelecting == false) {
					ConcurrentMap<String, String> temp = taskCount;
					taskCount = new ConcurrentHashMap<String, String>();
					int tempSize = temp.size();
					if(tempSize > 0) {
						String[] str = new String[tempSize];
						int index = 0;
						Iterator<Entry<String, String>> iterator = temp.entrySet()
								.iterator();
						while (iterator.hasNext()) {
							Entry<String, String> entry = iterator.next();
							str[index] = entry.getKey();
							index++;
						}
						Map<String, Object> map = groupCallService
								.selectNotReceipt(str);
						if(map!=null && map.size() > 0) {
							Iterator<Entry<String, String>> iterator2 = temp.entrySet()
									.iterator();
							Map<String, String> sendMap = new HashMap<String, String>();
							while (iterator2.hasNext()) {
								Entry<String, String> entry = iterator2.next();
								if (map.get(entry.getKey()) == null) {
									taskCount.put(entry.getKey(), entry.getValue());
								} else {
									String taskVoiceCode = entry.getValue();
									String allTime = ((Object[]) map.get(entry.getKey()))[0]
											+ "";
									sendMap.put(taskVoiceCode, DateUtil.dataString2Seconds(allTime));
								}
							}

							if (sendMap != null && sendMap.size() > 0) {
								String[][] param = { { "json", sendMap.toString() } };
								HttpRequest.sendPost(notReceiptServerURL, param);
							}

							Iterator<Entry<String, Object>> iterator3 = map.entrySet()
									.iterator();
							while (iterator3.hasNext()) {
								Entry<String, Object> entry = iterator3.next();
								Object[] oo = (Object[]) entry.getValue();
								getDataForPort(1, Integer.parseInt(oo[1] + ""));
							}
						}
						else {
							taskCount.putAll(temp);
						}
					}
					
				}
				
			}
		};

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, getNotReceiptTaskTime, getNotReceiptTaskTime,
				TimeUnit.SECONDS);
	}

	public void init() {
		reMain();
		getData(parallelMaxNumber);
	}

	public void getDataForPorts(int[] port) {
		isSelecting = true;
		try {
			while (true) {
				List<ExchangeMessage> list = getDataFromServer(port.length);
				if (list != null && list.size() > 0) {
					addToCountMap(list, port);
					deal(list, port);
					break;
				} else {
					try {
						Thread.sleep(oneceStopOtherGoTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		finally {
			isSelecting = false;
		}
	}

	public void getDataForPort(int length, int port) {
		isSelecting = true;
		try {
			while (true) {
				List<ExchangeMessage> list = getDataFromServer(length);
				if (list != null && list.size() > 0) {
					int[] portList = new int[length];
					for (int i = 0; i < length; i++) {
						portList[i] = port;
					}
					addToCountMap(list, portList);
					deal(list, portList);
					break;
				} else {
					try {
						Thread.sleep(oneceStopOtherGoTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
		finally {
			isSelecting = false;
		}
	}

	public void getData(int length) {
		while (true) {
			List<ExchangeMessage> list = getDataFromServer(length);
			if (list != null && list.size() > 0) {
				int[] portList = new int[length];
				int port = 0;
				for (int i = 0; i < length; i++) {
					portList[i] = port;
					port++;
					if (port == parallelMaxNumber) {
						port = 0;
					}
				}
				addToCountMap(list, portList);
				deal(list, portList);
				break;
			} else {
				try {
					Thread.sleep(oneceStopOtherGoTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private List<ExchangeMessage> getDataFromServer(int length) {
		String[][] param = new String[1][2];
		param[0][0] = "limit";
		param[0][1] = length + "";
		List<ExchangeMessage> list = RequestEntityDataHTTP.sendGet(databaseURL,
				param, ExchangeMessage.class);
		if (list != null) {
			FileRequest.sendPost(fileURL, list);
			int size = list.size();
			for (int i = 0; i < size; i++) {
				StringBuilder receiveCSVFileNameSB = new StringBuilder();
				// receiveCSVFileNameSB.append(emList.get(i).taskVoiceCode);
				receiveCSVFileNameSB.append((int) (Math.random() * 9) + 1);
				receiveCSVFileNameSB.append("_");
				receiveCSVFileNameSB.append(System.nanoTime());
				receiveCSVFileNameSB.append("_receive.csv");
				String receiveCSVFileName = receiveCSVFileNameSB.toString();
				list.get(i).setCsvFileName(receiveCSVFileName);
			}
		}
		return list;
	}

}
