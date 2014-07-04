package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.project.NaiteProjectObject;

public class ExerciseProject implements NaiteProjectObject {
	//
	private String id;
	private String fileName;
	private String projectName;
	
	private Lecture lecture;
	
	public ExerciseProject(){}
	
	public ExerciseProject(String projectName) {
		//
		this.projectName = projectName;
	}
	
	public String getLectureId() {
		//
		if (lecture == null) return null;
		return lecture.getId();
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
	public Lecture getLecture() {
		return lecture;
	}
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	public static List<ExerciseProject> createDomains(String str) {
		//
		if (str == null || str.length() <= 0) {
			return null;
		}
		
		List<ExerciseProject> projects = new ArrayList<ExerciseProject>();
		String[] strArr = str.split("\n");
		for (String line : strArr) {
			if (line != null && line.length() > 0) {
				projects.add(createDomain(line));
			}
		}
		return projects;
	}
	private static ExerciseProject createDomain(String line) {
		//
		String[] strArr = line.split(";");
		if (strArr.length < 3) {
			return null;
		}
		
		ExerciseProject project = new ExerciseProject();
		project.setId(strArr[0].trim());
		project.setFileName(strArr[1].trim());
		project.setProjectName(strArr[2].trim());
		return project;
	}
	
	public static String createId(List<ExerciseProject> exists) {
		//
		if (exists == null || exists.size() <= 0) {
			return "1";
		}
		
		int id = 0;
		for (ExerciseProject ele : exists) {
			int eleId = Integer.parseInt(ele.getId());
			if (eleId > id) {
				id = eleId;
			}
		}
		id++;
		return String.valueOf(id);
	}

	@Override
	public String getServerPath() {
		//
		return "lectures/" + lecture.getId() + "/exerciseprojects";
	}

	/*
	public StudentProject newStudentProject(Student student) {
		//
		StudentProject project = new StudentProject();
		project.setFileName(this.fileName);
		project.setLecture(this.lecture);
		project.setLectureId(this.lecture.getId());
		project.setProjectName(this.projectName + "-" + student.getNickname());
		project.setStudentId(student.getId());
		project.setExerciseProjectId(this.id);
		return project;
	}
	*/
	
	
}
