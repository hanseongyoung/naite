package kr.namoosori.naite.ri.plugin.netserver.work;

import kr.namoosori.naite.ri.plugin.netserver.context.Client;
import kr.namoosori.naite.ri.plugin.netserver.context.ClientBox;
import kr.namoosori.naite.ri.plugin.netserver.context.Message;

public class ClientManager {
	//
	private ClientBox clientBox;
	
	public ClientManager(ClientBox clientBox) {
		//
		this.clientBox = clientBox;
	}

	public Client registClient(String clientIp, String messageBlock) {
		//
		String clientId = Message.parseFromClientId(messageBlock);
		Client client = clientBox.findClient(clientId);
		if (client == null) {
			client = clientBox.addClient(clientId, clientIp);
		} else {
			client.setCurrentTime();
			client.setIpAddress(clientIp);
		}
		return client;
	}
}
