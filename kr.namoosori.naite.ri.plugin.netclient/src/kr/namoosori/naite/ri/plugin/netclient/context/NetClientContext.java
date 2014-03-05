package kr.namoosori.naite.ri.plugin.netclient.context;

public class NetClientContext {
	//
	public static final long MULTICAST_MAX_INTERVAL = 10000L * 3L; // 30초
	
	private String serverIp;
	private int serverPort = 4000;
	private String clientId;
	private long lastServerAliveTime;
	
	public boolean isRequestAvailable() {
		//
		if (serverIp == null || serverIp.length() <= 0) {
			return false;
		}
		if (serverPort <= 0) {
			return false;
		}
		if (clientId == null || clientId.length() <= 0) {
			return false;
		}
		return true;
	}
	
	public boolean isServerAlive() {
		//
		if (lastServerAliveTime <= 0L) {
			return false;
		}
		
		long gap = System.currentTimeMillis() - lastServerAliveTime;
		return gap <= MULTICAST_MAX_INTERVAL;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public long getLastServerAliveTime() {
		return lastServerAliveTime;
	}
	public void setLastServerAliveTime(long lastServerAliveTime) {
		this.lastServerAliveTime = lastServerAliveTime;
	}
}
