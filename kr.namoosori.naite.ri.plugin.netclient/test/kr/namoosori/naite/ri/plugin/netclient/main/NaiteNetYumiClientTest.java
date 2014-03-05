package kr.namoosori.naite.ri.plugin.netclient.main;

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
					clientStub.send("hong", "안녕");
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
