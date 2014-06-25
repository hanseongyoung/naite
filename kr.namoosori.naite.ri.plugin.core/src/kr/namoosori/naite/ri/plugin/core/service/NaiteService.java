package kr.namoosori.naite.ri.plugin.core.service;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.NaiteUser;
import kr.namoosori.naite.ri.plugin.core.service.domain.StandardProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.StandardTextbook;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public interface NaiteService {
	//
	public void createNaiteUser(String name, String email, String password) throws NaiteException;
	
	public Lecture getCurrentLecture(String teacherEmail) throws NaiteException;
	public Lecture getCurrentLectureOfStudent(String studentEmail) throws NaiteException;
	public Lecture getLecture(String lectureId) throws NaiteException;
	
	public List<Lecture> getTeacherLectures(String teacherEmail) throws NaiteException;
	public void createLecture(String name, String teacherEmail) throws NaiteException;
	
	public Student getCurrentStudent(String lectureId, String studentEmail) throws NaiteException;
	public List<Student> findStudents(String lectureId) throws NaiteException;
	public void createStudent(String lectureId, Student student) throws NaiteException;
	
	public void deleteTextbook(Textbook textbook) throws NaiteException;
	public void deleteExerciseProject(ExerciseProject project) throws NaiteException;
	
	public List<StandardProject> findAllStandardProjects() throws NaiteException;
	public List<StandardTextbook> findAllStandardTextbooks() throws NaiteException;
	public List<NaiteUser> findAllNaiteUsers() throws NaiteException;

	// -------------------------------------------------------------------------
	// file service
	public void downloadTextbook(String downloadLocation, Textbook textbook) throws NaiteException;
	public void createTextbook(String textbookFilePathName, String lectureId) throws NaiteException;
	public void createTextbookByStandard(String standardTextbookId, String lectureId) throws NaiteException;
	public void createExerciseProject(String projectFilePathName, String projectName, String lectureId) throws NaiteException;
	public void createStandardProject(String projectFilePathName, String projectName) throws NaiteException;
	public void createStandardTextbook(String textbookFilePathName) throws NaiteException;
	public void createStudentProject(String projectFilePathName, String projectName, String lectureId, String studentId, String exerciseProjectId) throws NaiteException;

	// -------------------------------------------------------------------------
	/**
	 * 표준 프로젝트를 강의 프로젝트로 복사한다.
	 * @param standardProjectId 표준 프로젝트 아이디
	 * @param lectureId 강의 아이디
	 * @throws NaiteException
	 */
	public void createExerciseProject(String standardProjectId, String lectureId) throws NaiteException;
}
