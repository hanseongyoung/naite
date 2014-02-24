package kr.namoosori.naite.ri.plugin.core;


public abstract class CoreContext {
	//
	public static int DEFAULT_SERVER_PORT = 19193;
	
	protected static CoreContext instance = null;

	public static CoreContext getInstance() {
		return instance;
	}

	public abstract String getServerUrl();
	public abstract boolean hasServerUrl();
}
