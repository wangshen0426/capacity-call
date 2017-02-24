package com.cqut.dao.receipt;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.receipt.Receipt;

import com.cqut.dao.receipt.customInterface.IReceiptMapDao;

@Component
public class ReceiptMapDao extends BaseDao implements IReceiptMapDao {

	
	public Class<?> getEntity() {
		return Receipt.class;
	}

	public List<Map<String, Object>> findReceipts(String[] property,
			String condition, String sortField, String order, boolean needLink,
			int index, int limit) {
		return get(property, condition, sortField, order, needLink, index, limit);
	}

	
	public int findCount(String[] properties, String condition, boolean needLink) {
		return getCount(properties, condition, needLink);
	}
	
	
	public int updateReceipt(Map<String, Object> properties, String condition) {
		return this.updateProperties(properties, condition);
	}

}