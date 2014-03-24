package kr.namoosori.naite.ri.plugin.teacher.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class DeleteHandler extends AbstractHandler {
	//
	private String resourceBase;
	
	public DeleteHandler(String resourceBase) {
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
		
		if (target.endsWith("delete")) {
			baseRequest.setHandled(true);
			String id = request.getParameter("id");
			
			System.out.println("*********************************");
			System.out.println("deleteHandler:"+target + ", id:"+id);
			System.out.println("  - resourceBase:"+resourceBase);
			System.out.println("*********************************");
			
			String textFileUri = target.replaceAll("/delete", ".txt");
			String textFileLoc = resourceBase + textFileUri;
			System.out.println("textFileLoc:"+textFileLoc);
			
			deleteFile(id, textFileLoc);
			deleteLineInContents(id, textFileLoc);
		}
	}

	private void deleteFile(String id, String textFileLoc) {
		//
		File txtFile = new File(textFileLoc);
		String targetLine = null;
		try {
			//BufferedReader br = new BufferedReader(new FileReader(txtFile));
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), "UTF-8"));
			String line = null;
			while((line = br.readLine()) != null) {
				if (line.startsWith(id)) {
					targetLine = line;
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (targetLine == null) {
			return;
		}
		System.out.println("targetLine:"+targetLine);
		String[] strs = targetLine.split(";");
		if (strs.length < 2) {
			return;
		}
		String deleteFileName = strs[1];
		String deleteFileLoc = textFileLoc.replaceAll(".txt", "/" + deleteFileName);
		System.out.println("deleteFileLoc:"+deleteFileLoc);
		File deleteFile = new File(deleteFileLoc);
		if (deleteFile.exists()) {
			deleteFile.delete();
			System.out.println("file deleted --> " + deleteFileName);
		}
	}

	private void deleteLineInContents(String id, String textFileLoc) {
		//
		File origin = new File(textFileLoc);
		String tempFileLoc = textFileLoc + ".temp";
		File temp = new File(tempFileLoc);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(origin));
			PrintWriter out = new PrintWriter(new FileWriter(temp));
			String line = null;
			while((line = br.readLine()) != null) {
				if (line.startsWith(id)) {
					//
				} else {
					out.println(line);
					out.flush();
				}
			}
			out.close();
			br.close();
			
			origin.delete();
			temp.renameTo(origin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
