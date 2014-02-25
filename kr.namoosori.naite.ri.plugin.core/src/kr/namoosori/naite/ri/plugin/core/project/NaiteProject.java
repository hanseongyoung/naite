package kr.namoosori.naite.ri.plugin.core.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import kr.namoosori.naite.ri.plugin.core.contents.NaiteContents;
import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.util.ZipUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class NaiteProject {
	//
	private NaiteContents contents = new NaiteContents();
	
	public void create(String serverPath, String zipFileName, String projectName) throws NaiteException {
		//
		createProjectContents(serverPath, zipFileName);
		createProject(projectName);
	}

	private void createProject(String projectName) throws NaiteException {
		//
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists()) {
			//
		} else {
			try {
				project.create(new NullProgressMonitor());
			} catch (CoreException e) {
				throw new NaiteException("프로젝트 생성중 문제발생", e);
			}
		}
		
		try {
			project.open(new NullProgressMonitor());
		} catch (CoreException e) {
			throw new NaiteException("프로젝트 오픈중 문제발생", e);
		}
	}

	private void createProjectContents(String serverPath, String zipFileName) throws NaiteException {
		//
		InputStream inputStream = contents.getInputStream(serverPath, zipFileName);
		File targetDir = NaiteWorkspace.getRootLocationAsFile();
		try {
			ZipUtils.unzip(inputStream, targetDir, false);
		} catch (IOException e) {
			throw new NaiteException("Unzip 도중 문제발생", e);
		}
	}
}
