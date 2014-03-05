package kr.namoosori.naite.ri.plugin.netserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteRuntimeException;

public abstract class AbstractSocketServer implements Runnable {
	//
	private ServerSocket serverSocket;
	
	private boolean continueServe;
	
	public void startServer() {
		//
		this.continueServe = true;
		Thread serverThread = new Thread(this);
		serverThread.start();
	}
	
	public void stopServer() {
		//
		System.out.println("stop requested...");
		this.continueServe = false;
	}
	
	private void close() {
		//
		try {
			this.serverSocket.close();
			this.serverSocket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ServerSocket prepareServerSocket() {
		//
		try {
			ServerSocket serverSocket = new ServerSocket(4000);
			serverSocket.setSoTimeout(10000);
			return serverSocket;
		} catch (IOException e) {
			throw new NaiteRuntimeException(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		//
		this.serverSocket = prepareServerSocket();
		
		System.out.println("### start...");
		Socket clientSocket = null;
		while(continueServe) {
			try {
				System.out.println("wait...");
				synchronized (serverSocket) {
					clientSocket = serverSocket.accept();
				}
				takeOver(clientSocket);
			} catch (SocketTimeoutException e) {
				System.out.println("socket timeout...");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		close();
		System.out.println("### stoped...");
	}

	protected abstract void takeOver(Socket clientSocket);
}
