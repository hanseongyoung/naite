package kr.namoosori.naite.ri.plugin.netclient.work;

import kr.namoosori.naite.ri.plugin.netclient.context.NetClientContext;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;

public class SocketMessageSender implements MessageSender {
	//
	private NetClientContext context;

	public SocketMessageSender(NetClientContext context) {
		//
		this.context = context;
	}

	@Override
	public String send(String receiverId, String message) {
		if (!context.isRequestAvailable()) {
			throw new RuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = new NetServerRequest(context.getClientId(),
				receiverId, message);
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}

	@Override
	public String sendAll(String message) {
		if (!context.isRequestAvailable()) {
			throw new RuntimeException("server info not exist.");
		}
		ConnectlessSocketClient client = new ConnectlessSocketClient(
				context.getServerIp(), context.getServerPort());
		NetServerRequest req = new NetServerRequest(context.getClientId(),
				NetServerRequest.RECEIVER_ALL, message);
		NetServerResponse res = client.send(req);
		return res.getResponseStatus();
	}

}
