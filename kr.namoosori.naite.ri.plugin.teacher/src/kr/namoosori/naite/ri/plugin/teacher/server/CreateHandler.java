package kr.namoosori.naite.ri.plugin.teacher.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class CreateHandler extends AbstractHandler {
	//
	private String resourceBase;
	
	public CreateHandler(String resourceBase) {
		//
		this.resourceBase = resourceBase;
	}
	
	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (target.endsWith("create")) {
			baseRequest.setHandled(true);
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			System.out.println("*********************************");
			System.out.println("createHandler:"+target + ", id:"+id + ", name:"+name);
			System.out.println("  - resourceBase:"+resourceBase);
			System.out.println("*********************************");
			
			String textFileUri = target.replaceAll("/create", ".txt");
			String textFileLoc = resourceBase + textFileUri;
			System.out.println("textFileLoc:"+textFileLoc);
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(textFileLoc, true)));
			out.println(id + ";" + name);
			out.close();
		}
	}
}
