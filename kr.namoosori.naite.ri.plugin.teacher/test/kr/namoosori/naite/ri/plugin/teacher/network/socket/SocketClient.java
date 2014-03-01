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
	private InputStream fromServer;
	private OutputStream toServer;
	
	public String send(String message) {
		String serverMessage = null;
		try {
			socket = new Socket("10.0.1.83", 4449);
			fromServer = socket.getInputStream();
			toServer = socket.getOutputStream();
			System.out.println("connect");
			write(message);
			serverMessage = receive();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				toServer.close();
				fromServer.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return serverMessage;
	}
	
	private String receive() throws IOException {
		InputStreamReader reader = new InputStreamReader(fromServer);
		BufferedReader br = new BufferedReader(reader);
		String read = null;
		StringBuffer sb = new StringBuffer();
		while ((read = br.readLine()) != null) {
			sb.append(read);
		}
		return sb.toString();
	}
	
	private void write(String message) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(toServer);
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(message);
		bw.flush();
		System.out.println("write:"+message);
	}

	public static void main(String[] args) throws Exception {
		SocketClient client = new SocketClient();
		String receive = client.send("hello teacher");
		System.out.println("server message:"+receive);
	}

}
