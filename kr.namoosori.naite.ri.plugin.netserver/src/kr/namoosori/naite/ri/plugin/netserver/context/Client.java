package kr.namoosori.naite.ri.plugin.netserver.context;

public class Client {
	//
	public static final String ALL = "*";
	
	private String id;
	private long lastAccessTime;
	private String ipAddress;
	
	public Client(String clientId, String ipAddress) {
		//
		this.id = clientId;
		this.ipAddress = ipAddress;
		setCurrentTime();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setCurrentTime() {
		//
		this.lastAccessTime = System.currentTimeMillis();
	}

	@Override
	public boolean equals(Object obj) {
		//
		if (this == obj) return true;
		if (!(obj instanceof Client)) return false;
		
		Client other = (Client) obj;
		return this.id == null ? false : this.id.equals(other.id);
	}
}
