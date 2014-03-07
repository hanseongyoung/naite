package kr.namoosori.naite.ri.plugin.netserver.main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import kr.namoosori.naite.ri.plugin.netserver.facade.ServerEventListener;
import kr.namoosori.naite.ri.plugin.netserver.server.ConnectlessMessageServer;
import kr.namoosori.naite.ri.plugin.netserver.server.MulticastServer;
import kr.namoosori.naite.ri.plugin.netserver.work.ServerEventManager;

public class NaiteNetServer {
	//
	private static NaiteNetServer instance = new NaiteNetServer();
	
	public static NaiteNetServer getInstance() {
		//
		return instance;
	}
	
	private ConnectlessMessageServer messageServer;
	private MulticastServer multicastServer;
	
	private NaiteNetServer () {
		//
		this.messageServer = new ConnectlessMessageServer();
		this.multicastServer = new MulticastServer(getServerIp());
	}
	
	private String getServerIp() {
		//
		String address = null;
		try {
			InetAddress local = InetAddress.getLocalHost();
			address = local.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address;
	}

	public void start() {
		//
		this.messageServer.startServer();
		this.multicastServer.startServer();
	}
	
	public void stop() {
		//
		this.multicastServer.stopServer();
		this.messageServer.stopServer();
	}

	public void addServerEventListener(ServerEventListener listener) {
		//
		ServerEventManager manager = this.messageServer.getEventManager();
		manager.addServerStateListener(listener);
	}
	
	public void removeServerEventListener(ServerEventListener listener) {
		//
		ServerEventManager manager = this.messageServer.getEventManager();
		manager.removeServerStateListener(listener);
	}
	
}
