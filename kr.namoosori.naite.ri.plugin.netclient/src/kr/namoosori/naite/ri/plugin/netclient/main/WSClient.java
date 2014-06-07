package kr.namoosori.naite.ri.plugin.netclient.main;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

@ClientEndpoint
public class WSClient {
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Received msg : "+ message);
	}
	
}
