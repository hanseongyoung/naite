package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	private Socket socket;
	
	public void send(String message) throws UnknownHostException, IOException {
		socket = new Socket("10.0.1.83", 4449);
		System.out.println("connect");
		write(socket.getOutputStream(), message);
	}
	
	private void write(OutputStream outputStream, String message) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(message);
		bw.flush();
		System.out.println("write:"+message);
	}

	public static void main(String[] args) throws Exception {
		SocketClient client = new SocketClient();
		client.send("hello teacher");
	}

}
