package kr.namoosori.naite.ri.plugin.student.dialogs;

import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;
import kr.namoosori.naite.ri.plugin.student.util.DialogSettingsUtils;

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

public class StudentInfoDialog extends TitleAreaDialog {
	//
	private static final String SECTION_STUDENT = "student";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASS = "pass";
	
	private Text nameText;
	private Text mailText;
	private Text passText;
	private Button initButton;
	
	public StudentInfoDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("수강생 정보");
		setMessage("수강생 정보를 입력합니다.");
		Composite composite = (Composite) super.createDialogArea(parent);
		createForm(composite);
		return composite;
	}
	
	private void createForm(Composite parent) {
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label nameLabel = new Label(composite, SWT.RIGHT);
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		nameLabel.setText("이름");
		
		nameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label mailLabel = new Label(composite, SWT.RIGHT);
		mailLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		mailLabel.setText("이메일");
		
		mailText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		mailText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label passLabel = new Label(composite, SWT.RIGHT);
		passLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		passLabel.setText("비밀번호");
		
		passText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
		passText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
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
		nameText.setText("");
		mailText.setText("");
		passText.setText("");
		saveData();
	}
	
	private void initControl() {
		//
		String name = DialogSettingsUtils.get(SECTION_STUDENT, KEY_NAME);
		String email = DialogSettingsUtils.get(SECTION_STUDENT, KEY_EMAIL);
		String pass = DialogSettingsUtils.get(SECTION_STUDENT, KEY_PASS);
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
		DialogSettingsUtils.put(SECTION_STUDENT, KEY_NAME, nameText.getText());
		DialogSettingsUtils.put(SECTION_STUDENT, KEY_EMAIL, mailText.getText());
		DialogSettingsUtils.put(SECTION_STUDENT, KEY_PASS, passText.getText());
	}

	@Override
	protected void okPressed() {
		//
		if (!checkControl()) return;
		saveData();
		setContextData();
		super.okPressed();
	}

	private void setContextData() {
		//
		String email = DialogSettingsUtils.get("student", "email");
		NaiteNetClient.getInstance().getContext().setClientId(email);
	}

}
