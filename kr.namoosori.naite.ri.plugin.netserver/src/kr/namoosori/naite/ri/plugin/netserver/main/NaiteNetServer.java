package kr.namoosori.naite.ri.plugin.netserver.main;

import kr.namoosori.naite.ri.plugin.netserver.server.ConnectlessMessageServer;

public class NaiteNetServer {
	//
	private static NaiteNetServer instance = new NaiteNetServer();
	
	public static NaiteNetServer getInstance() {
		//
		return instance;
	}
	
	private ConnectlessMessageServer messageServer;
	
	private NaiteNetServer () {
		//
		this.messageServer = new ConnectlessMessageServer();
	}
	
	public void start() {
		//
		this.messageServer.startServer();
	}
	
	public void stop() {
		//
		this.messageServer.stopServer();
	}
	
	

}
