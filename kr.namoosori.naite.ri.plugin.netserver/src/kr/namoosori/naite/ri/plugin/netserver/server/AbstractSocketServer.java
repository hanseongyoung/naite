package kr.namoosori.naite.ri.plugin.netserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteRuntimeException;

public abstract class AbstractSocketServer extends ContinuedServerThread {
	//
	private ServerSocket serverSocket;
	
	public AbstractSocketServer(String serverName, long interval, boolean randomInterval) {
		//
		super(serverName, interval, randomInterval);
	}
	
	@Override
	protected void releaseServer() {
		//
		try {
			this.serverSocket.close();
			this.serverSocket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void prepareServer() {
		//
		try {
			this.serverSocket = new ServerSocket(4000);
			serverSocket.setSoTimeout(10000);
		} catch (IOException e) {
			throw new NaiteRuntimeException(e.getMessage());
		}
	}
	
	@Override
	protected void whileServing() {
		//
		Socket clientSocket = null;
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

	protected abstract void takeOver(Socket clientSocket);
}
