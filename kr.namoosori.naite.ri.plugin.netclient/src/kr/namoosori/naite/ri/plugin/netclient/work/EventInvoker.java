package kr.namoosori.naite.ri.plugin.netclient.work;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;

public class EventInvoker implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;
	
	private NetClientContext context;
	
	private MessageProvider messageProvider;
	
	private boolean continueInvoke;
	
	public EventInvoker(NetClientContext context) {
		//
		this.context = context;
		this.messageProvider = new MessageProvider();
	}
	
	public void startInvoke() {
		//
		this.continueInvoke = true;
		Thread thread = new Thread(this);
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
			NetServerResponse response = pull();
			if(response != null && response.hasMessage()) {
				invoke(response);
			}
			sleepForWhile();
		}
		System.out.println("EventInvoker stoped...");
	}
	
	private void invoke(NetServerResponse response) {
		//
		messageProvider.sendToListener(response);
	}

	private NetServerResponse pull() {
		//
		if (!context.isRequestAvailable()) {
			System.out.println("WARNING : server info not available");
			return null;
		}
		System.out.println("### PULL ###");
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

}
