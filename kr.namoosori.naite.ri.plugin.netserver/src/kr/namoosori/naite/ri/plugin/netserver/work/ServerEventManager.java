package kr.namoosori.naite.ri.plugin.netserver.work;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netserver.facade.ServerEventListener;

public class ServerEventManager {
	//
	private List<ServerEventListener> listeners = new ArrayList<ServerEventListener>();
	
	public void addServerStateListener(ServerEventListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}
	
	public void removeServerStateListener(ServerEventListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.remove(listener);
	}

	public void sendToListener(String clientId, boolean clientIn) {
		//
		if (listeners == null || listeners.size() <= 0) {
			return;
		}
		for (ServerEventListener listener : listeners) {
			if (clientIn) {
				listener.clientIn(clientId);
			} else {
				listener.clientOut(clientId);
			}
		}
	}
}
