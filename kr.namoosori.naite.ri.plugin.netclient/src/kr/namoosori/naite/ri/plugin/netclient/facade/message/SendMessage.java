package kr.namoosori.naite.ri.plugin.netclient.facade.message;

import java.util.ArrayList;
import java.util.List;

public class SendMessage {
	//
	private String command;
	private List<NameValue> nameValues = new ArrayList<NameValue>();
	
	public SendMessage(){
		//
	}
	
	private SendMessage(String message) {
		//
		parseMessage(message);
	}
	
	public String toMessageBlock() {
		//
		StringBuffer sb = new StringBuffer();
		sb.append(command);
		
		if (nameValues == null || nameValues.size() <= 0) {
			return sb.toString();
		}
		
		sb.append("?");
		for (int i = 0; i < nameValues.size(); i++) {
			NameValue nameValue = nameValues.get(i);
			sb.append(nameValue.toMessageBlock());
			if (i < (nameValues.size() - 1)) {
				sb.append("&");
			}
		}
		return sb.toString();
	}
	
	// [command]?[key1]=[value1]&[key2]=[value2]...
	private void parseMessage(String message) {
		//
		if (message.indexOf("?") < 0) {
			this.command = message;
			return;
		}
		
		String[] strArr = message.split("\\?");
		this.command = strArr[0];
		this.nameValues = NameValue.createList(strArr[1]);
	}
	
	public void addNameValue(NameValue nameValue) {
		//
		this.nameValues.add(nameValue);
	}

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public List<NameValue> getNameValues() {
		return nameValues;
	}
	public void setNameValues(List<NameValue> nameValues) {
		this.nameValues = nameValues;
	}

	public static SendMessage create(String message) {
		//
		if (message == null || message.length() <= 0) {
			return null;
		}
		return new SendMessage(message);
	}

	public String getValue(String name) {
		//
		return NameValue.getValue(name, nameValues);
	}

}