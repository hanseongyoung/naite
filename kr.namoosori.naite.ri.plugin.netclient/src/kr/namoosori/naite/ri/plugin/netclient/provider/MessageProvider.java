package kr.namoosori.naite.ri.plugin.netclient.provider;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.work.NetServerResponse;

public class MessageProvider {
	//
	private List<MessageListener> listeners = new ArrayList<MessageListener>();
	
	public void addMessageListener(MessageListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}
	
	public void removeMessageListener(MessageListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.remove(listener);
	}
	
	public void sendToListener(NetServerResponse response) {
		//
		if (listeners == null || listeners.size() <= 0) {
			return;
		}
		for (MessageListener listener : listeners) {
			// TODO : event type
			List<ClientMessage> messages = response.getResponseMessages();
			for (ClientMessage message : messages) {
				listener.messageReceived(message);
			}
		}
	}
}
