package kr.namoosori.naite.ri.plugin.netclient.event;

import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.RefreshListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.ServerStateListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.provider.MessageProvider;
import kr.namoosori.naite.ri.plugin.netclient.provider.RefreshProvider;
import kr.namoosori.naite.ri.plugin.netclient.provider.ServerStateProvider;

public class EventManager {
	//
	private static EventManager instance = new EventManager();
	
	public static EventManager getInstance() {
		//
		return instance;
	}
	
	private MessageProvider messageProvider;
	private ServerStateProvider serverStateProvider;
	private RefreshProvider refreshProvider;
	
	private EventManager() {
		//
		this.messageProvider = new MessageProvider();
		this.serverStateProvider = new ServerStateProvider();
		this.refreshProvider = new RefreshProvider();
	}
	
	public void invokeServerStateEvent(boolean serverState) {
		//
		this.serverStateProvider.sendToListener(serverState);
	}
	
	public void invokeMessageEvent(List<ClientMessage> messages) {
		//
		this.messageProvider.sendToListener(messages);
	}
	
	public void invokeRefreshEvent() {
		//
		this.refreshProvider.sendToListener();
	}
	
	public void addMessageListener(MessageListener listener) {
		//
		this.messageProvider.addMessageListener(listener);
	}
	
	public void removeMessageListener(MessageListener listener) {
		//
		this.messageProvider.removeMessageListener(listener);
	}
	
	public void addServerStateListener(ServerStateListener listener) {
		//
		this.serverStateProvider.addServerStateListener(listener);
	}
	
	public void removeServerStateListener(ServerStateListener listener) {
		//
		this.serverStateProvider.removeServerStateListener(listener);
	}
	
	public void addRefreshListener(RefreshListener listener) {
		//
		this.refreshProvider.addRefreshListener(listener);
	}
	
	public void removeRefreshListener(RefreshListener listener) {
		//
		this.refreshProvider.removeRefreshListener(listener);
	}

}
