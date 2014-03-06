package kr.namoosori.naite.ri.plugin.netclient.facade;

import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;

public interface MessageListener {
	//
	public void messageReceived(ClientMessage message);
}
