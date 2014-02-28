package kr.namoosori.naite.ri.plugin.student.event;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.student.StudentContext;
import kr.namoosori.naite.ri.plugin.student.login.LoginManager;

import org.eclipse.swt.widgets.Display;

public class TeacherEventHandler extends Thread {
	//
	private static TeacherEventHandler instance = new TeacherEventHandler();
	
	public static TeacherEventHandler getInstance() {
		//
		return instance;
	}
	
	private static final long INTERVAL = 1000 * 10;
	
	private boolean requiredRefresh;
	private StudentContext context = StudentContext.getInstance();
	private LoginManager loginManager = LoginManager.getInstance();
	
	private TeacherEventHandler() {
		super("TeacherEventHandler");
	}
	
	private List<RefreshEventListener> listeners = new ArrayList<RefreshEventListener>();
	
	public void addRefreshEventListener(RefreshEventListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeRefreshEventListener(RefreshEventListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void run() {
		//
		while(true) {
			if (check() && requiredRefresh) {
				handleRefresh();
				requiredRefresh = false;
			}
			sleepForWhile();
		}
	}
	
	private boolean invokedTeacherNotExist = false;
	private boolean invokedNotLogin = false;
	
	private boolean check() {
		//
		if (!context.isTeacherAlive()) {
			if (!invokedTeacherNotExist) {
				handleTeacherNotExist();
				invokedTeacherNotExist = true;
			}
			return false;
		}
		
		if (!loginManager.isLogin()) {
			if (!invokedNotLogin) {
				handleNotLogin();
				invokedNotLogin = true;
			}
			return false;
		}
		
		invokedTeacherNotExist = false;
		invokedNotLogin = false;
		return true;
	}

	
	private void handleNotLogin() {
		//
		if (this.listeners.size() <= 0) {
			return;
		}
		
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				for (RefreshEventListener listener : listeners) {
					listener.notLogin();
				}
			}
		});
	}

	private void handleTeacherNotExist() {
		//
		if (this.listeners.size() <= 0) {
			return;
		}
		
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				for (RefreshEventListener listener : listeners) {
					listener.teacherNotExist();
				}
			}
		});
	}

	private void handleRefresh() {
		//
		if (this.listeners.size() <= 0) {
			return;
		}
		
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				for (RefreshEventListener listener : listeners) {
					listener.refresh();
				}
			}
		});
	}

	private void sleepForWhile() {
		//
		try {
			sleep((long) (Math.random() * INTERVAL));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setRequiredRefresh(boolean requiredRefresh) {
		this.requiredRefresh = requiredRefresh;
	}
}
