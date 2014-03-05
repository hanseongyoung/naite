package kr.namoosori.naite.ri.plugin.netserver.context;

import java.util.ArrayList;
import java.util.List;

public class ClientBox {
	//
	private List<Client> clients = new ArrayList<Client>();

	public Client findClient(String clientId) {
		//
		Client finded = null;
		for (Client client : clients) {
			if (client.getId().equals(clientId)) {
				finded = client;
				break;
			}
		}
		return finded;
	}

	public synchronized Client addClient(String clientId, String clientIp) {
		//
		Client exist = findClient(clientId);
		if (exist != null) {
			return exist;
		}
		
		Client client = new Client(clientId, clientIp);
		clients.add(client);
		return client;
	}

	public List<Client> getClients() {
		return clients;
	}

}
