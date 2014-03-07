package kr.namoosori.naite.ri.plugin.netclient.work;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteRuntimeException;
import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;

public class SocketMessageSender implements MessageSender {
	//
	private NetClientContext context;

	public SocketMessageSender(NetClientContext context) {
		//
		this.context = context;
	}

	@Override
	public String send(String receiverId, SendMessage sendMessage) {
		//
		if (!context.isRequestAvailable()) {
			throw new NaiteRuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asMessageRequest(
				context.getClientId(), receiverId, sendMessage.toMessageBlock());
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}

	@Override
	public String sendAll(SendMessage sendMessage) {
		//
		if (!context.isRequestAvailable()) {
			throw new NaiteRuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = NetServerRequest.asMessageRequest(
				context.getClientId(), NetServerRequest.RECEIVER_ALL, sendMessage.toMessageBlock());
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}

}
