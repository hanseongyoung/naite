package kr.namoosori.naite.ri.plugin.student.login;

import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageSender;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.NameValue;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.SendMessage;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;
import kr.namoosori.naite.ri.plugin.student.util.DialogSettingsUtils;

public class LoginObserver implements Runnable {
	//
	private static final long INTERVAL = 1000 * 10;
	
	private static LoginObserver instance = new LoginObserver();
	
	public static LoginObserver getInstance() {
		//
		return instance;
	}
	
	private LoginManager loginManager = LoginManager.getInstance();
	private boolean continueObserve;
	private boolean alreadySendToTeacher;
	
	private LoginObserver() {
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
	
	@Override
	public void run() {
		//
		while (continueObserve) {
			if (loginManager.check()) {
				stopObserve();
				break;
			}

			Student localInfo = getLocalStudent();

			if (!alreadySendToTeacher) {
				boolean successSend = sendToTeacher(localInfo);
				if (successSend) {
					alreadySendToTeacher = true;
				}
			}

			sleepForWhile();
		}
		System.out.println("LoginObserver stoped...");

	}
	
	private Student getLocalStudent() {
		//
		String name = DialogSettingsUtils.get("student", "name");
		System.out.println("name:"+name);
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
	
	private boolean sendToTeacher(Student localInfo) {
		//
		if (localInfo == null) {
			return false;
		}
		
		try {
			MessageSender sender = NaiteNetClient.getInstance().getMessageSender();
			
			SendMessage sendMessage = new SendMessage();
			sendMessage.setCommand("registerStudent");
			sendMessage.addNameValue(new NameValue("name", localInfo.getName()));
			sendMessage.addNameValue(new NameValue("email", localInfo.getEmail()));
			sendMessage.addNameValue(new NameValue("pass", localInfo.getPassword()));
			
			String result = sender.send(localInfo.getEmail(), "teacher", sendMessage);
			if (result != null && result.equals("ok")) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setAlreadySendToTeacher(boolean alreadySendToTeacher) {
		this.alreadySendToTeacher = alreadySendToTeacher;
	}
}
