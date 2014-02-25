package kr.namoosori.naite.ri.plugin.student.event;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;

public class TeacherEventHandler extends Thread {
	//
	private static TeacherEventHandler instance = new TeacherEventHandler();
	
	public static TeacherEventHandler getInstance() {
		//
		return instance;
	}
	
	private static final long INTERVAL = 1000 * 30;
	
	private boolean requiredRefresh;
	
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
			if (requiredRefresh) {
				handleRefresh();
				requiredRefresh = false;
			}
			sleepForWhile();
		}
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
