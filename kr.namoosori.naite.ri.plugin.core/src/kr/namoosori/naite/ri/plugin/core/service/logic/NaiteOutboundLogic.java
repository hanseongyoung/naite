package kr.namoosori.naite.ri.plugin.core.service.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.namoosori.naite.ri.plugin.core.contents.NaiteContents;
import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public class NaiteOutboundLogic implements NaiteService {
	//
	private NaiteContents naiteContents = new NaiteContents();

	@Override
	public Lecture getCurrentLecture(String teacherEmail) throws NaiteException {
		//
		String currentJson = naiteContents.getContentsJson("currentlecture/"+teacherEmail);
		Lecture current = Lecture.createDomainByJson(currentJson);
		if (current == null) {
			return null;
		}
		
		String json = naiteContents.getContentsJson("lectures/"+current.getId());
		Lecture lecture = Lecture.createDomainByJson(json);
		return lecture;
	}

	@Override
	public void createLecture(String name, String teacherEmail) throws NaiteException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("current", "true");
		params.put("teacherEmail", teacherEmail);
		naiteContents.doPost("lectures", params);
	}

	@Override
	public Student getCurrentStudent(String studentEmail) throws NaiteException {
		// TODO Auto-generated method stub
		System.out.println("[Outbound] getCurrentStudent");
		return null;
	}

	@Override
	public List<Student> findStudents(String lectureId) throws NaiteException {
		System.out.println("[Outbound] findStudents");
		String str = naiteContents.getContentsJson("students");
		List<Student> students = Student.createDomainsByJson(str);
		return students;
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
