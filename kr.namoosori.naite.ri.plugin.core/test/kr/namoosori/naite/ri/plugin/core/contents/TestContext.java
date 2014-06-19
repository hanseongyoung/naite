package kr.namoosori.naite.ri.plugin.core.contents;

import kr.namoosori.naite.ri.plugin.core.CoreContext;

public class TestContext extends CoreContext {
	//
	private boolean serverOn;
	private String serverIp;
	private int serverPort;
	
	public static void init() {
		//
		instance = new TestContext();
	}
	
	public static TestContext getInstance() {
		//
		if (instance == null) {
			instance = new TestContext();
		}
		return (TestContext) instance;
	}
	
	public TestContext() {
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
	public String getWSServerUrl() {
		//
		if (serverIp == null) {
			return null;
		}
		return "ws://" + serverIp + ":" + serverPort + "/";
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

	public boolean isServerOn() {
		return serverOn;
	}

	public void setServerOn(boolean serverOn) {
		this.serverOn = serverOn;
	}


}
