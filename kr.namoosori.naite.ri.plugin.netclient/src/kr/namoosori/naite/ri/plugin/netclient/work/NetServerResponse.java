package kr.namoosori.naite.ri.plugin.netclient.work;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netclient.facade.ClientMessage;

public class NetServerResponse {
	//
	public static final String RESPONSE_OK = "ok";
	public static final String RESPONSE_ERROR = "error";
	
	private NetServerRequest request;
	
	private String responseStatus;
	private List<ClientMessage> responseMessages = new ArrayList<ClientMessage>();

	public NetServerResponse(NetServerRequest request) {
		//
		this.request = request;
	}
	
	public void addResponseMessage(String message) {
		//
		if (RESPONSE_OK.equals(message) || RESPONSE_ERROR.equals(message)) {
			this.responseStatus = message;
		} else {
			//[sender ID]:[Type]:[Message]
			String[] arr = message.split(NetServerRequest.DELIM);
			this.responseMessages.add(new ClientMessage(arr[0], arr[2]));
		}
	}
	
	public boolean hasMessage() {
		//
		if (responseMessages != null && responseMessages.size() > 0) {
			return true;
		}
		return false;
	}

	public NetServerRequest getRequest() {
		return request;
	}

	public List<ClientMessage> getResponseMessages() {
		return responseMessages;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

}
