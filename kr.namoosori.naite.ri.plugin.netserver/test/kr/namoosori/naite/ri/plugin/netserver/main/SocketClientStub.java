package kr.namoosori.naite.ri.plugin.netserver.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientStub {
	private Socket socket;
	private BufferedReader fromServer;
	private BufferedWriter toServer;
	
	public String send(String message) {
		String serverMessage = null;
		try {
			socket = new Socket("localhost", 4000);
			fromServer = getReader(socket.getInputStream());
			toServer = getWriter(socket.getOutputStream());
			System.out.println("connect");
			write(message);
			while(!"ok".equals(serverMessage)) {
				serverMessage = receive();
			}
			write("ok");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (toServer != null) toServer.close();
				if (fromServer != null) fromServer.close();
				if (socket != null) socket.close();
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
		System.out.println("read-->"+read);
		return read;
	}
	
	private void write(String message) throws IOException {
		toServer.write(message + System.lineSeparator());
		toServer.flush();
		System.out.println("write-->"+message);
	}

}
