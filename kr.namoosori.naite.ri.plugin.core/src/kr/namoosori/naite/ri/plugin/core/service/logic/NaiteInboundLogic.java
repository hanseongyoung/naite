package kr.namoosori.naite.ri.plugin.core.service.logic;

import java.io.File;
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

import org.apache.commons.lang.StringUtils;

public class NaiteInboundLogic implements NaiteService {
	//
	private NaiteContents naiteContents = new NaiteContents();
	
	@Override
	public Lecture getCurrentLecture() throws NaiteException {
		//
		List<Lecture> lectures = findLectures();
		if (lectures == null) {
			return null;
		}
		
		Lecture current = null;
		for (Lecture lecture : lectures) {
			if (lecture.isCurrent()) {
				current = lecture;
				break;
			}
		}
		if (current == null) {
			return null;
		}
		
		List<Textbook> books = findTextbooks(current.getId());
		if (books != null) {
			for (Textbook textbook : books) {
				current.addTextbook(textbook);
			}
		}
		
		List<ExerciseProject> projects = findExerciseProjects(current.getId());
		if (projects != null) {
			for (ExerciseProject exerciseProject : projects) {
				current.addExerciseProject(exerciseProject);
			}
		}
		
		return current;
	}
	
	@Override
	public Student getCurrentStudent(String studentEmail) throws NaiteException {
		//
		List<Lecture> lectures = findLectures();
		if (lectures == null) {
			return null;
		}
		
		Lecture current = null;
		for (Lecture lecture : lectures) {
			if (lecture.isCurrent()) {
				current = lecture;
				break;
			}
		}
		if (current == null) {
			return null;
		}
		
		List<Student> students = findStudents(current.getId());
		Student finded = null;
		for (Student student : students) {
			if (student.getEmail().equals(studentEmail)) {
				finded = student;
			}
		}
		return finded;
	}

	
	private List<Student> findStudents(String lectureId) {
		try {
			String str = naiteContents.getContentsString("lectures/"+lectureId+"/students.txt");
			List<Student> students = Student.createDomains(str);
			return students;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createLecture(String name) throws NaiteException {
		//
		List<Lecture> exists = findLectures();
		
		String id = Lecture.createId(exists);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("name", name);
		params.put("current", "true");
		naiteContents.doPost("lectures/create", params);
	}
	
	
	private List<Lecture> findLectures() throws NaiteException {
		//
		try {
			String str = naiteContents.getContentsString("lectures.txt");
			List<Lecture> lectures = Lecture.createDomains(str);
			return lectures;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Textbook> findTextbooks(String lectureId) throws NaiteException {
		//
		try {
			String str = naiteContents.getContentsString("lectures/"+lectureId+"/textbooks.txt");
			List<Textbook> textbooks = Textbook.createDomains(str);
			return textbooks;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<ExerciseProject> findExerciseProjects(String lectureId) throws NaiteException {
		//
		try {
			String str = naiteContents.getContentsString("lectures/"+lectureId+"/projects.txt");
			List<ExerciseProject> projects = ExerciseProject.createDomains(str);
			return projects;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}

	//--------------------------------------------------------------------------
	@Override
	public void downloadTextbook(String localDownloadPath, Textbook textbook) throws NaiteException {
		//
		String lectureId = textbook.getLecture().getId();
		String serverFileName = textbook.getName();
		naiteContents.download(localDownloadPath, "lectures/" + lectureId + "/textbooks/", serverFileName);
	}

	@Override
	public void createTextbook(String filePathName, String lectureId) throws NaiteException {
		//
		String fileName = StringUtils.substringAfterLast(filePathName, "\\");
		
		registerTextbook(lectureId, fileName);
		uploadTextbook(lectureId, fileName, filePathName);
	}

	private void registerTextbook(String lectureId, String name) throws NaiteException {
		//
		List<Textbook> exists = findTextbooks(lectureId);
		String id = Textbook.createId(exists);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("name", name);
		naiteContents.doPost("lectures/" + lectureId + "/textbooks/create", params);
	}
	
	private void uploadTextbook(String lectureId, String fileName, String filePathName) throws NaiteException {
		//
		Map<String, String> params = new HashMap<String, String>();
		params.put("fileName1", fileName);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file1", new File(filePathName));
		
		naiteContents.doMultipartPost("lectures/" + lectureId + "/textbooks/upload", params, fileParams);
	}
	
	@Override
	public void createExerciseProject(String filePathName, String projectName,
			String lectureId) throws NaiteException {
		//
		String fileName = StringUtils.substringAfterLast(filePathName, "\\");
		
		registerExerciseProject(lectureId, fileName, projectName);
		uploadExerciseProject(lectureId, fileName, filePathName);
	}
	
	private void registerExerciseProject(String lectureId, String name, String projectName) throws NaiteException {
		//
		List<ExerciseProject> exists = findExerciseProjects(lectureId);
		String id = ExerciseProject.createId(exists);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("name", name);
		params.put("projectName", projectName);
		naiteContents.doPost("lectures/" + lectureId + "/projects/create", params);
	}

	private void uploadExerciseProject(String lectureId, String fileName, String filePathName) throws NaiteException {
		//
		Map<String, String> params = new HashMap<String, String>();
		params.put("fileName1", fileName);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file1", new File(filePathName));
		
		naiteContents.doMultipartPost("lectures/" + lectureId + "/projects/upload", params, fileParams);
	}

	
	//--------------------------------------------------------------------------

}
