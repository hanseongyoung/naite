package kr.namoosori.naite.ri.plugin.student;

import kr.namoosori.naite.ri.plugin.core.CoreContext;

public class StudentContext extends CoreContext {
	//
	public static final String DEFAULT_DOMAIN = "playapp-syhan.rhcloud.com";
	public static final int DEFAULT_PORT = 80;
	
	private boolean serverOn;
	private String serverIp;
	private int serverPort;
	//private long lastTeacherAliveTime;
	
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
	
//	public void setTeacherAliveTime() {
//		//
//		this.lastTeacherAliveTime = System.currentTimeMillis();
//	}
//	
//	public boolean isTeacherAlive() {
//		//
//		if (lastTeacherAliveTime <= 0L) {
//			return false;
//		}
//		
//		long gap = System.currentTimeMillis() - lastTeacherAliveTime;
//		return gap <= TEACHER_MAX_INTERVAL;
//	}
	
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

//	public long getLastTeacherAliveTime() {
//		return lastTeacherAliveTime;
//	}


}
