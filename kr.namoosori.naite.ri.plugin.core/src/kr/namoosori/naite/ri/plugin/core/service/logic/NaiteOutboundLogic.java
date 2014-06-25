package kr.namoosori.naite.ri.plugin.core.service.logic;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import kr.namoosori.naite.ri.plugin.core.contents.NaiteContents;
import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.NaiteUser;
import kr.namoosori.naite.ri.plugin.core.service.domain.StandardProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.StandardTextbook;
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
	public Lecture getLecture(String lectureId) throws NaiteException {
		//
		String json = naiteContents.getContentsJson("lectures/"+lectureId);
		Lecture lecture = Lecture.createDomainByJson(json);
		return lecture;
	}
	
	@Override
	public List<Lecture> getTeacherLectures(String teacherEmail)
			throws NaiteException {
		//
		String json = naiteContents.getContentsJson("teacherlectures/" + teacherEmail);
		List<Lecture> lectures = Lecture.createDomainsByJson(json);
		return lectures;
	}
	
	@Override
	public Lecture getCurrentLectureOfStudent(String studentEmail) throws NaiteException {
		//
		String currentJson = naiteContents.getContentsJson("currentlecture/students/"+studentEmail);
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
	public Student getCurrentStudent(String lectureId, String studentEmail) throws NaiteException {
		String jsonStr = naiteContents.getContentsJson("lectures/"+lectureId + "/students/"+studentEmail);
		return Student.createDomainByJson(jsonStr);
	}

	@Override
	public List<Student> findStudents(String lectureId) throws NaiteException {
		//
		String str = naiteContents.getContentsJson("lectures/" + lectureId + "/students");
		List<Student> students = Student.createDomainsByJson(str);
		return students;
	}

	@Override
	public void createStudent(String lectureId, Student student)
			throws NaiteException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", student.getName());
		params.put("email", student.getEmail());
		params.put("password", student.getPassword());
		params.put("lectureId", lectureId);
		naiteContents.doPost("lectures/" + lectureId + "/students", params);
	}

	@Override
	public void deleteTextbook(Textbook textbook) throws NaiteException {
		String lectureId = textbook.getLectureId();
		String textbookId = textbook.getId();
		naiteContents.doPost("lectures/" + lectureId + "/textbooks/" + textbookId + "/delete", new HashMap<String, String>());
	}

	@Override
	public void deleteExerciseProject(ExerciseProject project)
			throws NaiteException {
		String lectureId = project.getLectureId();
		String projectId = project.getId();
		naiteContents.doPost("lectures/" + lectureId + "/exerciseprojects/" + projectId + "/delete", new HashMap<String, String>());
	}

	@Override
	public void downloadTextbook(String downloadLocation, Textbook textbook)
			throws NaiteException {
		String lectureId = textbook.getLecture().getId();
		String serverFileName = textbook.getName();
		String standardTextbookId = textbook.getStandardTextbookId();
		if (standardTextbookId != null && standardTextbookId.length() > 0) {
			naiteContents.download(downloadLocation, "standardtextbooks/file/", serverFileName);
		} else {
			naiteContents.download(downloadLocation, "lectures/" + lectureId + "/textbooks/file/", serverFileName);
		}
	}

	@Override
	public void createTextbook(String textbookFilePathName, String lectureId)
			throws NaiteException {
		String textbookFileName = StringUtils.substringAfterLast(textbookFilePathName, "\\");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", textbookFileName);
		params.put("lectureId", lectureId);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file", new File(textbookFilePathName));
		
		naiteContents.doMultipartPost("lectures/"+lectureId + "/textbooks", params, fileParams);
	}
	
	@Override
	public void createTextbookByStandard(String standardTextbookId, String lectureId)
			throws NaiteException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("standardTextbookId", standardTextbookId);
		params.put("lectureId", lectureId);
		
		naiteContents.doPost("lectures/"+lectureId + "/textbooks", params);
	}
	
	@Override
	public void createStandardTextbook(String textbookFilePathName)
			throws NaiteException {
		String textbookFileName = StringUtils.substringAfterLast(textbookFilePathName, "\\");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", textbookFileName);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file", new File(textbookFilePathName));
		
		naiteContents.doMultipartPost("standardtextbooks", params, fileParams);
	}
	
	@Override
	public void createStandardProject(String projectFilePathName,
			String projectName) throws NaiteException {
		String fileName = StringUtils.substringAfterLast(projectFilePathName, "\\");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("fileName", fileName);
		params.put("projectName", projectName);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file", new File(projectFilePathName));
		
		naiteContents.doMultipartPost("standardprojects", params, fileParams);
	}

	@Override
	public void createExerciseProject(String projectFilePathName,
			String projectName, String lectureId) throws NaiteException {
		String fileName = StringUtils.substringAfterLast(projectFilePathName, "\\");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("fileName", fileName);
		params.put("projectName", projectName);
		params.put("lectureId", lectureId);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file", new File(projectFilePathName));
		
		naiteContents.doMultipartPost("lectures/" + lectureId + "/exerciseprojects", params, fileParams);
	}
	
	@Override
	public void createStudentProject(String projectFilePathName,
			String projectName, String lectureId, String studentId, 
			String exerciseProjectId) throws NaiteException {
		String fileName = StringUtils.substringAfterLast(projectFilePathName, "\\");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("fileName", fileName);
		params.put("projectName", projectName);
		params.put("lectureId", lectureId);
		params.put("studentId", studentId);
		params.put("exerciseProjectId", exerciseProjectId);
		
		Map<String, File> fileParams = new HashMap<String, File>();
		fileParams.put("file", new File(projectFilePathName));
		
		naiteContents.doMultipartPost("lectures/" + lectureId + "/students/" +studentId + "/projects", params, fileParams);
	}
	
	@Override
	public void createExerciseProject(String standardProjectId, String lectureId)
			throws NaiteException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("standardProjectId", standardProjectId);
		params.put("lectureId", lectureId);
		naiteContents.doPost("lectures/" + lectureId + "/exerciseprojects", params);
	}

	@Override
	public List<StandardProject> findAllStandardProjects()
			throws NaiteException {
		String str = naiteContents.getContentsJson("standardprojects");
		List<StandardProject> projects = StandardProject.createDomainsByJson(str);
		return projects;
	}

	@Override
	public List<StandardTextbook> findAllStandardTextbooks()
			throws NaiteException {
		String str = naiteContents.getContentsJson("standardtextbooks");
		List<StandardTextbook> textbooks = StandardTextbook.createDomainsByJson(str);
		return textbooks;
	}

	@Override
	public List<NaiteUser> findAllNaiteUsers() throws NaiteException {
		String str = naiteContents.getContentsJson("naiteusers");
		List<NaiteUser> users = NaiteUser.createDomainsByJson(str);
		return users;
	}

	@Override
	public void createNaiteUser(String name, String email, String password)
			throws NaiteException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("email", email);
		params.put("password", password);
		naiteContents.doPost("naiteusers", params);
	}

	

}
