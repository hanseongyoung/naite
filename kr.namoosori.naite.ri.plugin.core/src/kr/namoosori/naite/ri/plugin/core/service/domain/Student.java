package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.util.List;

public class Student {
	//
	private String id;
	private String email;
	private String name;
	private String password;
	private List<ExerciseProject> exerciseProjects;
	
	private Lecture lecture;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<ExerciseProject> getExerciseProjects() {
		return exerciseProjects;
	}
	public void setExerciseProjects(List<ExerciseProject> exerciseProjects) {
		this.exerciseProjects = exerciseProjects;
	}
	public Lecture getLecture() {
		return lecture;
	}
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	
	public static String createId(List<Student> exists) {
		//
		if (exists == null || exists.size() <= 0) {
			return "1";
		}
		
		int id = 0;
		for (Student ele : exists) {
			int eleId = Integer.parseInt(ele.getId());
			if (eleId > id) {
				id = eleId;
			}
		}
		return String.valueOf(id++);
	}
	
}
