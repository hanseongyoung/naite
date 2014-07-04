package kr.namoosori.naite.ri.plugin.core.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;


/**
 * 긴 시간을 요구하는 Job일 경우 작업 진행하는 동안 UI에 표현하고
 * 다른 작업을 할 수 있게 하는 Job 컨테이너이다.
 * BusyIndicator와 같이 수행한다.
 *
 * @see <a href="http://www.eclipse.org/swt/snippets/#busyindicator">BusyIndicator snippets</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * @author syhan
 */
public abstract class BusyUIIndicateJob extends BusyJob {
	//
	private boolean done = false;
    private Display display = Display.getCurrent();
    
    private IStatusLineManager manager;
    private String displayMessage;
    
    public BusyUIIndicateJob(IStatusLineManager manager, String displayMessage) {
    	this.manager = manager;
    	this.displayMessage = displayMessage;
    }

    public abstract Object job();
    public void doAfter(){}

    public Object start() {
        //
        BusyIndicator.showWhile(display, this);
        return getData();
    }

    @Override
    public void run() {
        //
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                try {
                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                            // 검색 진행중임을 UI에 표현
                        	IProgressMonitor pm = manager.getProgressMonitor();
                        	pm.beginTask(displayMessage, IProgressMonitor.UNKNOWN);
                        	//manager.setMessage("다운로드 중...");
                        }
                    });

                    setData(job());

                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                            // 검색 완료되었음을 UI에 표현
                        	//manager.setMessage(null);
                        	IProgressMonitor pm = manager.getProgressMonitor();
                        	pm.done();
                        	//MessageDialog.openInformation(getShell(), "완료", "완료되었습니다.");
                        	
                        	doAfter();
                        }
                    });
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                        	MessageDialog.openError(getShell(), "조회중 오류", "조회중 문제 발생");
                        }
                    });
                    throw new RuntimeException(e);
                } finally {
                    done = true;
                    display.wake();
                }

            }
        });
        thread.start();

        while (!done) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private Shell getShell() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }
}
