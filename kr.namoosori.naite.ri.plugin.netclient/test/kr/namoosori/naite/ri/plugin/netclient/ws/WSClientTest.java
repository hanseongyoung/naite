package kr.namoosori.naite.ri.plugin.netclient.ws;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WSClientTest {
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received msg : "+ message);
	}
	
	private static Object waitLock = new Object();
	
	private static void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		WebSocketContainer container = null;
		Session session = null;
		
		try {
			container = ContainerProvider.getWebSocketContainer();
			session = container.connectToServer(WSClientTest.class, URI.create("ws://localhost:9000/hello"));
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
