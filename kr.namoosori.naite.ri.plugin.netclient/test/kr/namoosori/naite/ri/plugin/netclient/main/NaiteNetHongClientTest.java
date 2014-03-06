package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;

// Play with : kr.namoosori.naite.ri.plugin.netserver.main.NaiteNetServerTest
// Play with : kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetHongClientTest
// Play with : kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetYumiClientTest
public class NaiteNetHongClientTest {
	
	public static void main(String[] args) throws Exception {
		final NetClientStub clientStub = new NetClientStub("hong");
		clientStub.addMessageListener(new MessageListener() {
			@Override
			public void messageReceived(ClientMessage message) {
				System.out.println("### [Listener] received from "+ message.getSenderId() + "--> " + message.getSendMessage().toMessageBlock());
			}
		});
		clientStub.start();
	}

}
