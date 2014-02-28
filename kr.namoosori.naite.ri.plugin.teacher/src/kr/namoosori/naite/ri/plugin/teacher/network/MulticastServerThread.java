package kr.namoosori.naite.ri.plugin.teacher.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import kr.namoosori.naite.ri.plugin.core.CoreConstants;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;

public class MulticastServerThread extends Thread {
	//
	private static MulticastServerThread instance;
	
	public static MulticastServerThread getInstance() throws IOException {
		//
		if (instance == null) {
			instance = new MulticastServerThread();
		}
		return instance;
	}
	
	private DatagramSocket socket;
	
	private boolean continueServe;
	
	private String defaultServeString = "check";	
	private String serveString;
	
	private MulticastServerThread() throws IOException {
		//
		super("MulticastServerThread");
		socket = new DatagramSocket(CoreConstants.MULTICAST_PORT);
		continueServe = true;
	}
	
	public void setDefaultServeString(String defaultServeString) {
		//
		this.defaultServeString = defaultServeString;
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
				multicast(serveString);
				serveString = null;
			} else {
				multicast(defaultServeString);
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
			sleep((long) (Math.random() * TeacherContext.TEACHER_MAX_INTERVAL));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void multicast(String data) {
		//
		try {
			byte[] buf = data.getBytes();
			InetAddress group = InetAddress.getByName(CoreConstants.MULTICAST_GROUP_IP);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, CoreConstants.MULTICAST_PORT);
			socket.send(packet);
			//System.out.println("send..."+data);
		} catch (IOException e) {
			e.printStackTrace();
			end();
		}
	}

}
