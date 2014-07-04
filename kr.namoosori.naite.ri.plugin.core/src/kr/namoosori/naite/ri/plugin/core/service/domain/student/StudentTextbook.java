package kr.namoosori.naite.ri.plugin.core.service.domain.student;

public class StudentTextbook {
	//
	private String id;
	private String name;
	private String lectureId;
	private String studentId;
	private String textbookId;
	private String standardTextbookId;
	
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
	public String getTextbookId() {
		return textbookId;
	}
	public void setTextbookId(String textbookId) {
		this.textbookId = textbookId;
	}
	public String getStandardTextbookId() {
		return standardTextbookId;
	}
	public void setStandardTextbookId(String standardTextbookId) {
		this.standardTextbookId = standardTextbookId;
	}
}
