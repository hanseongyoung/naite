package kr.namoosori.naite.ri.plugin.netclient.main;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import kr.namoosori.naite.ri.plugin.core.CoreContext;

public class NaiteWSClient {
	//
	private static NaiteWSClient instance = new NaiteWSClient();
	
	private static final boolean USE_WS = false;
	
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
		if (!USE_WS) {
			return;
		}
		
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
