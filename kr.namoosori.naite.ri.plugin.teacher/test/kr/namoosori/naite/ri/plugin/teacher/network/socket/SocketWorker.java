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
	private BufferedReader fromClient;
	private BufferedWriter toClient;
	
	public SocketWorker(Socket socket) throws IOException {
		this.socket = socket;
		this.fromClient = getReader(socket.getInputStream());
		this.toClient = getWriter(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			String message = receive();
			write("ok-"+message);
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
//		String read = null;
//		StringBuffer sb = new StringBuffer();
//		while ((read = fromClient.readLine()) != null) {
//			sb.append(read);
//		}
//		return sb.toString();
		String read = fromClient.readLine();
		return read;
	}
	
	private void write(String message) throws IOException {
		toClient.write(message + System.lineSeparator());
		toClient.flush();
		System.out.println("write:"+message);
	}

}
