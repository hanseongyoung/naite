package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.util.ArrayList;
import java.util.List;

public class Textbook {
	//
	private String id;
	private String name;
	
	private String standardTextbookId;
	
	private Lecture lecture;
	
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
	public Lecture getLecture() {
		return lecture;
	}
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	
	public String getStandardTextbookId() {
		return standardTextbookId;
	}
	public void setStandardTextbookId(String standardTextbookId) {
		this.standardTextbookId = standardTextbookId;
	}

	public String getLectureId() {
		//
		if (lecture == null) return null;
		return lecture.getId();
	}
	
	public static List<Textbook> createDomains(String str) {
		//
		if (str == null || str.length() <= 0) {
			return null;
		}
		List<Textbook> textbooks = new ArrayList<Textbook>();
		String[] strArr = str.split("\n");
		for (String line : strArr) {
			if (line != null && line.length() > 0) {
				textbooks.add(createDomain(line));
			}
		}
		return textbooks;
	}
	
	private static Textbook createDomain(String line) {
		//
		String[] strArr = line.split(";");
		if (strArr.length < 2) {
			return null;
		}
		
		Textbook textbook = new Textbook();
		textbook.setId(strArr[0].trim());
		textbook.setName(strArr[1].trim());
		return textbook;
	}
	
	public static String createId(List<Textbook> exists) {
		//
		if (exists == null || exists.size() <= 0) {
			return "1";
		}
		
		int id = 0;
		for (Textbook ele : exists) {
			int eleId = Integer.parseInt(ele.getId());
			if (eleId > id) {
				id = eleId;
			}
		}
		id++;
		return String.valueOf(id);
	}
	
}
