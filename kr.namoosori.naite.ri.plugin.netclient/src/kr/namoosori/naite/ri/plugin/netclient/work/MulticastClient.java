package kr.namoosori.naite.ri.plugin.netclient.work;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;

// TODO : 서버아이피를 받으면 더이상 동작하지 않게 함
public class MulticastClient extends Thread {
	//
	private NetClientContext context;
	private MulticastSocket socket;
	private boolean continueReceive;
	
	public MulticastClient(NetClientContext context) {
		super("MulticastClient");
		this.context = context;
		
		try {
			socket = new MulticastSocket(4446);
			System.out.println("client:"+socket.getLocalAddress().getHostAddress());
			InetAddress address = InetAddress.getByName("230.0.0.1");
			socket.joinGroup(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
		continueReceive = true;
	}

	@Override
	public void run() {
		System.out.println("MulticastClient start...");
		while(continueReceive) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				String receiveString = new String(packet.getData(), 0, packet.getLength());
				processReceive(receiveString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			socket.leaveGroup(InetAddress.getByName("230.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		socket.close();
		System.out.println("end... MulticastClientThread");
	}

	private void processReceive(String receiveString) {
		//
		synchronized (context) {
			context.setServerIp(receiveString);
			context.setLastServerAliveTime(System.currentTimeMillis());
		}
	}
	
	public void end() {
		//
		this.continueReceive = false;
	}

}
