package kr.namoosori.naite.ri.plugin.teacher.views;

import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

public class StudentsView extends ViewPart implements MessageListener {
	//
	public static final String ID = StudentsView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		NaiteNetClient.getInstance().addMessageListener(this);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageReceived(final ClientMessage message) {
		//
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openInformation(getSite().getShell(), "메시지", message.getSenderId());
			}
		});
	}

}
