package kr.namoosori.naite.ri.plugin.core.service;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;

public interface NaiteService {
	//
	public Lecture getCurrentLecture() throws NaiteException;
	public void createLecture(String name) throws NaiteException;
	
	//--------------------------------------------------------------------------
	// file service
	public void downloadTextbook(String downloadLocation, Textbook textbook) throws NaiteException;

	public void createTextbook(String textbookFileName, String lectureId) throws NaiteException;

	//--------------------------------------------------------------------------
	// project service
	public void projectCreate(ExerciseProject exerciseProject) throws NaiteException;
	
}
