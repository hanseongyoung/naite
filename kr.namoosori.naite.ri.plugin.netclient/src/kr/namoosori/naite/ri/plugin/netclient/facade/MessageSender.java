package kr.namoosori.naite.ri.plugin.netclient.facade;

import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;

public interface MessageSender {
	//
	/**
	 * Send a message to other network client.
	 * @param receiverId client's id on network
	 * @param sendMessage a message object to send
	 * @return send result
	 */
	public String send(String senderId, String receiverId, SendMessage sendMessage);
	
	/**
	 * Send a message to all other clients on network.
	 * Registered clients will received this message.
	 * @param message a message object to send
	 * @return send result
	 */
	public String sendAll(String senderId, SendMessage sendMessage);
	
	/**
	 * @return
	 */
	public List<ClientMessage> getMessages(String clientId);
}
