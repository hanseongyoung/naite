package kr.namoosori.naite.ri.plugin.teacher;

import kr.namoosori.naite.ri.plugin.core.CoreContext;
import kr.namoosori.naite.ri.plugin.core.util.NetworkUtils;

public class TeacherContext extends CoreContext {
	//
	public static final String DEFAULT_DOMAIN = "playapp-syhan.rhcloud.com";
	public static final int DEFAULT_PORT = 80;
	
	private String serverIp;
	private int serverPort;
	
	private TeacherContext() {
		//
		this.serverIp = NetworkUtils.getHostAddress();
		this.serverPort = DEFAULT_SERVER_PORT;
	}
	
	public static void init() {
		//
		instance = new TeacherContext();
	}
	
	public static TeacherContext getInstance() {
		//
		if (instance == null) {
			instance = new TeacherContext();
		}
		return (TeacherContext) instance;
	}
	//--------------------------------------------------------------------------
	@Override
	public String getServerUrl() {
		//
		if (serverIp == null) {
			return null;
		}
		return "http://" + serverIp + ":" + serverPort + "/";
	}
	
	@Override
	public boolean hasServerUrl() {
		//
		return serverIp != null;
	}

	//--------------------------------------------------------------------------
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	
}
