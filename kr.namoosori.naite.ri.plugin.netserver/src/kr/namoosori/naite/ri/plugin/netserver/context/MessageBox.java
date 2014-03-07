package kr.namoosori.naite.ri.plugin.netserver.context;

import java.util.ArrayList;
import java.util.List;

public class MessageBox {
	//
	private List<Message> messages = new ArrayList<Message>();

	public synchronized void addAll(List<Message> toClientMessages) {
		//
		messages.addAll(toClientMessages);
	}

	public synchronized void add(Message message) {
		//
		messages.add(message);
	}
	
	public synchronized void removeAll(List<Message> clearMessages) {
		//
		messages.removeAll(clearMessages);
	}
	
	public synchronized void remove(Message message) {
		//
		messages.remove(message);
	}
	
	public List<Message> findByClientId(String clientId) {
		//
		List<Message> finded = new ArrayList<Message>();
		for (Message message : messages) {
			if (message.getFrom().getId().equals(clientId)
					|| message.getTo().getId().equals(clientId)) {
				finded.add(message);
			}
		}
		return finded;
	}

	public List<Message> getMessages() {
		return messages;
	}

}
