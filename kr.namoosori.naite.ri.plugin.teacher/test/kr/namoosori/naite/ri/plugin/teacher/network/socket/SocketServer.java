package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
				String message = receive(socket.getInputStream());
				System.out.println("receive message:"+message);
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String receive(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(reader);
		String read = null;
		StringBuffer sb = new StringBuffer();
		while ((read = br.readLine()) != null) {
			sb.append(read);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.start();
		System.out.println("start!");
	}

}
