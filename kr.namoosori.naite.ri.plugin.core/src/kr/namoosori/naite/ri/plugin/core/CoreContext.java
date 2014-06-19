package kr.namoosori.naite.ri.plugin.core;

import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;


public abstract class CoreContext {
	//
	public static final int DEFAULT_SERVER_PORT = 19193;
	public static final long TEACHER_MAX_INTERVAL = 10000L * 3L; // 30초
	
	protected static CoreContext instance = null;

	public static CoreContext getInstance() {
		return instance;
	}
	
	public static Lecture CURRENT_LECTURE;

	public abstract String getServerUrl();
	public abstract String getWSServerUrl();
	public abstract boolean hasServerUrl();
}
