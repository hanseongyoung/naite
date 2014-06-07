package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;

public class NetClientStub {
	private NaiteNetClient client = NaiteNetClient.getInstance();
	private String clientId;
	
	public NetClientStub(String clientId) {
		this.clientId = clientId;
	}
	
	public void addMessageListener(MessageListener listener) {
		EventManager.getInstance().addMessageListener(listener);
	}
	
	public void send(String senderId, String receiverId, SendMessage sendMessage) {
		client.getMessageSender().send(senderId, receiverId, sendMessage);
	}
	
	public void start() {
		NetClientContext context = client.getContext();
		context.setClientId(clientId);
		context.setServerIp("localhost");
		context.setServerPort(4000);
		client.start();
	}

}
