package com.cqut.action.memorial;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class UpdateMemorial {
	private String memorialId;
	private String memorialDate;
	private String memorialTime;
	private String memorialPosition;
	private String memorialText;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getMemorialId() {
		return memorialId;
	}

	@SuppressWarnings("deprecation")
	public void setMemorialId(String memorialId) {
		this.memorialId = URLDecoder.decode(memorialId);
	}

	public String getMemorialDate() {
		return memorialDate;
	}

	@SuppressWarnings("deprecation")
	public void setMemorialDate(String memorialDate) {
		this.memorialDate = URLDecoder.decode(memorialDate);
	}

	public String getMemorialTime() {
		return memorialTime;
	}

	@SuppressWarnings("deprecation")
	public void setMemorialTime(String memorialTime) {
		this.memorialTime = URLDecoder.decode(memorialTime);
	}

	public String getMemorialPosition() {
		return memorialPosition;
	}

	public void setMemorialPosition(String memorialPosition) {
		try {
			this.memorialPosition = URLDecoder
					.decode(memorialPosition, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getMemorialText() {
		return memorialText;
	}

	public void setMemorialText(String memorialText) {
		try {
			this.memorialText = URLDecoder.decode(memorialText, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void exe() {
		if (memorialId != null && memorialDate != null && memorialTime != null
				&& memorialPosition != null && memorialText != null) {
			String sql = "update memorial set memorialDate = ?, memorialPosition = ?, memorialTime = ?, memorialText = ? where memorialId = ?";
			Object[] param = { memorialDate, memorialPosition, memorialTime,
					memorialText, memorialId };
			commonDao.execute(sql, param);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
