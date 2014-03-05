package kr.namoosori.naite.ri.plugin.netclient.work;

public class NetServerRequest {
	//
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_GET = "GET";
	public static final String TYPE_MESSAGE = "MESSAGE";
	public static final String TYPE_CONTROL = "CONTROL";
	public static final String RECEIVER_ALL = "*";
	public static final String DELIM = ":";
	
	public static NetServerRequest asMessageRequest(String senderId, String receiverId, String message) {
		//
		//System.out.println("sender:"+senderId+",receiver:"+receiverId+",message:"+message);
		NetServerRequest req = new NetServerRequest(senderId, message);
		req.setReceiverId(receiverId);
		req.setMethod(METHOD_PUT);
		req.setType(TYPE_MESSAGE);
		return req;
	}
	
	public static NetServerRequest asPullRequest(String senderId) {
		//
		NetServerRequest req = new NetServerRequest(senderId, null);
		req.setMethod(METHOD_GET);
		req.setType(TYPE_MESSAGE);
		return req;
	}
	
	private String senderId;
	private String receiverId;
	private String method; // PUT, GET
	private String type;   // MESSAGE, CONTROL
	private String message;

	public NetServerRequest(String senderId, String message) {
		//
		this.senderId = senderId;
		this.message = message;
	}

	//request message format  --> [sender ID]:[receiver ID]:[Method]:[Type]:[Message]
	//response message format --> [sender ID]:[Type]:[Message]
	//put message --> 'yumi:hong:PUT:MESSAGE:hello'    --> ok
	//get message --> 'hong::GET:MESSAGE:'             --> yumi:MESSAGE:hello  --> ok
	//put control --> 'yumi:hong:PUT:CONTROL:stop'     --> ok
	//get control --> 'hong::GET:CONTROL:'             --> yumi:CONTROL:stop   --> ok
	public String toMessageBlock() {
		//
		StringBuffer sb = new StringBuffer();
		sb.append(senderId != null ? senderId : "").append(DELIM);
		sb.append(receiverId != null ? receiverId : "").append(DELIM);
		sb.append(method != null ? method : "").append(DELIM);
		sb.append(type != null ? type : "").append(DELIM);
		sb.append(message != null ? message : "");
		return sb.toString();
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
