package com.quzrtz.hello;

/**
 * 定时任务
 * 
 */
public interface ScheduleJobService {
	
	/**
	 * 保存定时任务
	 */
	void save(ScheduleJobEntity scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);

	/**
	 * 执行日志
	 * @param log
	 */
	void saveScheduleLog(ScheduleJobLogEntity log);
}
