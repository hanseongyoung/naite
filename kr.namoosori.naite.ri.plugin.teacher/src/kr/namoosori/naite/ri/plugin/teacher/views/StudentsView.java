package kr.namoosori.naite.ri.plugin.teacher.views;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;
import kr.namoosori.naite.ri.plugin.netserver.facade.ServerEventListener;
import kr.namoosori.naite.ri.plugin.netserver.main.NaiteNetServer;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

public class StudentsView extends ViewPart implements MessageListener, ServerEventListener {
	//
	public static final String ID = StudentsView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;

	@Override
	public void createPartControl(Composite parent) {
		//
		NaiteNetClient.getInstance().addMessageListener(this);
		NaiteNetServer.getInstance().addServerEventListener(this);
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		createForm(parent);
		createStudentListSection(form);
		createStudentDetailSection(form);
		
		refreshStudentViewer();
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
	private List<Student> students = new ArrayList<Student>();
	private List<Student> tmpStudents = new ArrayList<Student>();
	private boolean existStudent(String email) {
		//
		for (Student student : tmpStudents) {
			if (student.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	private Composite createStudentListSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(2, false));
		
		studentListViewer = new TableViewer(composite, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		Table table = studentListViewer.getTable();
		
		TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setWidth(120);
		
		TableColumn emailColumn = new TableColumn(table, SWT.LEFT);
		emailColumn.setWidth(200);
		
		studentListViewer.setContentProvider(new ArrayContentProvider());
		studentListViewer.setLabelProvider(new StudentTableLabelProvider());
		
		Button registerButton = toolkit.createButton(composite, "등록", SWT.FLAT);
		registerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) studentListViewer.getSelection();
				Student student = (Student) selection.getFirstElement();
				String lectureId = TeacherContext.CURRENT_LECTURE.getId(); 
				
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.createStudent(lectureId, student);
					tmpStudents.remove(student);
					refreshStudentViewer();
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		return composite;
	}
	
	private void refreshStudentViewer() {
		//
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		String lectureId = TeacherContext.CURRENT_LECTURE.getId(); 
		try {
			this.students = service.findStudents(lectureId);
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		List<Student> allStudents = new ArrayList<Student>();
		if (this.students != null && this.students.size() > 0) {
			allStudents.addAll(this.students);
		}
		if (this.tmpStudents != null && this.tmpStudents.size() > 0) {
			allStudents.addAll(this.tmpStudents);
		}
		
		for (Student student : allStudents) {
			System.out.println("****" + student);
		}
		
		studentListViewer.setInput(allStudents);
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
		//
		form.setFocus();
	}

	@Override
	public void messageReceived(final ClientMessage message) {
		//
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				//MessageDialog.openInformation(getSite().getShell(), "메시지", message.getSenderId());
				if (!existStudent(message.getSenderId())) {
					Student student = new Student();
					student.setEmail(message.getSenderId());
					student.setName(message.getValue("name"));
					student.setPassword(message.getValue("pass"));
					studentListViewer.add(student);
					tmpStudents.add(student);
				}
			}
		});
	}

	@Override
	public void clientIn(String clientId) {
		//
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (studentListViewer == null) return;
				IStructuredSelection selection = (IStructuredSelection) studentListViewer.getSelection();
				Student student = (Student) selection.getFirstElement();
				student.setLogined(true);
				studentListViewer.refresh();
			}
		});
	}

	@Override
	public void clientOut(String clientId) {
		//
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (studentListViewer == null) return;
				IStructuredSelection selection = (IStructuredSelection) studentListViewer.getSelection();
				Student student = (Student) selection.getFirstElement();
				student.setLogined(false);
				studentListViewer.refresh();
			}
		});
	}

	@Override
	public void dispose() {
		//
		NaiteNetServer.getInstance().removeServerEventListener(this);
		NaiteNetClient.getInstance().removeMessageListener(this);
		super.dispose();
	}

}
