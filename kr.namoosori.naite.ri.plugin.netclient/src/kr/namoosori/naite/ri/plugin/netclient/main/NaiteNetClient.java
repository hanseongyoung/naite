package kr.namoosori.naite.ri.plugin.netclient.main;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.ServerStateListener;
import kr.namoosori.naite.ri.plugin.netclient.provider.MessageProvider;
import kr.namoosori.naite.ri.plugin.netclient.provider.ServerStateProvider;
import kr.namoosori.naite.ri.plugin.netclient.work.EventInvoker;
import kr.namoosori.naite.ri.plugin.netclient.work.MulticastClient;
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
	
	private MulticastClient multicastClient;
	
	private NaiteNetClient() {
		//
		context = new NetClientContext();
		sender = new SocketMessageSender(context);
		invoker = new EventInvoker(context, sender);
		multicastClient = new MulticastClient(context);
	}
	
	public void start() {
		//
		this.invoker.startInvoke();
		this.multicastClient.start();
	}
	
	public void stop() {
		//
		this.invoker.stopInvoke();
		this.multicastClient.end();
	}
	
	public MessageSender getMessageSender() {
		//
		return this.sender;
	}

	public NetClientContext getContext() {
		return context;
	}
	
	public String getClientId() {
		return context.getClientId();
	}

	public void addMessageListener(MessageListener listener) {
		//
		MessageProvider provider = invoker.getMessageProvider();
		provider.addMessageListener(listener);
	}
	
	public void removeMessageListener(MessageListener listener) {
		//
		MessageProvider provider = invoker.getMessageProvider();
		provider.removeMessageListener(listener);
	}
	
	public void addServerStateListener(ServerStateListener listener) {
		//
		ServerStateProvider provider = invoker.getServerStateProvider();
		provider.addServerStateListener(listener);
	}
	
	public void removeServerStateListener(ServerStateListener listener) {
		//
		ServerStateProvider provider = invoker.getServerStateProvider();
		provider.removeServerStateListener(listener);
	}
}
