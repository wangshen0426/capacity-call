package com.cqut.service.YZ_GroupCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;

import com.cqut.dao.YZ_GroupCall.customInterface.IGroupCallEntityDao;
import com.cqut.dao.YZ_GroupCall.customInterface.IGroupCallMapDao;
import com.cqut.dao.common.ICommonDao;
import com.cqut.entity.YZ_GroupCall.YZ_GroupCall;
import com.cqut.service.YZ_GroupCall.customInterface.IY_GroupCallService;

@Controller
@RemoteProxy
public class GroupCallService implements IY_GroupCallService {

	@Resource(name = "groupCallMapDao")
	private IGroupCallMapDao mapDao;
	@Resource(name = "groupCallEntityDao")
	private IGroupCallEntityDao entityDao;
	@Resource(name = "commonDao")
	private ICommonDao commonDao;

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesWithLimit(
			String[] properties, String condition, String sortField,
			String order, boolean needLink, int curPage, int limit) {
		List<Map<String, Object>> data = mapDao.findYZ_GroupCalls(properties,
				condition, sortField, order, needLink, ((curPage - 1) * limit),
				limit);

		return data;
	}

	@RemoteMethod
	public List<Map<String, Object>> findMapByProperties(String[] properties,
			String condition, String sortField, String order, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findYZ_GroupCalls(properties,
				condition, sortField, order, needLink, -1, -1);

		return data;
	}

	@RemoteMethod
	public List<Map<String, Object>> findMapByPropertiesQuick(
			String[] properties, String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findYZ_GroupCalls(properties,
				condition, "", "", needLink, -1, -1);

		return data;
	}

	@RemoteMethod
	public Map<String, Object> getYZ_GroupCall(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findYZ_GroupCalls(properties,
				condition, "", "", needLink, -1, -1);

		return data != null && data.size() > 0 ? data.get(0) : null;
	}

	public YZ_GroupCall getYZ_GroupCallEntity(String[] properties,
			String condition, boolean needLink) {
		List<Map<String, Object>> data = mapDao.findYZ_GroupCalls(properties,
				condition, "", "", needLink, -1, -1);

		return data != null && data.size() > 0 ? new YZ_GroupCall(data.get(0))
				: null;
	}

	@RemoteMethod
	public int findCountByProperties(String[] properties, String condition,
			boolean needLink) {
		int length = mapDao.findCount(properties, condition, needLink);
		return length;
	}

	public List<YZ_GroupCall> findYZ_GroupCallByProperties(String[] properties,
			String condition, boolean needLink, int index, int limit) {
		return entityDao.findYZ_GroupCalls(properties, condition, needLink,
				index, limit);
	}

	@RemoteMethod
	public boolean deleteById(String id) {
		try {
			commonDao.delete(YZ_GroupCall.class, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntity(YZ_GroupCall value) {
		return deleteById(value.getID());
	}

	@RemoteMethod
	public boolean deleteByIds(String[] ids) {
		try {
			commonDao.delete(YZ_GroupCall.class, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RemoteMethod
	public boolean deleteByEntitys(YZ_GroupCall[] values) {
		String[] ids = new String[values.length];
		int index = 0;
		for (YZ_GroupCall item : values) {
			ids[index++] = item.getID();
		}
		return deleteByIds(ids);
	}

	@RemoteMethod
	public boolean save(Map<String, Object> value) {
		return saveEntity(new YZ_GroupCall(value));
	}

	@RemoteMethod
	public boolean saveEntity(YZ_GroupCall value) {
		return commonDao.merge(value) != null ? true : false;
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(Map<String, Object> value) {
		return saveAndReturn(new YZ_GroupCall(value));
	}

	@RemoteMethod
	public Map<String, Object> saveAndReturn(YZ_GroupCall value) {
		YZ_GroupCall yZ_GroupCall = (YZ_GroupCall) commonDao.merge(value);
		if (yZ_GroupCall != null) {
			return yZ_GroupCall.getProperties();
		}
		return null;
	}

	@RemoteMethod
	public boolean updateEntity(YZ_GroupCall data, String condition) {
		if (mapDao.updateYZ_GroupCall(data.getProperties(), condition) == 1) {
			return true;
		}
		return false;
	}

	public Map<String, Object> selectNotReceipt(String[] str) {
		Map<String, Object> rs = null;
		if (str != null && str.length > 0) {
			int length = str.length;
			StringBuilder sql = new StringBuilder(
					"select telAtt,Date2-Date1,Content from YZ_GroupCall where Status2 = 2 and ( telAtt = ? ");
			for (int i = 1; i < length; i++) {
				sql.append(" OR telAtt = ?");
			}

			sql.append(")");
			List<Object> list = commonDao.executeAndReturn(sql.toString(), str);
			rs = new HashMap<String, Object>();
			if (list != null && list.size() > 0) {
				int listSize = list.size();
				for (int i = 0; i < listSize; i++) {
					Object[] ttt = new Object[2];
					Object[] oo = (Object[]) list.get(i);
					ttt[0] = oo[1];
					ttt[1] = oo[2].toString().split(",")[1];
					rs.put(oo[0].toString(), ttt);
				}
			}
		}

		return rs;
	}
}
