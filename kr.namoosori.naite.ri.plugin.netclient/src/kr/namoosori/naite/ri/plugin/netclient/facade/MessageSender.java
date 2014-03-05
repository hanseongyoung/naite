package kr.namoosori.naite.ri.plugin.netclient.facade;

public interface MessageSender {
	//
	/**
	 * Send a message to other network client.
	 * @param receiverId client's id on network
	 * @param message a message to send
	 * @return send result
	 */
	public String send(String receiverId, String message);
	
	/**
	 * Send a message to all other clients on network.
	 * Registered clients will received this message.
	 * @param message a message to send
	 * @return send result
	 */
	public String sendAll(String message);
}
