package com.cqut.dao.receipt.customInterface;

import java.util.List;
import java.util.Map;

public interface IReceiptMapDao {

	public List<Map<String, Object>> findReceipts(String[] property,
			String condition, String sortField, String order, 
			boolean needLink, int index, int limit);
	
	public int findCount(String[] properties, String condition, boolean needLink);
	
	public int updateReceipt(Map<String, Object> properties, String condition);
}
