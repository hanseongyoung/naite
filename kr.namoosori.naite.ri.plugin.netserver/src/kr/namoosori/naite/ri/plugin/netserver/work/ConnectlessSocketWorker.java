package kr.namoosori.naite.ri.plugin.netserver.work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import kr.namoosori.naite.ri.plugin.netserver.context.Client;
import kr.namoosori.naite.ri.plugin.netserver.context.Message;
import kr.namoosori.naite.ri.plugin.netserver.context.NetServerContext;

public class ConnectlessSocketWorker extends Thread {
	//
	private NetServerContext context;
	private MessageManager messageManager;
	private ClientManager clientManager;
	
	private Socket socket;
	private BufferedReader fromClient;
	private BufferedWriter toClient;
	
	public ConnectlessSocketWorker(Socket socket, NetServerContext context) throws IOException {
		//
		this.socket = socket;
		this.fromClient = getReader(socket.getInputStream());
		this.toClient = getWriter(socket.getOutputStream());
		this.context = context;
		this.messageManager = new MessageManager(context.getMessageBox(), context.getClientBox());
		this.clientManager = new ClientManager(context.getClientBox());
	}

	@Override
	public void run() {
		//
		try {
			String messageBlock = receiveMessage();
			Client fromClient = registClient(messageBlock);
			List<Message> messages = processMessage(fromClient, messageBlock);
			sendMessages(messages);
			String clientEndResponse = receiveMessage();
			if (clientEndResponse.equals(Message.OK)) {
				clearMessage(messages);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fromClient.close();
				toClient.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clearMessage(List<Message> messages) {
		//
		messageManager.clearMessages(messages);
	}

	private void sendMessages(List<Message> messages) throws IOException {
		//
		if (messages != null) {
			for (Message message : messages) {
				writeMessage(message.toClientMessage());
			}
		}
		writeMessage(Message.OK);
	}

	private List<Message> processMessage(Client fromClient, String messageBlock) {
		//
		return messageManager.processMessage(fromClient, messageBlock);
	}

	private Client registClient(String messageBlock) {
		//
		return clientManager.registClient(socket.getInetAddress().toString(), messageBlock);
	}

	private String receiveMessage() throws IOException {
		String read = fromClient.readLine();
		System.out.println(socket.getInetAddress() + "-->" + read);
		return read;
	}
	private void writeMessage(String message) throws IOException {
		toClient.write(message + System.lineSeparator());
		toClient.flush();
		System.out.println("write-->"+message);
	}
	
	private BufferedWriter getWriter(OutputStream outputStream) {
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		BufferedWriter bw = new BufferedWriter(out);
		return bw;
	}

	private BufferedReader getReader(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(reader);
		return br;
	}

}
