package kr.namoosori.naite.ri.plugin.student;

import kr.namoosori.naite.ri.plugin.core.CoreContext;

public class StudentContext extends CoreContext {
	//
	private String serverIp;
	private int serverPort;
	
	public static void init() {
		//
		instance = new StudentContext();
	}
	
	public static StudentContext getInstance() {
		//
		if (instance == null) {
			instance = new StudentContext();
		}
		return (StudentContext) instance;
	}
	
	public StudentContext() {
		//
		this.serverPort = DEFAULT_SERVER_PORT;
	}
	
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
