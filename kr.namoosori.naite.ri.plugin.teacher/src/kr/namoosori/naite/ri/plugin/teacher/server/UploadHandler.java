package kr.namoosori.naite.ri.plugin.teacher.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.util.NaiteFileUtils;
import kr.namoosori.naite.ri.plugin.teacher.server.multipart.HttpServletMultipartRequest;
import kr.namoosori.naite.ri.plugin.teacher.server.multipart.MultipartFile;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class UploadHandler extends AbstractHandler {
	//
	private String resourceBase;
	
	public UploadHandler(String resourceBase) {
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
		if (target.endsWith("upload")) {
			baseRequest.setHandled(true);
			System.out.println("*********************************");
			System.out.println("uploadHandler:"+target);
			System.out.println("  - resourceBase:"+resourceBase);
			System.out.println("*********************************");
			
			String fileUri = target.replaceAll("/upload", "/");
			String fileLoc = resourceBase + fileUri;
			System.out.println("fileLoc:"+fileLoc);
			
			HttpServletMultipartRequest multiReq = new HttpServletMultipartRequest(request);
			MultipartFile multiFile = multiReq.getFileParameter("file1");
			String paramFileName = multiReq.getParameter("fileName1");
			
			paramFileName = StringUtils.substringAfterLast(paramFileName, "\n");
			paramFileName = new String(paramFileName.getBytes("ISO-8859-1"), "UTF-8");
			System.out.println("*** paramFileName["+paramFileName+"]");
			
			//------------------------------------------------------------------
			InputStream in = multiFile.getInputStream();
			try {
				NaiteFileUtils.saveFile(fileLoc + paramFileName, in);
			} catch (NaiteException e) {
				e.printStackTrace();
			}
		}
	}

}
