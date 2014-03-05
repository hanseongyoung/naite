package kr.namoosori.naite.ri.plugin.netclient.work;

import java.util.ArrayList;
import java.util.List;

public class NetServerResponse {
	//
	public static final String RESPONSE_OK = "ok";
	public static final String RESPONSE_ERROR = "error";
	
	private NetServerRequest request;
	
	private String responseStatus;
	private List<String> responseMessages = new ArrayList<String>();

	public NetServerResponse(NetServerRequest request) {
		//
		this.request = request;
	}
	
	public void addResponseMessage(String message) {
		//
		this.responseMessages.add(message);
	}

	public NetServerRequest getRequest() {
		return request;
	}

	public List<String> getResponseMessages() {
		return responseMessages;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

}
