package kr.namoosori.naite.ri.plugin.core;

import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;


public abstract class CoreContext {
	//
	public static int DEFAULT_SERVER_PORT = 19193;
	
	protected static CoreContext instance = null;

	public static CoreContext getInstance() {
		return instance;
	}
	
	public static Lecture CURRENT_LECTURE;

	public abstract String getServerUrl();
	public abstract boolean hasServerUrl();
}
