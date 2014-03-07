package kr.namoosori.naite.ri.plugin.netclient.work;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.provider.MessageProvider;
import kr.namoosori.naite.ri.plugin.netclient.provider.ServerStateProvider;

public class EventInvoker implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;
	
	private NetClientContext context;
	
	private MessageProvider messageProvider;
	private ServerStateProvider serverStateProvider;
	
	private boolean continueInvoke;
	
	public EventInvoker(NetClientContext context) {
		//
		this.context = context;
		this.messageProvider = new MessageProvider();
		this.serverStateProvider = new ServerStateProvider();
	}
	
	public void startInvoke() {
		//
		this.continueInvoke = true;
		Thread thread = new Thread(this, "EventInvoker");
		thread.start();
		System.out.println("EventInvoker started...");
	}

	public void stopInvoke() {
		//
		this.continueInvoke = false;
	}

	@Override
	public void run() {
		//
		while(continueInvoke) {
			checkServerState();
			checkMessage();
			sleepForWhile();
		}
		System.out.println("EventInvoker stoped...");
	}
	
	private boolean prevServerState;
	private void checkServerState() {
		//
		boolean serverState = context.isServerAlive();
		if (prevServerState != serverState) {
			serverStateProvider.sendToListener(serverState);
			prevServerState = serverState;
		}
	}

	private void checkMessage() {
		//
		NetServerResponse response = pull();
		if(response != null && response.hasMessage()) {
			messageProvider.sendToListener(response);
		}
	}

	private NetServerResponse pull() {
		//
		if (!context.isRequestAvailable()) {
			System.out.println("WARNING : server info not available - "+context.getServerIp() + ", "+context.getClientId());
			return null;
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asPullRequest(context.getClientId());
		NetServerResponse res = client.send(req);
		return res;
	}

	private void sleepForWhile() {
		//
		try {
			Thread.sleep((long) (Math.random() * INTERVAL));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public MessageProvider getMessageProvider() {
		return messageProvider;
	}

	public ServerStateProvider getServerStateProvider() {
		return serverStateProvider;
	}

}
