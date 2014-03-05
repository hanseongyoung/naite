package kr.namoosori.naite.ri.plugin.netserver.server;

public class ConnectlessMessageServerTest {
	
	public static void main(String[] args) throws Exception {
		ConnectlessMessageServer server = new ConnectlessMessageServer();
		server.startServer();
		
		Thread.sleep(30000);
		server.stopServer();
		
		Thread.sleep(5000);
		server.startServer();
		
		Thread.sleep(30000);
		server.stopServer();
	}
}
