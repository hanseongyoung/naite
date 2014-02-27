package kr.namoosori.naite.ri.plugin.core.job;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;

public abstract class BusyJobDialog {
	//
	private String dialogMsg;
    private boolean isVisibleDialog;
    private Shell shell;
    private Object data;
    private Object[] dataArray;

    public BusyJobDialog(Shell shell) {
        this.shell = shell;
        this.dialogMsg = "조회중...";
        this.isVisibleDialog = true;
    }

    public BusyJobDialog(Shell shell, boolean isVisibleDialog, String dialogMsg) {
        this.shell = shell;
        this.dialogMsg = dialogMsg;
        this.isVisibleDialog = isVisibleDialog;
    }

    public BusyJobDialog(Shell shell, boolean isVisibleDialog) {
        this.shell = shell;
        this.dialogMsg = "조회중...";
        this.isVisibleDialog = isVisibleDialog;
    }

    public BusyJobDialog(Shell shell, String dialogMsg) {
        this.shell = shell;
        this.dialogMsg = dialogMsg;
        this.isVisibleDialog = true;
    }

    public abstract void job();

    public void run() {

        if(isVisibleDialog)
            busyJobDialog(shell);
        else
            busyJob(shell);
    }

    private void busyJobDialog(Shell shell) {
        try {
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
            dialog.run(true, false, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    try {
                        monitor.beginTask(dialogMsg, IProgressMonitor.UNKNOWN);
                        job();

                    } finally {
                        monitor.done();
                    }

                }
            });

        } catch (InvocationTargetException ite) {
            throw new RuntimeException(ite.getCause());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private void busyJob(Shell shell) {
        BusyJob longJob = new BusyJob() {
            @Override
            public void run() {
                //
                job();
            }
        };

        BusyIndicator.showWhile(shell.getDisplay(), longJob);
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        run();
        return data;
    }

    public Object[] getDataArray() {
        run();
        return this.dataArray;
    }

    public void setDataArray(Object... dataArray) {
        this.dataArray = dataArray;
    }
}
