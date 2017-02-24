package com.cqut.dao.callTask.customInterface;

import java.util.List;

import com.cqut.entity.callTask.CallTask;

public interface ICallTaskEntityDao {

	public List<CallTask> findCallTasks (String[] property,
			String condition, boolean needLink, int index, int limit);
}
