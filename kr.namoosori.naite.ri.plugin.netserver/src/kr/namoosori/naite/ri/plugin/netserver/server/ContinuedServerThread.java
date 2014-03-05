package kr.namoosori.naite.ri.plugin.netserver.server;


public abstract class ContinuedServerThread implements Runnable {
	//
	private boolean continueServe;
	private String serverName;
	private long interval;
	private boolean randomInterval;
	
	public ContinuedServerThread(String serverName, long interval, boolean randomInterval) {
		//
		this.serverName = serverName;
		this.interval = interval;
		this.randomInterval = randomInterval;
	}
	
	public void startServer() {
		//
		this.continueServe = true;
		Thread serverThread = new Thread(this, serverName);
		serverThread.start();
	}
	
	public void stopServer() {
		//
		System.out.println("[" + serverName + "] stop requested...");
		this.continueServe = false;
	}
	
	@Override
	public void run() {
		//
		System.out.println("[" + serverName + "] starting...");
		prepareServer();
		while(continueServe) {
			whileServing();
			sleepForWhile();
		}
		releaseServer();
		System.out.println("[" + serverName + "] stoped...");
	}
	
	private void sleepForWhile() {
		//
		if (interval <= 0L) {
			return;
		}
		
		long intervalTime = interval;
		if (randomInterval) {
			intervalTime = (long) (Math.random() * interval);
		}
		
		try {
			Thread.sleep(intervalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1. prepare server resource.
	 */
	protected abstract void prepareServer();
	
	/**
	 * 2. continued called while server is not stop.
	 */
	protected abstract void whileServing();
	
	/**
	 * 3. release server resource.
	 */
	protected abstract void releaseServer();

}
