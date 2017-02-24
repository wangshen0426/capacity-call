package com.cqut.action;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.cqut.action.messageDeal.MessageDeal;
import com.cqut.util.SystemUtil;

public class Init implements InitializingBean {
	@Resource
	private MessageDeal messageDeal;

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				messageDeal.init();
				// 定时删除文件
			}
		}).start();

		/*Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// 定时请求未发送的数据
				// messageDeal.init();
				messageDeal.getData(Integer.parseInt(SystemUtil
						.getSystemParameter("parallelMaxNumber")));
			}
		};

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.MINUTES);*/
	}

}
