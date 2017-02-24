package com.cqut.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class PortPerTask {
	private int port;
	private AtomicInteger taskNumber;
	private volatile boolean isFirst = true;
	public PortPerTask(int taskNumber,int port) {
		this.taskNumber =new AtomicInteger(taskNumber);
		this.port = port;
	}
	public void decrement() {
		taskNumber.decrementAndGet();
	}
	
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isEmpty() {
		return taskNumber.get()==0;
	}
	public int getPort() {
		return port;
	}
	
}
