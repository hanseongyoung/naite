package kr.namoosori.naite.ri.plugin.teacher.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

public class NaiteServer {
	//
	private static NaiteServer instance = new NaiteServer();
	public static final int PORT = 19193;
	
	private Server server;
	
	private String resourceBase;
	
	public static NaiteServer getInstance() {
		//
		return instance;
	}
	
	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}
	
	public String getResourceBase() {
		return resourceBase;
	}

	public void start() {
		//
		init();
		
		try {
			server.start();
			System.out.println("naite server started...");
			//server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		//
		server = new Server();
		
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(PORT);
		server.addConnector(connector);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{"index.html"});
		resourceHandler.setResourceBase(resourceBase);
		
		UploadHandler uploadHandler = new UploadHandler(resourceBase);
		CreateHandler createHandler = new CreateHandler(resourceBase);
		DeleteHandler deleteHandler = new DeleteHandler(resourceBase);
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[]{resourceHandler, createHandler, uploadHandler, deleteHandler, new DefaultHandler()});
		
		server.setHandler(handlers);
	}
	
	public void stop() {
		//
		if (server == null) {
			return;
		}
		
		try {
			server.stop();
			System.out.println("naite server stoped...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		server = null;
	}
	
	public static void main(String[] args) {
		//
		NaiteServer server = NaiteServer.getInstance();
		server.setResourceBase("D:\\temp");
		server.start();
	}

}
