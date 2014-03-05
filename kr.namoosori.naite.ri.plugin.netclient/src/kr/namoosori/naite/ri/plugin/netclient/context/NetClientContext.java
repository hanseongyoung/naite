package kr.namoosori.naite.ri.plugin.netclient.context;

public class NetClientContext {
	//
	private String serverIp;
	private int serverPort;
	private String clientId;
	
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
}
