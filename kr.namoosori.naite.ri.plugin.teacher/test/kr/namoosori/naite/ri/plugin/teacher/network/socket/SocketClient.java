package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	private Socket socket;
	
	public String send(String message) throws UnknownHostException, IOException {
		socket = new Socket("10.0.1.83", 4449);
		System.out.println("connect");
		write(socket.getOutputStream(), message);
		String serverMessage = receive(socket.getInputStream());
		return serverMessage;
	};
	
	private String receive(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(reader);
		String read = null;
		StringBuffer sb = new StringBuffer();
		while ((read = br.readLine()) != null) {
			sb.append(read);
		}
		br.close();
		reader.close();
		inputStream.close();
		return sb.toString();
	}
	
	private void write(OutputStream outputStream, String message) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(message);
		bw.flush();
		bw.close();
		out.close();
		outputStream.close();
		System.out.println("write:"+message);
	}

	public static void main(String[] args) throws Exception {
		SocketClient client = new SocketClient();
		String receive = client.send("hello teacher");
		System.out.println("server message:"+receive);
	}

}
