package kr.namoosori.naite.ri.plugin.netclient.work;

import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;

public class EventInvoker implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;
	
	private NetClientContext context;
	
	private MessageSender sender;
	
	private boolean continueInvoke;
	
	public EventInvoker(NetClientContext context, MessageSender sender) {
		//
		this.context = context;
		this.sender = sender;
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
			EventManager.getInstance().invokeServerStateEvent(serverState);
			prevServerState = serverState;
		}
	}

	private void checkMessage() {
		//
		List<ClientMessage> messages = sender.getMessages(context.getClientId());
		if(messages != null && messages.size() > 0) {
			EventManager.getInstance().invokeMessageEvent(messages);
		}
	}

	private void sleepForWhile() {
		//
		try {
			Thread.sleep((long) (Math.random() * INTERVAL));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
