package kr.namoosori.naite.ri.plugin.student.login;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.student.StudentContext;
import kr.namoosori.naite.ri.plugin.student.util.DialogSettingsUtils;

public class LoginManager {
	//
	private static LoginManager instance = new LoginManager();

	private boolean logined;
	private List<LoginListener> listeners = new ArrayList<LoginListener>();
	private StudentContext context = StudentContext.getInstance();

	public static LoginManager getInstance() {
		//
		return instance;
	}

	private LoginManager() {
	}

	public boolean check() {
		//
		if (!logined) {
			if (context.isServerOn()) {
				checkLogin();
				invokeListeners();
			}
		}
		return logined;
	}
	
	private void checkLogin() {
		//
		String studentEmail = DialogSettingsUtils.get("student", "email");
		String studentPass = DialogSettingsUtils.get("student", "pass");
		NaiteService service = NaiteServiceFactory.getInstance()
				.getNaiteService();
		try {
			Student student = service.getCurrentStudent(studentEmail);
			if (student != null) {
				if (student.getPassword().equals(studentPass)) {
					logined = true;
				}
			}
		} catch (NaiteException e) {
			e.printStackTrace();
		}
	}
	
	public void addLoginListener(LoginListener listener) {
		//
		if (listener == null) {
			return;
		}
		this.listeners.add(listener);
	}
	
	public void removeLoginListener(LoginListener listener) {
		//
		if (listener == null) {
			return;
		}
		this.listeners.remove(listener);
	}
	
	private void invokeListeners() {
		//
		if (this.listeners.size() <= 0) {
			return;
		}
		for (LoginListener listener : listeners) {
			if (logined) {
				listener.logined();
			} else {
				listener.logoffed();
			}
		}
	}

	public boolean isLogined() {
		return logined;
	}
	
}
