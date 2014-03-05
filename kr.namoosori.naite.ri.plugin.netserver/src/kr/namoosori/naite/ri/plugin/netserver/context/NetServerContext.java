package kr.namoosori.naite.ri.plugin.netserver.context;

public class NetServerContext {
	//
	private MessageBox messageBox;
	private ClientBox clientBox;
	
	public NetServerContext() {
		//
		this.messageBox = new MessageBox();
		this.clientBox = new ClientBox();
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public ClientBox getClientBox() {
		return clientBox;
	}

	public void setClientBox(ClientBox clientBox) {
		this.clientBox = clientBox;
	}
	
}
