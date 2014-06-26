package kr.namoosori.naite.ri.plugin.student.views;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.job.BusyUIIndicateJob;
import kr.namoosori.naite.ri.plugin.core.project.NaiteProject;
import kr.namoosori.naite.ri.plugin.core.project.NaiteWorkspace;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.core.service.domain.StudentProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;
import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.MessageListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.RefreshListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.ServerStateListener;
import kr.namoosori.naite.ri.plugin.netclient.facade.message.ClientMessage;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteWSClient;
import kr.namoosori.naite.ri.plugin.student.StudentContext;
import kr.namoosori.naite.ri.plugin.student.StudentPlugin;
import kr.namoosori.naite.ri.plugin.student.dialogs.StudentInfoDialog;
import kr.namoosori.naite.ri.plugin.student.login.LoginListener;
import kr.namoosori.naite.ri.plugin.student.login.LoginManager;
import kr.namoosori.naite.ri.plugin.student.util.DialogSettingsUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

public class StudentLectureView extends ViewPart implements LoginListener, ServerStateListener, MessageListener, RefreshListener {
	//
	// TODO : 프로젝트를 설치할 때 자신의 아이디가 포함됨.
	// TODO : 프로젝트를 제출할 때 자신의 아이디 및 날짜버전으로 제출됨.
	// TODO : 강사는 수강생이 제출한 프로젝트를 볼 수 있음.
	// TODO : 강사가 프로젝트 내 특정 파일을 각 수강생에게 배포할 수 있음.
	public static final String ID = StudentLectureView.class.getName();
	
	private LoginManager loginManager = LoginManager.getInstance();
	
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	class LoginStatusAction extends Action {
		public LoginStatusAction() {
			setId("loginStatusAction");
			setImageDescriptor(StudentPlugin.getDefault().getImageRegistry().getDescriptor(StudentPlugin.IMG_COG));
			setToolTipText("설정");
			setText("설정");
		}

		@Override
		public void run() {
			StudentInfoDialog dialog = new StudentInfoDialog(getSite().getShell());
			dialog.open();
		}
	}
	
	/**
	 * 강의 선택 액션
	 */
	class SelectLectureAction extends Action {
		//
		public SelectLectureAction() {
			//
			setId("selectLectureAction");
			setImageDescriptor(StudentPlugin.getDefault().getImageRegistry().getDescriptor(StudentPlugin.IMG_HELP_TOPIC));
			setToolTipText("강의선택");
		}

		@Override
		public void run() {
			//
			NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
			List<Lecture> lectures = new ArrayList<Lecture>();
			try {
				lectures = service.getStudentLectures(getStudentEmail());
			} catch (NaiteException e) {
				e.printStackTrace();
			}
			
			ElementListSelectionDialog dialog = new ElementListSelectionDialog(getSite().getShell(), new LabelProvider(){
				@Override
				public String getText(Object element) {
					if (element == null) return "";
					Lecture lecture = (Lecture) element;
					return lecture.getName();
				}
			});
			dialog.setElements(lectures.toArray());
			dialog.setTitle("강의 선택");
			dialog.setMessage("이전 강의를 선택하세요.");
			
			if (dialog.open() == Window.OK) {
				Lecture selected = (Lecture) dialog.getResult()[0];
				currentLectureId = selected.getId();
				refresh();
			}
		}
	}
	
	/**
	 * Refresh 액션
	 */
	class RefreshAction extends Action {
		//
		public RefreshAction() {
			//
			setId("refreshAction");
			setImageDescriptor(StudentPlugin.getDefault().getImageRegistry().getDescriptor(StudentPlugin.IMG_REFRESH));
			setToolTipText("Refresh");
		}

		@Override
		public void run() {
			//
			refresh();
		}
	}
	
	@Override
	public void serverOn() {
		System.out.println("[StudentLectureView] server on");
		loginManager.check();
	}

	@Override
	public void serverOff() {
		System.out.println("[StudentLectureView] server off");
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				teacherNotExist();
			}
		});
	}

	@Override
	public void logined() {
		System.out.println("[StudentLectureView] logined");
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		});
	}

	@Override
	public void logoffed() {
		System.out.println("[StudentLectureView] logoffed");
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				updateNotLogin();
			}
		});
	}
	
	@Override
	public void messageReceived(final ClientMessage message) {
		System.out.println("[StudentLectureView] message --> "+message.getCommand());
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openInformation(getSite().getShell(), "메시지", message.getSenderId() + " : " + message.getCommand());
				if ("refresh".equals(message.getCommand())) {
					refresh();
				}
			}
		});
	}

	@Override
	public void createPartControl(Composite parent) {
		//
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		createForm(parent, "로그인하세요.");
		
		/*
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
		ActionContributionItem item = new ActionContributionItem(new LoginStatusAction());
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		manager.add(item);
		manager.update(true);
		*/
		
		// event
		loginManager.addLoginListener(this);
		EventManager.getInstance().addServerStateListener(this);
		EventManager.getInstance().addMessageListener(this);
		EventManager.getInstance().addRefreshListener(this);
		
		//NaiteWSClient.getInstance().send();
		if (getStudentEmail() != null) {
			refresh();
		}
	}
	
	private Student findCurrentStudent(String studentEmail) {
		//
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		Student student = null;
		try {
			student = service.getCurrentStudent(StudentContext.CURRENT_LECTURE.getId(), studentEmail);
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public void refresh() {
		//
		NaiteWSClient.getInstance().connect(getStudentEmail());
		
		StudentContext.CURRENT_LECTURE = getCurrentLecture();
		
		disposeSection();
		
		if (StudentContext.CURRENT_LECTURE == null) {
			form.setText("진행중인 강의가 없습니다.");
			form.getParent().layout();
			return;
		}
		
		form.setText(StudentContext.CURRENT_LECTURE.getName());
		
		// section
		createBookSection(form);
		createExampleSection(form);
		
		form.getParent().layout();
		bookSection.getParent().layout();
		exampleSection.getParent().layout();
		form.reflow(true);
	}
	
	private void disposeSection() {
		//
		if (bookSection != null && !bookSection.isDisposed()) {
			bookSection.dispose();
		}
		if (exampleSection != null && !exampleSection.isDisposed()) {
			exampleSection.dispose();
		}
	}

	private void teacherNotExist() {
		//
		disposeSection();
		
		form.setText("강사가 로그아웃 하였습니다.");
		form.getParent().layout();
	}

	private void updateNotLogin() {
		//
		disposeSection();
		
		form.setText("수강생 정보를 확인하세요.");
		form.getParent().layout();
	}
	
	private void createForm(Composite parent, String formTitle) {
		//
		// scrolled form
		form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		form.setText(formTitle);
		toolkit.decorateFormHeading(form.getForm());
		
		ToolBarManager toolbarManager = (ToolBarManager) form.getToolBarManager();
		toolbarManager.add(new LoginStatusAction());
		toolbarManager.add(new SelectLectureAction());
		toolbarManager.add(new RefreshAction());
		toolbarManager.update(true);
	}
	
	// 조회할 기준 강의 아이디
	private String currentLectureId = null;

	private Lecture getCurrentLecture() {
		//
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		try {
			Lecture lecture = null;
			if (currentLectureId == null) {
				lecture = service.getCurrentLectureOfStudent(getStudentEmail());
			} else {
				lecture = service.getLecture(currentLectureId);
			}
			return lecture;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String getStudentEmail() {
		String studentEmail = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_STUDENT, DialogSettingsUtils.KEY_EMAIL);
		return studentEmail;
	}


	private Section bookSection;

	private void createBookSection(ScrolledForm parentForm) {
		//
		bookSection = toolkit.createSection(parentForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		bookSection.setText("강의교재");
		bookSection.setDescription("아래 링크를 선택하여 다운로드 하세요.");
		bookSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		bookSection.setExpanded(true);
		
		Composite client = createBookSectionClient(bookSection);
		bookSection.setClient(client);
	}
	
	private Composite createBookSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(2, false));
		
		for (Textbook textbook : StudentContext.CURRENT_LECTURE.getTextbooks()) {
			createTextbookLink(composite, textbook);
		}
		
		return composite;
	}
	
	private void createTextbookLink(Composite composite, final Textbook textbook) {
		//
		ImageHyperlink image = toolkit.createImageHyperlink(composite, SWT.NONE);
		image.setImage(StudentPlugin.getDefault().getImageRegistry().get(StudentPlugin.IMG_HELP_TOPIC));
		
		Hyperlink link = toolkit.createHyperlink(composite, textbook.getName(), SWT.WRAP);
	
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.SAVE);
                fileDialog.setFilterExtensions(new String[] { "*.*" });
                fileDialog.setFilterNames(new String[] { "All Files" });
                fileDialog.setText("강의교재 저장");
                fileDialog.setFileName(textbook.getName());
                final String fileSelected = fileDialog.open();
                if (fileSelected == null || fileSelected.length() <= 0) return;
                
                IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
				BusyUIIndicateJob job = new BusyUIIndicateJob(manager) {
					@Override
					public Object job() {
						//
						NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
						try {
							service.downloadTextbook(fileSelected, textbook);
						} catch (NaiteException e) {
							e.printStackTrace();
						}
						return null;
					}
				};
				job.start();
				
			}
		});
	}
	
	private Section exampleSection;
	
	private void createExampleSection(ScrolledForm parentForm) {
		//
		exampleSection = toolkit.createSection(parentForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		//toolkit.createCompositeSeparator(section);
		exampleSection.setText("실습예제");
		exampleSection.setDescription("실습예제를 프로젝트 환경에 설치합니다.");
		exampleSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		exampleSection.setExpanded(true);
		
		Composite client = createExampleSectionClient(exampleSection);
		exampleSection.setClient(client);
	}

	private Composite createExampleSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(3, false));
		Student currentStudent = findCurrentStudent(getStudentEmail());
		
		for (ExerciseProject exerciseProject : StudentContext.CURRENT_LECTURE.getExerciseProjects()) {
			StudentProject studentProject = exerciseProject.newStudentProject(currentStudent);
			createExerciseProjectLink(composite, new NaiteProject(studentProject));
		}
		
		return composite;
	}
	
	private void refreshExampleSectionClient() {
		//
		Control client = exampleSection.getClient();
		client.dispose();
		Composite newClient = createExampleSectionClient(exampleSection);
		exampleSection.setClient(newClient);
		exampleSection.getParent().layout();
		exampleSection.layout();
		form.reflow(true);
	}

	private void createExerciseProjectLink(Composite composite,
			final NaiteProject project) {
		//
		ImageHyperlink image = toolkit.createImageHyperlink(composite, SWT.NONE);
		image.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT));
		
		Hyperlink link = toolkit.createHyperlink(composite, project.getName(), SWT.WRAP);
	
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				//
				try {
					project.createAndExportToStudent();
					refreshExampleSectionClient();
				} catch (NaiteException e1) {
					e1.printStackTrace();
					MessageDialog.openWarning(getSite().getShell(), "실습예제 설치", e1.getMessage());
				}
			}
		});
		
		if (NaiteWorkspace.getInstance().exist(project.getName())) {
			Hyperlink updateLink = toolkit.createHyperlink(composite, "업로드", SWT.WRAP);
			updateLink.addHyperlinkListener(new HyperlinkAdapter(){
				@Override
				public void linkActivated(HyperlinkEvent e) {
					try {
						project.exportToStudentProject();
						MessageDialog.openInformation(getSite().getShell(), "실습예제 업로드", "실습 예제가 나의 공간으로 업로드 되었습니다.");
					} catch (NaiteException e1) {
						e1.printStackTrace();
						MessageDialog.openWarning(getSite().getShell(), "실습예제 업로드", e1.getMessage());
					}
				}
			});
		} else {
			toolkit.createLabel(composite, "");
		}
	}

	@Override
	public void setFocus() {
		//
		form.setFocus();
	}
	
	@Override
	public void dispose() {
		//
		EventManager.getInstance().removeRefreshListener(this);
		EventManager.getInstance().removeMessageListener(this);
		EventManager.getInstance().removeServerStateListener(this);
		loginManager.removeLoginListener(this);
		if (toolkit != null) {
			toolkit.dispose();
		}
		
		NaiteWSClient.getInstance().close();
		
		super.dispose();
	}

}
