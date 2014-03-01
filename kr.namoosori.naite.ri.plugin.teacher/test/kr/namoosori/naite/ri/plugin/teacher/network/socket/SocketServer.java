package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
	//
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(4449);
			Socket socket = serverSocket.accept();
			System.out.println("accept! -->" +socket.getLocalSocketAddress());
			socket.close();
			serverSocket.close();
			System.out.println("end!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.start();
		System.out.println("start!");
	}

}
