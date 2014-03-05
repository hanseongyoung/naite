package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.util.ArrayList;
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
		id++;
		return String.valueOf(id);
	}
	
	public static List<Student> createDomains(String str) {
		if (str == null || str.length() <= 0) {
			return null;
		}
		List<Student> students = new ArrayList<Student>();
		String[] strArr = str.split("\n");
		for (String line : strArr) {
			if (line != null && line.length() > 0) {
				students.add(createDomain(line));
			}
		}
		return students;
	}
	
	private static Student createDomain(String line) {
		//
		String[] strArr = line.split(";");
		if (strArr.length < 4) {
			return null;
		}
		
		Student student = new Student();
		student.setId(strArr[0].trim());
		student.setEmail(strArr[1].trim());
		student.setName(strArr[2].trim());
		student.setPassword(strArr[3].trim());
		return student;
	}
	
}
