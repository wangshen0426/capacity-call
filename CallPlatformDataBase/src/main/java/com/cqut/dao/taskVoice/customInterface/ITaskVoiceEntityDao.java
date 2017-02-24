package com.cqut.dao.taskVoice.customInterface;

import java.util.List;

import com.cqut.entity.taskVoice.TaskVoice;

public interface ITaskVoiceEntityDao {

	public List<TaskVoice> findTaskVoices (String[] property,
			String condition, boolean needLink, int index, int limit);
}
