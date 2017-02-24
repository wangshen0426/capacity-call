package com.cqut.action;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;

import util.FindFileName;
import util.GetFileName;
import util.HttpRequest;

public class Init implements InitializingBean {
	private ScheduledFuture<?> future = null;
	@Override
	public void afterPropertiesSet() throws Exception {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				sendInit();
			}
		};
		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		long currentTime = System.currentTimeMillis();
		long zero = currentTime / (1000 * 3600 * 24) * (1000 * 3600 * 24)
				- TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000;// 第二天零点的毫秒数
		long disTime = 24 * 60 * 60 * 1000 + twelve - currentTime;// 距第二个凌晨的毫秒数

		service.scheduleAtFixedRate(runnable, disTime,
				GetFileName.INTERVALCONFORMITYTIME, TimeUnit.MILLISECONDS);

		 ScheduledExecutorService service2 = Executors
				.newSingleThreadScheduledExecutor();
		 future = service2.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				if(sendInit()) {
					future.cancel(true);
				}
				
			}
		}, 0,10, TimeUnit.SECONDS);
		
	}

	private boolean sendInit() {
		String result ;
		String[] secondList = FindFileName.findSmallestOrBigestFileName();
		if (secondList != null) {
			String[][] strArr = { { "minId", secondList[1] },
					{ "maxId", secondList[0] },
					{ "serverName", GetFileName.SERVERNAME },
					{ "serverIp", GetFileName.SERVERIP },
					{ "serverLargeIp", GetFileName.SERVERLARGEIP },
					{ "updateIp", GetFileName.UPDATEIP }
					};
			result = HttpRequest.sendPost(GetFileName.MAINSERVER, strArr);
		} else {
			String[][] strArr = { { "serverName", GetFileName.SERVERNAME },
					{ "serverIp", GetFileName.SERVERIP },
					{ "serverLargeIp", GetFileName.SERVERLARGEIP } ,
					{ "updateIp", GetFileName.UPDATEIP }};
			result = HttpRequest.sendPost(GetFileName.MAINSERVER, strArr);
		}
		return !result.equals(HttpRequest.NULL);
	}
}
