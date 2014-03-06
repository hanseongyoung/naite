package kr.namoosori.naite.ri.plugin.teacher.views;

import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

public class StudentsView extends ViewPart implements MessageListener {
	//
	public static final String ID = StudentsView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;

	@Override
	public void createPartControl(Composite parent) {
		//
		NaiteNetClient.getInstance().addMessageListener(this);
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		createForm(parent);
		createStudentListSection(form);
		createStudentDetailSection(form);
	}

	private void createForm(Composite parent) {
		//
		form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
		form.setText("수강생 정보");
		toolkit.decorateFormHeading(form.getForm());
	}

	private Section studentListSection;
	private void createStudentListSection(ScrolledForm parentForm) {
		//
		studentListSection = toolkit.createSection(parentForm.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		studentListSection.setText("수강생 목록");
		studentListSection.setDescription("수강생의 현황을 보실 수 있습니다.");
		studentListSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		studentListSection.setExpanded(true);
		Composite client = createStudentListSectionClient(studentListSection);
		studentListSection.setClient(client);
	}

	private TableViewer studentListViewer;
	private Composite createStudentListSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		
		TableViewer studentListViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		Table table = studentListViewer.getTable();
		
		TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setWidth(90);
		
		TableColumn emailColumn = new TableColumn(table, SWT.LEFT);
		emailColumn.setWidth(120);
		
		studentListViewer.setContentProvider(new ArrayContentProvider());
		studentListViewer.setLabelProvider(new StudentTableLabelProvider());
		return composite;
	}

	private Section studentDetailSection;
	
	private void createStudentDetailSection(ScrolledForm parentForm) {
		//
		studentDetailSection = toolkit.createSection(parentForm.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		studentDetailSection.setText("수강생");
		studentDetailSection.setDescription("수강생의 상세정보를 확인합니다.");
		studentDetailSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		studentDetailSection.setExpanded(true);
		Composite client = createStudentDetailSectionClient(studentDetailSection);
		studentDetailSection.setClient(client);
	}

	private Composite createStudentDetailSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(1, false));

		return composite;
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
				Student student = new Student();
				student.setEmail(message.getSenderId());
				student.setName(message.getValue("name"));
				studentListViewer.add(student);
			}
		});
	}

}
