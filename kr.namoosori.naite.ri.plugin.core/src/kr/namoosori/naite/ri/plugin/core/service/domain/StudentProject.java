package kr.namoosori.naite.ri.plugin.core.service.domain;

/**
 * 학생 실습 프로젝트
 * @author syhan
 */
public class StudentProject extends ExerciseProject {
	//
	private String studentId;
	private String exerciseProjectId;
	
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

}
