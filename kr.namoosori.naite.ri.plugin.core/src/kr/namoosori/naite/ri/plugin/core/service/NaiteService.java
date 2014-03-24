package kr.namoosori.naite.ri.plugin.core.service;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public interface NaiteService {
	//
	public Lecture getCurrentLecture() throws NaiteException;
	public void createLecture(String name) throws NaiteException;
	public Student getCurrentStudent(String studentEmail) throws NaiteException;
	public List<Student> findStudents(String lectureId) throws NaiteException;
	public void createStudent(String lectureId, Student student) throws NaiteException;
	
	public void deleteTextbook(Textbook textbook) throws NaiteException;
	public void deleteExerciseProject(ExerciseProject project) throws NaiteException;

	// -------------------------------------------------------------------------
	// file service
	public void downloadTextbook(String downloadLocation, Textbook textbook)
			throws NaiteException;

	public void createTextbook(String textbookFilePathName, String lectureId)
			throws NaiteException;

	public void createExerciseProject(String projectFilePathName,
			String projectName, String lectureId) throws NaiteException;

	// -------------------------------------------------------------------------

}
