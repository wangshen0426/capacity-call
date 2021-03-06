package com.cqut.action.callList;

import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class GetUserReceipt {
	private String callKey;
	private String telnum;
	private String voicetaskcode;
	private String allTime;// 秒数
	// private String isAll;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getCallKey() {
		return callKey;
	}

	@SuppressWarnings("deprecation")
	public void setCallKey(String callKey) {
		this.callKey = URLDecoder.decode(callKey);
	}

	public String getTelnum() {
		return telnum;
	}

	@SuppressWarnings("deprecation")
	public void setTelnum(String telnum) {
		this.telnum = URLDecoder.decode(telnum);
	}

	public String getVoicetaskcode() {
		return voicetaskcode;
	}

	@SuppressWarnings("deprecation")
	public void setVoicetaskcode(String voicetaskcode) {
		this.voicetaskcode = URLDecoder.decode(voicetaskcode);
	}

	public String getAllTime() {
		return allTime;
	}

	@SuppressWarnings("deprecation")
	public void setAllTime(String allTime) {
		this.allTime = URLDecoder.decode(allTime);
	}

	// public String getIsAll() {
	// return isAll;
	// }
	//
	// @SuppressWarnings("deprecation")
	// public void setIsAll(String isAll) {
	// this.isAll = URLDecoder.decode(isAll);4
	// }

	public void exe() {
		if (callKey != null && telnum != null && voicetaskcode != null
				&& allTime != null) {
			String[] sqls = new String[2];

			Object[][] params = new Object[2][];
			// if (isAll.equals("true")) {
			String taskvoiceSql = "update taskvoice set sendState = ? where taskVoiceId = ?";
			Object[] taskvoiceParam = { 0, voicetaskcode };

			sqls[1] = taskvoiceSql;
			params[1] = taskvoiceParam;
			// } else {
			// sqls = new String[1];
			// params = new Object[1][];
			// }

			// 此处增添回复人的呼叫时间、回复按键、回复时间
			String receiptSql = "UPDATE receipt SET getAllTime = ?, receiptKey = ?, receiptTime = ? WHERE receiptMan in (SELECT linkUserId FROM linkman WHERE linkmanPhoneNumber = ?)AND taskVoiceId = ? and recallTime = (SELECT a.recallTime from (select MAX(recallTime) recallTime from receipt where taskVoiceId = ?) a) ";
			Object[] receiptParam = { allTime, callKey,
					System.currentTimeMillis(), telnum, voicetaskcode,
					voicetaskcode };
			sqls[0] = receiptSql;
			params[0] = receiptParam;
			commonDao.executeForQueueParam(sqls, params);

			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("{}");
		}
	}
}
