package kr.namoosori.naite.ri.plugin.core.service.domain.student;

import kr.namoosori.naite.ri.plugin.core.project.NaiteProjectObject;


/**
 * 학생 실습 프로젝트
 * @author syhan
 */
public class StudentProject implements NaiteProjectObject {
	//
	private String id;
	private String fileName;
	private String projectName;
	private String lectureId;
	private String studentId;
	private String exerciseProjectId;
	
	public String getLectureId() {
		return lectureId;
	}
	public void setLectureId(String lectureId) {
		this.lectureId = lectureId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getExerciseProjectId() {
		return exerciseProjectId;
	}
	public void setExerciseProjectId(String exerciseProjectId) {
		this.exerciseProjectId = exerciseProjectId;
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
	
	@Override
	public String getServerPath() {
		//
		return "lectures/" + lectureId + "/students/" + studentId + "/projects";
	}
	
}
