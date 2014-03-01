package kr.namoosori.naite.ri.plugin.teacher.network.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoClient {
	private String ip;
	private int port;
	private String str;

	public EchoClient(String ip, int port) throws IOException {

		this.ip = ip;
		this.port = port;
		Socket tcpSocket = getSocket(); // 사용자 메서드
		OutputStream os_socket = tcpSocket.getOutputStream(); // 소켓에 쓰고
		InputStream is_socket = tcpSocket.getInputStream(); // 소켓에서 읽는다

		BufferedWriter bufferW = new BufferedWriter(new OutputStreamWriter(
				os_socket));
		BufferedReader bufferR = new BufferedReader(new InputStreamReader(
				is_socket));
		System.out.print("입력 : ");

		str = "hello echo";
		str += System.getProperty("line.separator");
		bufferW.write(str);
		bufferW.flush();
		str = bufferR.readLine();
		System.out.println("Echo Result : " + str);

		bufferW.close();
		bufferR.close();
		tcpSocket.close();
	}

	public Socket getSocket() {
		Socket tcpSocket = null;
		try {
			tcpSocket = new Socket(ip, port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}
		return tcpSocket;
	}

	public static void main(String[] args) throws IOException {
		new EchoClient("10.0.1.83", 3000);
	}
}
