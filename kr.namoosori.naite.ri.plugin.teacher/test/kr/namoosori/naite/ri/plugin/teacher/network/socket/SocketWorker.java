package kr.namoosori.naite.ri.plugin.teacher.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWorker extends Thread {
	
	private Socket socket;
	private InputStream fromClient;
	private OutputStream toClient;
	
	public SocketWorker(Socket socket) throws IOException {
		this.socket = socket;
		this.fromClient = socket.getInputStream();
		this.toClient = socket.getOutputStream();
	}

	@Override
	public void run() {
		try {
			String message = receive(fromClient);
			write(toClient, "ok:"+message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fromClient.close();
				toClient.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		System.out.println("receive message:"+sb.toString());
		return sb.toString();
	}
	
	private void write(OutputStream outputStream, String message) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(message);
		bw.flush();
		System.out.println("write:"+message);
	}

}
