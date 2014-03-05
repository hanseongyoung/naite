package kr.namoosori.naite.ri.plugin.netserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MulticastServer extends ContinuedServerThread {
	//
	private DatagramSocket socket;
	private String serveData;
	
	public MulticastServer(String serveData) {
		//
		super("MulticastServer", 10000L * 3L, true);
		this.serveData = serveData;
	}

	@Override
	protected void prepareServer() {
		//
		try {
			socket = new DatagramSocket(4446);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void whileServing() {
		//
		try {
			byte[] buf = serveData.getBytes();
			InetAddress group = InetAddress.getByName("230.0.0.1");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
			
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void releaseServer() {
		//
		socket.close();
	}

}
