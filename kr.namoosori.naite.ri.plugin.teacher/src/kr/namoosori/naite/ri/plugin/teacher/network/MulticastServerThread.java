package kr.namoosori.naite.ri.plugin.teacher.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import kr.namoosori.naite.ri.plugin.core.CoreConstants;

public class MulticastServerThread extends Thread {
	//
	private long TEN_SECONDS = 10000;
	
	private DatagramSocket socket;
	
	private boolean continueServe;
	
	private String serveString;
	
	public MulticastServerThread() throws IOException {
		super("MulticastServerThread");
		socket = new DatagramSocket(CoreConstants.MULTICAST_PORT);
		System.out.println("server:"+socket.getLocalAddress().getHostAddress());
		continueServe = true;
	}
	
	public void setServeString(String serveString) {
		//
		this.serveString = serveString;
	}

	@Override
	public void run() {
		//
		System.out.println("start... MulticastServerThread");
		while(continueServe) {
			if (serveString != null) {
				multicast();
			}
			
			sleepForWhile();
		}
		socket.close();
		System.out.println("end... MulticastServerThread");
	}
	
	public void end() {
		//
		continueServe = false;
	}

	private void sleepForWhile() {
		//
		try {
			sleep((long) (Math.random() * TEN_SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void multicast() {
		//
		try {
			byte[] buf = serveString.getBytes();
			InetAddress group = InetAddress.getByName(CoreConstants.MULTICAST_GROUP_IP);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, CoreConstants.MULTICAST_PORT);
			socket.send(packet);
			System.out.println("send..."+serveString);
		} catch (IOException e) {
			e.printStackTrace();
			end();
		}
	}

}
