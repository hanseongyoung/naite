package kr.namoosori.naite.ri.plugin.netclient.work;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;

public class EventInvoker implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;
	
	private NetClientContext context;
	
	private boolean continueInvoke;
	
	public EventInvoker(NetClientContext context) {
		//
		this.context = context;
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
			sleepForWhile();
		}
		System.out.println("EventInvoker stoped...");
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
