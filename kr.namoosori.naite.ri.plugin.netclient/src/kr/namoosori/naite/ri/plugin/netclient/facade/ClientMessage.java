package kr.namoosori.naite.ri.plugin.netclient.facade;

public class ClientMessage {
	//
	private String senderId;
	private String message;
	
	public ClientMessage(String senderId, String message) {
		//
		this.senderId = senderId;
		this.message = message;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
