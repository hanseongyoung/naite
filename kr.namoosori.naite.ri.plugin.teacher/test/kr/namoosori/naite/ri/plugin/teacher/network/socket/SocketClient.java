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
	private BufferedReader fromServer;
	private BufferedWriter toServer;
	
	public String send(String message) {
		String serverMessage = null;
		try {
			socket = new Socket("10.0.1.83", 4449);
			fromServer = getReader(socket.getInputStream());
			toServer = getWriter(socket.getOutputStream());
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
	
	private BufferedWriter getWriter(OutputStream outputStream) {
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		BufferedWriter bw = new BufferedWriter(out);
		return bw;
	}

	private BufferedReader getReader(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(reader);
		return br;
	}

	private String receive() throws IOException {
		String read = fromServer.readLine();
		System.out.println(socket.getInetAddress()+":"+read);
		return read;
	}
	
	private void write(String message) throws IOException {
		toServer.write(message + System.lineSeparator());
		toServer.flush();
		System.out.println("me:"+message);
	}

	public static void main(String[] args) throws Exception {
		SocketClient client = new SocketClient();
		String receive = client.send("hello teacher");
		System.out.println("server say:"+receive);
	}

}
