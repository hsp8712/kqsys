package net.oraro.web;

import java.util.Timer;

/**
 * 定时任务管理
 * @author oraro
 *
 */
public class WebTimerTaskManage extends Timer {
	
	private static WebTimerTaskManage timerTaskManage;
	
	private WebTimerTaskManage(){}
	
	public static WebTimerTaskManage getInstance() {
		if(timerTaskManage == null) {
			timerTaskManage = new WebTimerTaskManage();
		}
		return timerTaskManage;
	}
}
