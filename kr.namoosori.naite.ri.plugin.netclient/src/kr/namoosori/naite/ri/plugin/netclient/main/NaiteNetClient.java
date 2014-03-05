package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.work.EventInvoker;
import kr.namoosori.naite.ri.plugin.netclient.work.SocketMessageSender;

public class NaiteNetClient {
	//
	private static NaiteNetClient instance = new NaiteNetClient();
	
	public static NaiteNetClient getInstance() {
		//
		return instance;
	}
	
	private NetClientContext context;
	
	private EventInvoker invoker;
	
	private MessageSender sender;
	
	private NaiteNetClient() {
		//
		context = new NetClientContext();
		invoker = new EventInvoker(context);
		sender = new SocketMessageSender(context);
	}
	
	public void start() {
		//
		this.invoker.startInvoke();
	}
	
	public void stop() {
		//
		this.invoker.stopInvoke();
	}
	
	public MessageSender getMessageSender() {
		//
		return this.sender;
	}
}
