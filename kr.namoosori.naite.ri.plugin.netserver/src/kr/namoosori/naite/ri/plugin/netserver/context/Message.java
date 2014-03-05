package kr.namoosori.naite.ri.plugin.netserver.context;


public class Message {
	//
	public static final String OK = "ok";
	public static final String MESSAGE_DELIM = ":";
	public static final int INDEX_FROM = 0;
	public static final int INDEX_TO = 1;
	public static final int INDEX_METHOD = 2;
	public static final int INDEX_TYPE = 3;
	public static final int INDEX_MESSAGE = 4;
	
	private Client from;
	private Client to;
	private MethodType method; // PUT,GET
	private MessageType type; // MESSAGE, CONTROL
	private String message;
	
	public Message(Client from, Client to, String messageBlock) {
		//
		this.from = from;
		this.to = to;
		parse(messageBlock);
	}
	
	//request message format  --> [sender ID]:[receiver ID]:[Method]:[Type]:[Message]
	//response message format --> [sender ID]:[Type]:[Message]
	//put message --> 'yumi:hong:PUT:MESSAGE:hello'    --> ok
	//get message --> 'hong::GET:MESSAGE:'             --> yumi:MESSAGE:hello  --> ok
	//put control --> 'yumi:hong:PUT:CONTROL:stop'     --> ok
	//get control --> 'hong::GET:CONTROL:'             --> yumi:CONTROL:stop   --> ok
	private void parse(String messageBlock) {
		//
		String[] strArr = messageBlock.split(MESSAGE_DELIM);
		this.method = MethodType.valueOf(strArr[INDEX_METHOD]);
		this.type = MessageType.valueOf(strArr[INDEX_TYPE]);
		this.message = strArr[INDEX_MESSAGE];
	}
	
	public String toClientMessage() {
		//
		return from.getId() + MESSAGE_DELIM + type.name() + MESSAGE_DELIM + message;
	}

	public Client getFrom() {
		return from;
	}
	public void setFrom(Client from) {
		this.from = from;
	}
	public Client getTo() {
		return to;
	}
	public void setTo(Client to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public MethodType getMethod() {
		return method;
	}
	public void setMethod(MethodType method) {
		this.method = method;
	}

	public static String parseFromClientId(String messageBlock) {
		//
		String[] strArr = messageBlock.split(MESSAGE_DELIM);
		return strArr[INDEX_FROM];
	}
	
	public static String parseToClientId(String messageBlock) {
		//
		String[] strArr = messageBlock.split(MESSAGE_DELIM);
		return strArr[INDEX_TO];
	}

	public static MethodType parseMethod(String messageBlock) {
		//
		String[] strArr = messageBlock.split(MESSAGE_DELIM);
		return MethodType.valueOf(strArr[INDEX_METHOD]);
	}
	
}
