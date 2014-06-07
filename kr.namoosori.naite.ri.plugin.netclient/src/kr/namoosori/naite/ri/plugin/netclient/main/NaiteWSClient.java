package kr.namoosori.naite.ri.plugin.netclient.main;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

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
	
	public void start() {
		System.out.println("##################### WebSocket connect #########################");
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			session = container.connectToServer(WSClient.class, URI.create("ws://localhost:9000/hello"));
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		try {
			session.getBasicRemote().sendText("wow!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		//
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
