package kr.namoosori.naite.ri.plugin.student.login;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.SecuredChecker;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;
import kr.namoosori.naite.ri.plugin.student.util.DialogSettingsUtils;

public class LoginManager implements Runnable, SecuredChecker {
	//
	private static final long INTERVAL = 1000 * 10;

	private static LoginManager instance = new LoginManager();

	private boolean logined;

	private boolean continueObserve;
	
	private List<LoginListener> listeners = new ArrayList<LoginListener>();

	public static LoginManager getInstance() {
		//
		return instance;
	}

	private LoginManager() {
	}

	public void startObserve() {
		//
		this.continueObserve = true;
		Thread thread = new Thread(this, "LoginObserver");
		thread.start();
		System.out.println("LoginObserver started...");
	}

	public void stopObserve() {
		//
		this.continueObserve = false;
	}

	public boolean isLogined() {
		//
		if (!logined) {
			checkLogin();
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

	@Override
	public void run() {
		//
		while (continueObserve) {
			if (logined) {
				stopObserve();
				break;
			}

			Student localInfo = getLocalStudent();

			boolean successSend = sendToTeacher(localInfo);
			if (successSend) {
				stopObserve();
				break;
			}

			sleepForWhile();
		}
		System.out.println("LoginObserver stoped...");

	}

	private boolean sendToTeacher(Student localInfo) {
		//
		MessageSender sender = NaiteNetClient.getInstance().getMessageSender();
		String result = sender.send("teacher", localInfo.getName() + ","
				+ localInfo.getEmail() + "," + localInfo.getPassword());
		if (result != null && result.equals("ok")) {
			return true;
		}
		return false;
	}

	private Student getLocalStudent() {
		//
		String name = DialogSettingsUtils.get("student", "name");
		if (name == null || name.length() <= 0) {
			return null;
		}
		String email = DialogSettingsUtils.get("student", "email");
		if (email == null || email.length() <= 0) {
			return null;
		}
		String pass = DialogSettingsUtils.get("student", "pass");
		if (pass == null || pass.length() <= 0) {
			return null;
		}

		Student student = new Student();
		student.setName(name);
		student.setEmail(email);
		student.setPassword(pass);
		return student;
	}

	private void sleepForWhile() {
		//
		try {
			Thread.sleep((long) (Math.random() * INTERVAL));
		} catch (InterruptedException e) {
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
	
	public boolean check() {
		//
		return isLogined();
	}
	
	private void invokeListeners() {
		//
		if (this.listeners.size() <= 0) {
			return;
		}
		for (LoginListener listener : listeners) {
			listener.loginChecked(logined);
		}
	}

	@Override
	public void notPermitted() {
		//
		invokeListeners();
	}
}
