package kr.namoosori.naite.ri.plugin.netclient.facade.message;

public class ClientMessage {
	//
	private String senderId;
	private SendMessage sendMessage;
	
	public ClientMessage(String senderId, String message) {
		//
		this.senderId = senderId;
		this.sendMessage = SendMessage.create(message);
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public SendMessage getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(SendMessage sendMessage) {
		this.sendMessage = sendMessage;
	}

}
