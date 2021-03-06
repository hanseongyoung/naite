package kr.namoosori.naite.ri.plugin.teacher.server;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.namoosori.naite.ri.plugin.core.util.NaiteFileUtils;

import org.apache.commons.lang.StringUtils;
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
		//
		request.setCharacterEncoding("UTF-8");
		
		if (target.endsWith("create")) {
			baseRequest.setHandled(true);
			String id = request.getParameter("id");
			String email = request.getParameter("email");  // for Student
			String name = request.getParameter("name");
			String current = request.getParameter("current"); // for Lecture
			String projectName = request.getParameter("projectName"); // for ExciseProject
			String pass = request.getParameter("pass");   // for Student
			
			
			System.out.println("*********************************");
			System.out.println("createHandler:"+target + ", id:"+id + ", name:"+name);
			System.out.println("  - resourceBase:"+resourceBase);
			System.out.println("*********************************");
			
			String textFileUri = target.replaceAll("/create", ".txt");
			String textFileLoc = resourceBase + textFileUri;
			System.out.println("textFileLoc:"+textFileLoc);
			
			StringBuffer sb = new StringBuffer();
			sb.append(id);
			if (StringUtils.isNotEmpty(email)) {
				sb.append(";").append(email);
			}
			sb.append(";").append(name);
			if (StringUtils.isNotEmpty(current)) {
				sb.append(";").append(current);
			}
			if (StringUtils.isNotEmpty(projectName)) {
				sb.append(";").append(projectName);
			}
			if (StringUtils.isNotEmpty(pass)) {
				sb.append(";").append(pass);
			}
			
			// check dir exist
			NaiteFileUtils.checkDir(textFileLoc);
			
			//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(textFileLoc, true)));
			// Mac과 호환을 위하여 UTF-8로 인코딩하여 파일로 저장한다.
			PrintWriter out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(textFileLoc, true),
									"UTF-8")
							)
					);
			out.println(sb.toString());
			out.close();
		}
	}
}
