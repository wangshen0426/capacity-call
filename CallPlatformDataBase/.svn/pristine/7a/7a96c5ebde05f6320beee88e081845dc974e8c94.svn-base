package com.cqut.service.receipt;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import com.cqut.dao.common.ICommonDao;
import com.cqut.dao.receipt.customInterface.IReceiptEntityDao;
import com.cqut.dao.receipt.customInterface.IReceiptMapDao;
import com.cqut.entity.receipt.Receipt;

import com.cqut.service.receipt.customInterface.IReceiptService;

@Controller  
@RemoteProxy
public class ReceiptService implements IReceiptService {

	@Resource(name = "receiptMapDao")
	private IReceiptMapDao mapDao;
	@Resource(name = "receiptEntityDao")
	private IReceiptEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(String[] properties,
			String condition, String sortField, String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findReceipts(properties, condition, sortField, order, needLink, ((curPage-1)*limit), limit);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findReceipts(properties, condition, sortField, order, needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findReceipts(properties, condition, "", "", needLink, -1, -1);
		
		return data;
	}
	
	@RemoteMethod
	public Map<String, Object> getReceipt(String[] properties,
			String condition, boolean needLink){
		List<Map<String, Object>> data = mapDao.findReceipts(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? data.get(0) : null;
	}
	
	public Receipt getReceiptEntity(String[] properties,
			String condition, boolean needLink){
			List<Map<String, Object>> data = mapDao.findReceipts(properties, condition, "", "", needLink, -1, -1);
		
		return data != null && data.size() > 0 ? new Receipt(data.get(0)) : null;
	}
	
	@RemoteMethod
	public int findCountByProperties(String[] properties,
			String condition, boolean needLink){
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}
	
	public List<Receipt> findReceiptByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findReceipts(properties, condition, needLink, index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(Receipt.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RemoteMethod
	public boolean deleteByEntity(Receipt value) {
		return deleteById(value.getReceiptId());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(Receipt.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(Receipt[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (Receipt item : values) {
			ids[index++] = item.getReceiptId();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new Receipt(value));
	}

	@RemoteMethod
	public boolean saveEntity(Receipt value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new Receipt(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Receipt value) {
		Receipt receipt = (Receipt) commonDao.merge(value);
		if (receipt != null) {
			return receipt.getProperties();
		}
		return null;
	}
	
	@RemoteMethod
	public boolean updateEntity(Receipt data, String condition) {
		if(mapDao.updateReceipt(data.getProperties(), condition)==1){
			return true;
		}
		return false;
	}
	

}
