package kr.namoosori.naite.ri.plugin.core.service;

import kr.namoosori.naite.ri.plugin.core.service.logic.NaiteOutboundLogic;

public class NaiteServiceFactory {
	//
	private static NaiteServiceFactory instance = new NaiteServiceFactory();
	
	public static NaiteServiceFactory getInstance() {
		//
		return instance;
	}
	
	public NaiteService getNaiteService() {
		//return new NaiteInboundLogic();
		return new NaiteOutboundLogic();
	}
}
