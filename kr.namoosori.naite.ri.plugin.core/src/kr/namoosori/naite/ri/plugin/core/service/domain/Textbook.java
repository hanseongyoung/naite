package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.util.ArrayList;
import java.util.List;

public class Textbook {
	//
	private String id;
	private String name;
	
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
	
	public static List<Textbook> createDomains(String str) {
		//
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
}
