package kr.namoosori.naite.ri.plugin.netclient.provider;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.ServerStateListener;

public class ServerStateProvider {
	//
	private List<ServerStateListener> listeners = new ArrayList<ServerStateListener>();
	
	public void addServerStateListener(ServerStateListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}
	
	public void removeServerStateListener(ServerStateListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.remove(listener);
	}

	public void sendToListener(boolean serverState) {
		//
		if (listeners == null || listeners.size() <= 0) {
			return;
		}
		for (ServerStateListener listener : listeners) {
			if (serverState) {
				listener.serverOn();
			} else {
				listener.serverOff();
			}
		}
	}
}
