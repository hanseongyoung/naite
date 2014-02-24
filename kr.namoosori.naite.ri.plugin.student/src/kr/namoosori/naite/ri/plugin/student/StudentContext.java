package kr.namoosori.naite.ri.plugin.student;

import kr.namoosori.naite.ri.plugin.core.CoreContext;

public class StudentContext extends CoreContext {
	//
	public static void init() {
		//
		instance = new StudentContext();
	}
	@Override
	public String getServerUrl() {
		return null;
	}

	@Override
	public boolean hasServerUrl() {
		return false;
	}


}
