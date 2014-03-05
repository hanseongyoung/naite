package kr.namoosori.naite.ri.plugin.netclient.work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectlessSocketClient {
	//
	private String serverIp;
	private int port;
	
	private Socket socket;
	private BufferedReader fromServer;
	private BufferedWriter toServer;
	
	public ConnectlessSocketClient(String serverIp, int port) {
		//
		this.serverIp = serverIp;
		this.port = port;
	}
	
	public NetServerResponse send(NetServerRequest request) {
		NetServerResponse response = new NetServerResponse(request);
		try {
			socket = new Socket(serverIp, port);
			fromServer = getReader(socket.getInputStream());
			toServer = getWriter(socket.getOutputStream());
			System.out.println("connect");
			write(request.toMessageBlock());
			
			String serverMessage = null;
			while(!"ok".equals(serverMessage)) {
				serverMessage = receive();
				response.addResponseMessage(serverMessage);
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
		return response;
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
