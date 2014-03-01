package kr.namoosori.naite.ri.plugin.teacher.network.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private BufferedReader bufferR;
	private BufferedWriter bufferW;
	private InputStream is;
	private OutputStream os;
	private ServerSocket serverS;

	public EchoServer(int port) {
		try {
			// ServerSocket 객체를 3000번 포트를 이용하여 생성.
			serverS = new ServerSocket(port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}

		while (true) {
			try {
				System.out.println("클라이언트의 요청을 기다리는 중");

				Socket tcpSocket = serverS.accept();
				System.out.println("클라이언트의 IP주소 : "
						+ tcpSocket.getInetAddress().getHostAddress());
				is = tcpSocket.getInputStream(); // '소켓으로부터' 읽고
				os = tcpSocket.getOutputStream(); // '소켓에' 쓴다.
				bufferR = new BufferedReader(new InputStreamReader(is));
				bufferW = new BufferedWriter(new OutputStreamWriter(os));
				String message = bufferR.readLine();
				System.out.println("수신메시지 : " + message);
				message += System.getProperty("line.separator"); // 엔터키넣기
				bufferW.write(message);
				bufferW.flush();
				bufferR.close();
				bufferW.close();
				tcpSocket.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new EchoServer(3000);
	}
}
