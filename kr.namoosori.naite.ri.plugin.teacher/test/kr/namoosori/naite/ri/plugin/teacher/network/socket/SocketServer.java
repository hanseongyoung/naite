package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
	//
	private ServerSocket serverSocket;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(4449);
			while (true) {
				System.out.println("server wait...");
				Socket socket = serverSocket.accept();
				System.out.println("accept! -->" +socket.getInetAddress());
				
				new SocketWorker(socket).start();
			}
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
