package kr.namoosori.naite.ri.plugin.netserver.server;

import java.io.IOException;
import java.net.Socket;

import kr.namoosori.naite.ri.plugin.netserver.context.NetServerContext;
import kr.namoosori.naite.ri.plugin.netserver.work.ConnectlessSocketWorker;

public class ConnectlessMessageServer extends AbstractSocketServer {
	//
	private NetServerContext context = new NetServerContext();
	
	public ConnectlessMessageServer() {
		//
		super("MessageServer", 0L, false);
	}
	
	@Override
	protected void takeOver(Socket clientSocket) {
		//
		try {
			ConnectlessSocketWorker worker = new ConnectlessSocketWorker(clientSocket, context);
			worker.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
