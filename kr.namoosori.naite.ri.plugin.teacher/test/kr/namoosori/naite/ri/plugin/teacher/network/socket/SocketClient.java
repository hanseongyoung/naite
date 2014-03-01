package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.net.Socket;

public class SocketClient {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("10.0.1.83", 4449);
		System.out.println("connect");
	}

}
