package kr.namoosori.naite.ri.plugin.student.login;

public class LoginManager {
	//
	private static LoginManager instance = new LoginManager();
	
	public static LoginManager getInstance() {
		//
		return instance;
	}
	
	private LoginManager() {
	}
	
	public boolean isLogin() {
		return true;
	}
}
