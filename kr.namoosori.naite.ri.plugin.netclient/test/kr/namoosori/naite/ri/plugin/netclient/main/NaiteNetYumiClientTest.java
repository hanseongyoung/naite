package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.facade.message.NameValue;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;

// Play with : kr.namoosori.naite.ri.plugin.netserver.main.NaiteNetServerTest
// Play with : kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetHongClientTest
// Play with : kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetYumiClientTest
public class NaiteNetYumiClientTest {
	
	public static void main(String[] args) throws Exception {
		final NetClientStub clientStub = new NetClientStub("yumi");
		clientStub.start();
		
		Thread.sleep(3000);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					SendMessage sendMessage = new SendMessage();
					sendMessage.setCommand("noti");
					sendMessage.addNameValue(new NameValue("msg", "안녕"));
					
					clientStub.send("hong", sendMessage);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}

}
