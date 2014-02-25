package kr.namoosori.naite.ri.plugin.student.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import kr.namoosori.naite.ri.plugin.core.CoreConstants;
import kr.namoosori.naite.ri.plugin.student.StudentContext;
import kr.namoosori.naite.ri.plugin.student.event.TeacherEventHandler;

public class MulticastClientThread extends Thread {
	//
	private static MulticastClientThread instance;
	
	public static MulticastClientThread getInstance() throws IOException {
		//
		if (instance == null) {
			instance = new MulticastClientThread();
		}
		return instance;
	}
	
	private MulticastSocket socket;
	
	private boolean continueReceive;
	
	private MulticastClientThread() throws IOException {
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
				processReceive(receiveString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socket.close();
		System.out.println("end... MulticastClientThread");
	}
	
	private void processReceive(String receiveString) {
		//
		if (receiveString == null || receiveString.length() <= 0) {
			return;
		}
		String[] arr = receiveString.split(":");
		if (arr[0].equals("ip")) {
			if (!StudentContext.getInstance().hasServerUrl()) {
				StudentContext.getInstance().setServerIp(arr[1]);
				TeacherEventHandler.getInstance().setRequiredRefresh(true);
			}
		}
		if (arr[0].equals("cmd")) {
			if (arr[1].equals("refresh")) {
				TeacherEventHandler.getInstance().setRequiredRefresh(true);
			}
		}
	}

	public void end() {
		//
		continueReceive = false;
	}
	
}
