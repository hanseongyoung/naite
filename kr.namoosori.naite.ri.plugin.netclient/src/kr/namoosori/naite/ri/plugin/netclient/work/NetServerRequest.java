package kr.namoosori.naite.ri.plugin.netclient.work;

public class NetServerRequest {
	//
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_GET = "GET";
	public static final String TYPE_MESSAGE = "MESSAGE";
	public static final String TYPE_CONTROL = "CONTROL";
	public static final String RECEIVER_ALL = "*";
	private static final String DELIM = ":";
	
	private String senderId;
	private String receiverId;
	private String method; // PUT, GET
	private String type;   // MESSAGE, CONTROL
	private String message;

	public NetServerRequest(String senderId, String receiverId, String message) {
		//
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.method = METHOD_PUT;
		this.type = TYPE_MESSAGE;
	}

	public String toMessageBlock() {
		//
		String block = "";
		block += (senderId != null ? senderId : "");
		block += DELIM + (receiverId != null ? receiverId : "");
		block += DELIM + (method != null ? method : "");
		block += DELIM + (type != null ? type : "");
		block += DELIM + (message != null ? message : "");
		return block;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
