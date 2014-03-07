package kr.namoosori.naite.ri.plugin.netserver.work;

import kr.namoosori.naite.ri.plugin.netserver.context.Client;
import kr.namoosori.naite.ri.plugin.netserver.context.ClientBox;
import kr.namoosori.naite.ri.plugin.netserver.context.Message;

public class ClientManager {
	//
	private ClientBox clientBox;
	private ServerEventManager eventManager;
	
	public ClientManager(ClientBox clientBox, ServerEventManager eventManager) {
		//
		this.clientBox = clientBox;
		this.eventManager = eventManager;
	}

	public Client registClient(String clientIp, String messageBlock) {
		//
		String clientId = Message.parseFromClientId(messageBlock);
		Client client = clientBox.findClient(clientId);
		if (client == null) {
			client = clientBox.addClient(clientId, clientIp);
			this.eventManager.sendToListener(clientId, true);
		} else {
			client.setCurrentTime();
			client.setIpAddress(clientIp);
		}
		return client;
	}
}
