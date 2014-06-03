package kr.namoosori.naite.ri.plugin.netclient.work;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteRuntimeException;
import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;

public class SocketMessageSender implements MessageSender {
	//
	private NetClientContext context;

	public SocketMessageSender(NetClientContext context) {
		//
		this.context = context;
	}

	@Override
	public String send(String senderId, String receiverId, SendMessage sendMessage) {
		//
		if (!context.isRequestAvailable()) {
			throw new NaiteRuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asMessageRequest(
				senderId, receiverId, sendMessage.toMessageBlock());
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}

	@Override
	public String sendAll(String senderId, SendMessage sendMessage) {
		//
		if (!context.isRequestAvailable()) {
			throw new NaiteRuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asMessageRequest(
				senderId, NetServerRequest.RECEIVER_ALL, sendMessage.toMessageBlock());
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}
	
	public List<ClientMessage> getMessages(String clientId) {
		if (!context.isRequestAvailable()) {
			throw new NaiteRuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asPullRequest(clientId);
		NetServerResponse res = client.send(req);
		return res.getResponseMessages();
	}

}
