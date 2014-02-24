package kr.namoosori.naite.ri.plugin.core.service;

import kr.namoosori.naite.ri.plugin.core.service.logic.NaiteInboundLogic;

public class NaiteServiceFactory {
	//
	private static NaiteServiceFactory instance = new NaiteServiceFactory();
	
	public static NaiteServiceFactory getInstance() {
		//
		return instance;
	}
	
	public NaiteService getNaiteService() {
		return new NaiteInboundLogic();
	}
}
