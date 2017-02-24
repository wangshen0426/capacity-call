package com.cqut.dataSource;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class MyBasicDataSource extends BasicDataSource {
	
	/**
	 * 重写BasicDataSource，解决在LINUX下数据泄露的问题
	 * @author 王兴龙
	 */
	@Override  
  public synchronized void close() throws SQLException {  
      DriverManager.deregisterDriver(DriverManager.getDriver(url));  
      super.close();  
  }  


}
