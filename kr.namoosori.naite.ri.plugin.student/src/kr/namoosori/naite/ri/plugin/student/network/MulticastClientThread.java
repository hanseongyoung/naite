package kr.namoosori.naite.ri.plugin.student.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import kr.namoosori.naite.ri.plugin.core.CoreConstants;

public class MulticastClientThread extends Thread {
	//
	private MulticastSocket socket;
	
	private boolean continueReceive;
	
	public MulticastClientThread() throws IOException {
		//
		super("MulticastClientThread");
		socket = new MulticastSocket(CoreConstants.MULTICAST_PORT);
		System.out.println("client:"+socket.getLocalAddress().getHostAddress());
		InetAddress address = InetAddress.getByName(CoreConstants.MULTICAST_GROUP_IP);
		socket.joinGroup(address);
		continueReceive = true;
	}

	@Override
	public void run() {
		//
		System.out.println("start... MulticastClientThread");
		while(continueReceive) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				String receiveString = new String(packet.getData(), 0, packet.getLength());
				System.out.println("receive["+receiveString+"]");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socket.close();
		System.out.println("end... MulticastClientThread");
	}
	
	public void end() {
		//
		continueReceive = false;
	}
	
}
