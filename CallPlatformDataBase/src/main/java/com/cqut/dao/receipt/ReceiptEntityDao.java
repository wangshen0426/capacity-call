package com.cqut.dao.receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cqut.dao.BaseDao;
import com.cqut.entity.receipt.Receipt;

import com.cqut.dao.receipt.customInterface.IReceiptEntityDao;

@Component
public class ReceiptEntityDao extends BaseDao implements IReceiptEntityDao {

	@Override
	public Class<?> getEntity() {
		return Receipt.class;
	}
	
	public List<Receipt> findReceipts(String[] property, String condition,
			boolean needLink, int index, int limit) {
		List<Map<String, Object>> list = get(property, condition, needLink, index, limit);
		List<Receipt> results = new ArrayList<Receipt>(list.size());
		for(Map<String, Object> item : list){
			results.add(new Receipt(item));
		}
		return results;
	}

}
