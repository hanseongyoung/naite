package kr.namoosori.naite.ri.plugin.netclient.provider;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.RefreshListener;

public class RefreshProvider {
	//
	private List<RefreshListener> listeners = new ArrayList<RefreshListener>();
	
	public void addRefreshListener(RefreshListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}
	
	public void removeRefreshListener(RefreshListener listener) {
		//
		if (listener == null) {
			return;
		}
		listeners.remove(listener);
	}

	public void sendToListener() {
		//
		if (listeners == null || listeners.size() <= 0) {
			return;
		}
		for (RefreshListener listener : listeners) {
			listener.refresh();
		}
	}
}
