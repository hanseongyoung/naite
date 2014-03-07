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
	
	public List<Client> findClientsByTimeoutInterval(long timeoutInterval) {
		//
		List<Client> timeoutClients = new ArrayList<Client>();
		long currentTime = System.currentTimeMillis();
		for (Client client : clients) {
			long interval = currentTime - client.getLastAccessTime();
			if (interval > timeoutInterval) {
				timeoutClients.add(client);
			}
		}
		return timeoutClients;
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
	
	public synchronized void removeClient(Client client) {
		//
		clients.remove(client);
	}

	public List<Client> getClients() {
		return clients;
	}

}
