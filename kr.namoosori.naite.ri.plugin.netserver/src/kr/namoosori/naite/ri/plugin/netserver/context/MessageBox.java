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
		messages.removeAll(clearMessages);
	}

	public List<Message> getMessages() {
		return messages;
	}

}
