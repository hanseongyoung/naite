package kr.namoosori.naite.ri.plugin.netserver.work;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netserver.context.Client;
import kr.namoosori.naite.ri.plugin.netserver.context.ClientBox;
import kr.namoosori.naite.ri.plugin.netserver.context.Message;
import kr.namoosori.naite.ri.plugin.netserver.context.MessageBox;
import kr.namoosori.naite.ri.plugin.netserver.context.MethodType;

public class MessageManager {
	//
	private MessageBox messageBox;
	private ClientBox clientBox;
	
	public MessageManager(MessageBox messageBox, ClientBox clientBox) {
		//
		this.messageBox = messageBox;
		this.clientBox = clientBox;
	}

	// message format --> [sender ID]:[receiver ID]:[Method]:[Type]:[Message]
	// put message --> 'yumi:hong:PUT:MESSAGE:hello'    --> ok
	// get message --> 'hong::GET:MESSAGE:'             --> yumi:MESSAGE:hello  --> ok
	// put control --> 'yumi:hong:PUT:CONTROL:stop'     --> ok
	// get control --> 'hong::GET:CONTROL:'             --> yumi:CONTROL:stop   --> ok
	public List<Message> processMessage(Client fromClient, String messageBlock) {
		//
		MethodType method = Message.parseMethod(messageBlock);
		if (MethodType.PUT == method) {
			putMessage(fromClient, messageBlock);
			return null;
		} else if (MethodType.GET == method) {
			return getMyMessages(fromClient, messageBlock);
		}
		return null;
	}
	
	public void clearMessages(List<Message> messages) {
		//
		if (messages == null || messages.size() <= 0) {
			return;
		}
		messageBox.removeAll(messages);
	}

	private List<Message> getMyMessages(Client fromClient, String messageBlock) {
		// 
		List<Message> messages = messageBox.getMessages();
		List<Message> myMessages = new ArrayList<Message>();
		for (Message message : messages) {
			if (message.getTo().equals(fromClient)) {
				myMessages.add(message);
			}
		}
		return myMessages;
	}

	private void putMessage(Client fromClient, String messageBlock) {
		//
		String toClientId = Message.parseToClientId(messageBlock);
		if (toClientId.equals(Client.ALL)) {
			putMessageToAllOtherClients(fromClient, messageBlock);
		} else {
			putMessageToClient(fromClient, messageBlock);
		}
	}

	private void putMessageToClient(Client fromClient, String messageBlock) {
		//
		String toClientId = Message.parseToClientId(messageBlock);
		Client toClient = clientBox.findClient(toClientId);
		if (toClient == null) {
			return;
		}
		if (toClient.equals(fromClient)) {
			return;
		}
		
		messageBox.add(new Message(fromClient, toClient, messageBlock));
	}

	private void putMessageToAllOtherClients(Client fromClient, String messageBlock) {
		//
		List<Client> all = clientBox.getClients();
		List<Message> toClientMessages = new ArrayList<Message>();
		for (Client toClient : all) {
			if (!toClient.equals(fromClient)) {
				toClientMessages.add(new Message(fromClient, toClient, messageBlock));
			}
		}
		messageBox.addAll(toClientMessages);
	}

	

}
