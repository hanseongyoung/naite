package kr.namoosori.naite.ri.plugin.core.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.CoreContext;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class NaiteWorkspace {
	//
	private static NaiteWorkspace instance = new NaiteWorkspace();
	
	public static NaiteWorkspace getInstance() {
		//
		return instance;
	}
	
	private IWorkspaceRoot workspace;
	
	private NaiteWorkspace() {
		//
		this.workspace = ResourcesPlugin.getWorkspace().getRoot();
	}
	
	public File getRootLocationAsFile() {
		//
		return workspace.getLocation().toFile();
	}
	
	public String getRootLocation() {
		//
		return workspace.getLocation().toString();
	}
	
	public List<NaiteProject> getProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (projects == null || projects.length <= 0) {
			return null;
		}
		List<NaiteProject> naiteProjects = new ArrayList<NaiteProject>();
		for (IProject iProject : projects) {
			ExerciseProject exerciseProject = new ExerciseProject(iProject.getName());
			exerciseProject.setLecture((Lecture)CoreContext.CURRENT_LECTURE);
			naiteProjects.add(new NaiteProject(iProject, exerciseProject));
		}
		return naiteProjects;
	}
	
	public boolean exist(String projectName) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (projects == null || projects.length <= 0) {
			return false;
		}
		
		for (IProject iProject : projects) {
			if (iProject.getName().equals(projectName)) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getProjectNames() {
		//
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<String> projectNames = new ArrayList<String>();
		for (IProject project : projects) {
			projectNames.add(project.getName());
		}
		return projectNames;
	}
}
