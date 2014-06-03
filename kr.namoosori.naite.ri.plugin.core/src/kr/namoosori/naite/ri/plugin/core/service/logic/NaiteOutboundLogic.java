package kr.namoosori.naite.ri.plugin.core.service.logic;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public class NaiteOutboundLogic implements NaiteService {

	@Override
	public Lecture getCurrentLecture() throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] getCurrentLecture");
		return null;
	}

	@Override
	public void createLecture(String name) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] createLecture");
	}

	@Override
	public Student getCurrentStudent(String studentEmail) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] getCurrentStudent");
		return null;
	}

	@Override
	public List<Student> findStudents(String lectureId) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] findStudents");
		return null;
	}

	@Override
	public void createStudent(String lectureId, Student student)
			throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] createStudent");
	}

	@Override
	public void deleteTextbook(Textbook textbook) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] deleteTextbook");
	}

	@Override
	public void deleteExerciseProject(ExerciseProject project)
			throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] deleteExerciseProject");
	}

	@Override
	public void downloadTextbook(String downloadLocation, Textbook textbook)
			throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] downloadTextbook");
	}

	@Override
	public void createTextbook(String textbookFilePathName, String lectureId)
			throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] createTextbook");
	}

	@Override
	public void createExerciseProject(String projectFilePathName,
			String projectName, String lectureId) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] createExerciseProject");
	}

}
