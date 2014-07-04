package kr.namoosori.naite.ri.plugin.core.service.domain.student;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.namoosori.naite.ri.plugin.core.service.domain.AbstractLecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public class StudentLecture extends AbstractLecture {
	//
	private String id;
	private String name;
	private String teacherEmail;
	private List<StudentTextbook> textbooks = new ArrayList<StudentTextbook>();
	private List<StudentProject> projects = new ArrayList<StudentProject>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacherEmail() {
		return teacherEmail;
	}
	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
	public List<StudentTextbook> getTextbooks() {
		return textbooks;
	}
	public void setTextbooks(List<StudentTextbook> textbooks) {
		this.textbooks = textbooks;
	}
	public List<StudentProject> getProjects() {
		return projects;
	}
	public void setProjects(List<StudentProject> projects) {
		this.projects = projects;
	}
	
	public static StudentLecture createDomainByJson(String json) {
		//
		Gson gson = new Gson();
		StudentLecture lecture = gson.fromJson(json, StudentLecture.class);
		if (lecture == null) {
			return null;
		}
		
//		List<Textbook> textbooks = lecture.getTextbooks();
//		if (textbooks != null && textbooks.size() > 0) {
//			for (Textbook textbook : textbooks) {
//				textbook.setLecture(lecture);
//			}
//		}
//		
//		List<ExerciseProject> projects = lecture.getExerciseProjects();
//		if (projects != null && projects.size() > 0) {
//			for (ExerciseProject exerciseProject : projects) {
//				exerciseProject.setLecture(lecture);
//			}
//		}
		return lecture;
	}
	
	public static List<StudentLecture> createDomainsByJson(String jsonStr) {
		//
		Type listType = new TypeToken<ArrayList<StudentLecture>>() {
		}.getType();
		List<StudentLecture> lectures = new Gson().fromJson(jsonStr, listType);
		return lectures;
	}
	
}
