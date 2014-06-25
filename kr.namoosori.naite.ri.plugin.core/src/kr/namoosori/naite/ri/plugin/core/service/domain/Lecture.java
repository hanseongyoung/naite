package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Lecture {
	//
	private String id;
	private String name;
	private boolean current;
	private List<Textbook> textbooks = new ArrayList<Textbook>();
	private List<ExerciseProject> exerciseProjects = new ArrayList<ExerciseProject>();
	private List<Student> students = new ArrayList<Student>();
	
	public void addTextbook(Textbook textbook) {
		//
		textbook.setLecture(this);
		this.textbooks.add(textbook);
	}
	
	public void addExerciseProject(ExerciseProject exerciseProject) {
		//
		exerciseProject.setLecture(this);
		this.exerciseProjects.add(exerciseProject);
	}
	
	public void addStudent(Student student) {
		//
		student.setLecture(this);
		this.students.add(student);
	}
	
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
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public List<Textbook> getTextbooks() {
		return textbooks;
	}
	public void setTextbooks(List<Textbook> textbooks) {
		this.textbooks = textbooks;
	}
	public List<ExerciseProject> getExerciseProjects() {
		return exerciseProjects;
	}
	public void setExerciseProjects(List<ExerciseProject> exerciseProjects) {
		this.exerciseProjects = exerciseProjects;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public static List<Lecture> createDomains(String str) {
		//
		if (str == null || str.length() <= 0) {
			return null;
		}
		List<Lecture> lectures = new ArrayList<Lecture>();
		String[] strArr = str.split("\n");
		for (String line : strArr) {
			if (line != null && line.length() > 0) {
				lectures.add(createDomain(line));
			}
		}
		return lectures;
	}
	
	public static Lecture createDomainByJson(String json) {
		//
		Gson gson = new Gson();
		Lecture lecture = gson.fromJson(json, Lecture.class);
		if (lecture == null) {
			return null;
		}
		
		List<Textbook> textbooks = lecture.getTextbooks();
		if (textbooks != null && textbooks.size() > 0) {
			for (Textbook textbook : textbooks) {
				textbook.setLecture(lecture);
			}
		}
		
		List<ExerciseProject> projects = lecture.getExerciseProjects();
		if (projects != null && projects.size() > 0) {
			for (ExerciseProject exerciseProject : projects) {
				exerciseProject.setLecture(lecture);
			}
		}
		return lecture;
	}
	
	public static List<Lecture> createDomainsByJson(String jsonStr) {
		//
		Type listType = new TypeToken<ArrayList<Lecture>>() {
		}.getType();
		List<Lecture> lectures = new Gson().fromJson(jsonStr, listType);
		return lectures;
	}
	
	private static Lecture createDomain(String line) {
		//
		String[] strArr = line.split(";");
		if (strArr.length < 3) {
			return null;
		}
		
		Lecture lecture = new Lecture();
		lecture.setId(strArr[0].trim());
		lecture.setName(strArr[1].trim());
		lecture.setCurrent(Boolean.valueOf(strArr[2].trim()));
		return lecture;
	}

	public static String createId(List<Lecture> exists) {
		//
		if (exists == null || exists.size() <= 0) {
			return "1";
		}
		
		int id = 0;
		for (Lecture ele : exists) {
			int eleId = Integer.parseInt(ele.getId());
			if (eleId > id) {
				id = eleId;
			}
		}
		id++;
		return String.valueOf(id);
	}
	
	
	
}
