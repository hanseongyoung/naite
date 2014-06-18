package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StandardProject {
	//
	private String id;

	private String fileName;

	private String projectName;

	public StandardProject() {
		//
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public static List<StandardProject> createDomainsByJson(String jsonStr) {
		Type listType = new TypeToken<ArrayList<StandardProject>>() {
		}.getType();
		List<StandardProject> projects = new Gson().fromJson(jsonStr, listType);
		return projects;
	}

}
