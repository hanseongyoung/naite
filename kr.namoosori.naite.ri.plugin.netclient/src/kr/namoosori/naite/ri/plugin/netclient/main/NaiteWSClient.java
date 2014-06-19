package kr.namoosori.naite.ri.plugin.netclient.main;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import kr.namoosori.naite.ri.plugin.core.CoreContext;
import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;

@ClientEndpoint
public class NaiteWSClient {
	//
	private static NaiteWSClient instance = new NaiteWSClient();
	
	public static NaiteWSClient getInstance() {
		//
		return instance;
	}
	
	private Session session;
	
	private NaiteWSClient() {
		//
	}
	
	public void connect(String userId) {
		//
		if (userId == null || userId.length() <= 0) {
			return;
		}
		
		if (isConnected()) {
			System.out.println("already connected....");
			return;
		}
		
		String wsServerUrl = CoreContext.getInstance().getWSServerUrl();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			session = container.connectToServer(MessageWSClient.class, URI.create(wsServerUrl + "ws/message/" + userId));
			System.out.println("websocket connected...");
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String message) {
		//
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received msg:"+ message);
		
		List<ClientMessage> messages = new ArrayList<ClientMessage>();
		messages.add(new ClientMessage(message.split(":")[0], message.split(":")[1]));
		System.out.println("invoke --> "+messages.get(0).getCommand());
		EventManager.getInstance().invokeMessageEvent(messages);
		System.out.println("invoked...");
	}
	
	public void close() {
		//
		if (session == null) {
			return;
		}
		
		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		session = null;
	}
	
	public boolean isConnected() {
		// 
		return session != null;
	}

}
