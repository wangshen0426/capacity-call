package com.cqut.action.getLink;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.cqut.dao.common.ICommonDao;
import com.cqut.util.JJCommon;

public class RespondMessage {
	private String informationesId;
	private String receiptType;
	private String groupId;
	private String userId;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	public String getGroupId() {
		return groupId;
	}

	@SuppressWarnings("deprecation")
	public void setGroupId(String groupId) {
		this.groupId = URLDecoder.decode(groupId);
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("deprecation")
	public void setUserId(String userId) {
		this.userId = URLDecoder.decode(userId);
	}

	public String getInformationesId() {
		return informationesId;
	}

	@SuppressWarnings("deprecation")
	public void setInformationesId(String informationesId) {
		this.informationesId = URLDecoder.decode(informationesId);
	}

	public String getReceiptType() {
		return receiptType;
	}

	@SuppressWarnings("deprecation")
	public void setReceiptType(String receiptType) {
		this.receiptType = URLDecoder.decode(receiptType);
	}

	public void exe() {
		if (informationesId != null && receiptType != null && groupId != null
				&& userId != null) {
			String[] sqls;
			Object[] params;
			// 回应同意‘1’
			if (Integer.parseInt(receiptType) == 1) {
				String originUserIdSql = "select originUserId from informationes where informationesId = ?";
				Object[] originUserIdParam = { informationesId };
				List<Object> list = commonDao.executeAndReturn(originUserIdSql,
						originUserIdParam);
				String originUserId = list.get(0).toString();

				Long currentTime = System.currentTimeMillis();
				StringBuilder groupMemberIdStb = new StringBuilder();
				groupMemberIdStb.append(currentTime);
				groupMemberIdStb.append(groupId);
				groupMemberIdStb.append(originUserId);

				String insertGroupesSql = "insert into groupmember (groupMemberId, groupId, groupUserId) VALUES (?,?,?)";
				Object[] insertGroupesParam = { groupMemberIdStb.toString(),
						groupId, originUserId };
				sqls = new String[3];
				sqls[1] = insertGroupesSql;
				params = new Object[3][];
				params[1] = insertGroupesParam;

				StringBuilder myGroupesListId = new StringBuilder();
				myGroupesListId.append(currentTime);
				myGroupesListId.append(originUserId);
				String insertMygroupSql = "insert into mygroupeslist (myGroupesListId, groupesId, userId) values (?, ?, ?)";
				Object[] insertMygroupParam = { myGroupesListId.toString(),
						groupId, originUserId };
				sqls[2] = insertMygroupSql;
				params[2] = insertMygroupParam;
			} else {
				sqls = new String[1];
				params = new Object[1][];
			}

			Long currentTime = System.currentTimeMillis();
			String updateSql = "update informationes set receiptType = ?, receiptTime = ? where informationesId = ?";
			Object[] updateParam = { receiptType, currentTime, informationesId };

			sqls[0] = updateSql;
			params[0] = updateParam;
			commonDao.executeForQueueParam(sqls, (Object[][]) params);
			JJCommon.sendMessageToJS("true");
		} else {
			JJCommon.sendMessageToJS("");
		}
	}
}
