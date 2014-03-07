package kr.namoosori.naite.ri.plugin.netserver.server;

import java.io.IOException;
import java.net.Socket;

import kr.namoosori.naite.ri.plugin.netserver.context.NetServerContext;
import kr.namoosori.naite.ri.plugin.netserver.work.ClearWorker;
import kr.namoosori.naite.ri.plugin.netserver.work.ConnectlessSocketWorker;
import kr.namoosori.naite.ri.plugin.netserver.work.ServerEventManager;

public class ConnectlessMessageServer extends AbstractSocketServer {
	//
	private NetServerContext context = new NetServerContext();
	private ServerEventManager eventManager = new ServerEventManager();
	private ClearWorker clearWorker = new ClearWorker(context, eventManager);
	
	public ConnectlessMessageServer() {
		//
		super("MessageServer", 0L, false);
	}
	
	@Override
	protected void takeOver(Socket clientSocket) {
		//
		try {
			ConnectlessSocketWorker worker = new ConnectlessSocketWorker(clientSocket, context, eventManager);
			worker.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startServer() {
		//
		super.startServer();
		this.clearWorker.start();
	}

	@Override
	public void stopServer() {
		//
		this.clearWorker.stop();
		super.stopServer();
	}

	public ServerEventManager getEventManager() {
		return eventManager;
	}
	
}
