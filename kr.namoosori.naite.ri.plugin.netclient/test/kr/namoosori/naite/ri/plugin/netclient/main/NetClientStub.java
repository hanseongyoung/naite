package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;

public class NetClientStub {
	private NaiteNetClient client = NaiteNetClient.getInstance();
	private String clientId;
	
	public NetClientStub(String clientId) {
		this.clientId = clientId;
	}
	
	public void addMessageListener(MessageListener listener) {
		client.addMessageListener(listener);
	}
	
	public void send(String receiverId, String message) {
		client.getMessageSender().send(receiverId, message);
	}
	
	public void start() {
		NetClientContext context = client.getContext();
		context.setClientId(clientId);
		context.setServerIp("localhost");
		context.setServerPort(4000);
		client.start();
	}

}
