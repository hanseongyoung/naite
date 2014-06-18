package kr.namoosori.naite.ri.plugin.core.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import kr.namoosori.naite.ri.plugin.core.contents.NaiteContents;
import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.util.ZipUtils;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class NaiteProject {
	//
	private NaiteContents contents = new NaiteContents();
	
	private IProject project;
	private ExerciseProject exerciseProject;
	
	public NaiteProject(){}
	
	public NaiteProject(ExerciseProject exerciseProject) {
		//
		this.exerciseProject = exerciseProject;
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(exerciseProject.getProjectName());
	}
	
	public NaiteProject(IProject project, Lecture lecture) {
		//
		this.project = project;
		this.exerciseProject = new ExerciseProject(project.getName());
		this.exerciseProject.setLecture(lecture);
	}
	
	public String getName() {
		//
		if (project == null) {
			return null;
		}
		return project.getName();
	}
	
	public IProject getResource() {
		//
		return project;
	}
	
	public void create() throws NaiteException {
		//
		String serverPath = "lectures/" + exerciseProject.getLectureId() + "/exerciseprojects/";
		createProjectContents(serverPath);
		createProject();
	}
	
	public void export() throws NaiteException {
		//
		//String serverPath = "lectures/" + exerciseProject.getLectureId() + "/projects/";
		String packedFilePathName = packProjectContents();
		
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createExerciseProject(packedFilePathName, project.getName(), exerciseProject.getLectureId());
	}

	private void createProject() throws NaiteException {
		//
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

	private void createProjectContents(String serverPath) throws NaiteException {
		//
		String zipFileName = exerciseProject.getFileName();
		InputStream inputStream = contents.getInputStream(serverPath, zipFileName);
		File workspaceDir = NaiteWorkspace.getInstance().getRootLocationAsFile();
		File targetDir = new File(workspaceDir, project.getName());
		//File targetDir = project.getLocation().toFile();
		System.out.println("targetDir:"+targetDir);
		if (targetDir.exists()) {
			throw new NaiteException("프로젝트가 이미 존재합니다.");
		}
		
		try {
			FileUtils.forceMkdir(targetDir);
		} catch (IOException e1) {
			throw new NaiteException("프로젝트 생성 도중 문제발생", e1);
		}
		
		try {
			ZipUtils.unzip(inputStream, targetDir, false);
		} catch (IOException e) {
			throw new NaiteException("Unzip 도중 문제발생", e);
		}
		
		// targetDir 아래에 한 단계 디렉토리가 더 있는 경우 상위로 올린다.
//		File[] subfiles = targetDir.listFiles();
//		if (subfiles.length == 1 && subfiles[0].isDirectory()) {
//			System.out.println("move dir...");
//			boolean success = subfiles[0].renameTo(new File(targetDir.getAbsolutePath()));
//			System.out.println("success:"+success);
//		}
	}
	
	private String packProjectContents() throws NaiteException {
		String projectPath = project.getLocation().toString();
		System.out.println(projectPath);
		String fileName = project.getName() + ".zip";
		exerciseProject.setFileName(fileName);
		System.out.println(fileName);
		String tempZipFilePathName = FileUtils.getTempDirectoryPath() + fileName;
		System.out.println(tempZipFilePathName);
		try {
			ZipUtils.zip(projectPath, tempZipFilePathName);
		} catch (IOException e) {
			throw new NaiteException("Zip 도중 문제발생", e);
		}
		
		File tempZipFile = new File(tempZipFilePathName);
		tempZipFile.deleteOnExit();
		
		return tempZipFilePathName;
	}

	public ExerciseProject getExerciseProject() {
		return exerciseProject;
	}
	
	
}
