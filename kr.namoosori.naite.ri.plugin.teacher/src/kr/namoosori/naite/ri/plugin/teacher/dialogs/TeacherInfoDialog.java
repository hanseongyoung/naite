package kr.namoosori.naite.ri.plugin.teacher.dialogs;

import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteWSClient;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;
import kr.namoosori.naite.ri.plugin.teacher.util.DialogSettingsUtils;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TeacherInfoDialog extends TitleAreaDialog {
	//
	private Text domainText;
	private Text domainPortText;
	private Text nameText;
	private Text mailText;
	private Text passText;
	private Button initButton;
	
	public TeacherInfoDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("설정");
		setMessage("컨텐츠 서버 접속에 필요한 정보를 입력합니다.");
		Composite composite = (Composite) super.createDialogArea(parent);
		createForm(composite);
		return composite;
	}
	
	private void createForm(Composite parent) {
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label domainLabel = new Label(composite, SWT.RIGHT);
		domainLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		domainLabel.setText("도메인");
		
		domainText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		domainText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label domainPortLabel = new Label(composite, SWT.RIGHT);
		domainPortLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		domainPortLabel.setText("포트");
		
		domainPortText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		domainPortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label nameLabel = new Label(composite, SWT.RIGHT);
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		nameLabel.setText("이름");
		
		nameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label mailLabel = new Label(composite, SWT.RIGHT);
		mailLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		mailLabel.setText("이메일");
		
		mailText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		mailText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label passLabel = new Label(composite, SWT.RIGHT);
		passLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		passLabel.setText("비밀번호");
		
		passText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
		passText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		initButton = new Button(composite, SWT.FLAT);
		initButton.setText("초기화");
		initButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		initButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		
		initControl();
	}
	
	private void clear() {
		//
		domainText.setText(TeacherContext.DEFAULT_DOMAIN);
		domainPortText.setText(""+TeacherContext.DEFAULT_PORT);
		nameText.setText("");
		mailText.setText("");
		passText.setText("");
		saveData();
	}
	
	private void initControl() {
		//
		String domain = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_DOMAIN);
		String port = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_PORT);
		String name = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_NAME);
		String email = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_EMAIL);
		String pass = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_PASS);
		domainText.setText(domain != null ? domain : TeacherContext.getInstance().getServerIp());
		domainPortText.setText(port != null ? port : "" + TeacherContext.getInstance().getServerPort());
		nameText.setText(name != null ? name : "");
		mailText.setText(email != null ? email : "");
		passText.setText(pass != null ? pass : "");
	}
	
	private boolean checkControl() {
		//
		String name = nameText.getText().trim();
		if (name == null || name.length() <= 0) {
			MessageDialog.openWarning(getShell(), "입력정보 확인", "이름을 입력하세요.");
			nameText.setFocus();
			return false;
		}
		
		String email = mailText.getText().trim();
		if (email == null || email.length() <= 0) {
			MessageDialog.openWarning(getShell(), "입력정보 확인", "이메일을 입력하세요.");
			mailText.setFocus();
			return false;
		}
		
		String pass = passText.getText().trim();
		if (pass == null || pass.length() <= 0) {
			MessageDialog.openWarning(getShell(), "입력정보 확인", "비밀번호를 입력하세요.");
			passText.setFocus();
			return false;
		}
		return true;
	}
	
	private void saveData() {
		//
		DialogSettingsUtils.put(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_DOMAIN, domainText.getText());
		DialogSettingsUtils.put(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_PORT, domainPortText.getText());
		DialogSettingsUtils.put(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_NAME, nameText.getText());
		DialogSettingsUtils.put(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_EMAIL, mailText.getText());
		DialogSettingsUtils.put(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_PASS, passText.getText());
	}

	@Override
	protected void okPressed() {
		//
		if (!checkControl()) return;
		saveData();
		setContextData();
		NaiteWSClient.getInstance().close();
		EventManager.getInstance().invokeRefreshEvent();
		super.okPressed();
	}

	private void setContextData() {
		//
		String domain = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_DOMAIN);
		TeacherContext.getInstance().setServerIp(domain);
		
		String port = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_PORT);
		TeacherContext.getInstance().setServerPort(parseInt(port));
		
		String email = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_EMAIL);
		NaiteNetClient.getInstance().getContext().setClientId(email);
	}

	private int parseInt(String str) {
		//
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return 0;
	}

}
