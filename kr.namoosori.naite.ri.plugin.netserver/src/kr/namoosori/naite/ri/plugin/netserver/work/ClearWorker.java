package kr.namoosori.naite.ri.plugin.netserver.work;

import java.util.List;

import kr.namoosori.naite.ri.plugin.netserver.context.Client;
import kr.namoosori.naite.ri.plugin.netserver.context.Message;
import kr.namoosori.naite.ri.plugin.netserver.context.NetServerContext;

public class ClearWorker implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;

	private boolean continueRun;
	
	private NetServerContext context;
	private ServerEventManager eventManager;
	
	public ClearWorker(NetServerContext context, ServerEventManager eventManager) {
		//
		this.context = context;
		this.eventManager = eventManager;
	}

	@Override
	public void run() {
		//
		while(continueRun) {
			clear();
			sleepForWhile();
		}
		System.out.println("ClearWorker stoped...");
	}

	private void clear() {
		//
		List<Client> timeoutClients = context.getClientBox().findClientsByTimeoutInterval(INTERVAL);
		for (Client client : timeoutClients) {
			context.getClientBox().removeClient(client);
			clearMessages(client.getId());
			eventManager.sendToListener(client.getId(), false);
			System.out.println("client removed --> "+client.getId());
		}
	}

	private void clearMessages(String clientId) {
		//
		List<Message> messages = context.getMessageBox().findByClientId(clientId);
		for (Message message : messages) {
			context.getMessageBox().remove(message);
		}
	}

	public void start() {
		//
		this.continueRun = true;
		Thread thread = new Thread(this, "ClearWorker");
		thread.start();
		System.out.println("ClearWorker started...");
	}
	
	public void stop() {
		//
		this.continueRun = false;
	}
	
	private void sleepForWhile() {
		//
		try {
			Thread.sleep(INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
