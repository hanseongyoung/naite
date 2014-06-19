package kr.namoosori.naite.ri.plugin.netclient.main;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;

@ClientEndpoint
public class MessageWSClient {
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received msg:"+ message);
		
		List<ClientMessage> messages = new ArrayList<ClientMessage>();
		messages.add(new ClientMessage(message.split(":")[0], message.split(":")[1]));
		System.out.println("invoke --> "+messages.get(0).getCommand());
		EventManager.getInstance().invokeMessageEvent(messages);
		System.out.println("invoked...");
	}
}
