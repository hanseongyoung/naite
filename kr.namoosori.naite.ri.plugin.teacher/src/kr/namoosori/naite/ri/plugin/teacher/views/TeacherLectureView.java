package kr.namoosori.naite.ri.plugin.teacher.views;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.job.BusyUIIndicateJob;
import kr.namoosori.naite.ri.plugin.core.project.NaiteProject;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;
import kr.namoosori.naite.ri.plugin.netclient.event.EventManager;
import kr.namoosori.naite.ri.plugin.netclient.facade.RefreshListener;
import kr.namoosori.naite.ri.plugin.netclient.main.NaiteWSClient;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.ContentsPublishDialog;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.StandardProjectSelectDialog;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.StandardTextbookSelectDialog;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.TeacherInfoDialog;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.TeacherProjectUploadDialog;
import kr.namoosori.naite.ri.plugin.teacher.util.DialogSettingsUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
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

public class TeacherLectureView extends ViewPart implements RefreshListener {
	//
	public static final String ID = TeacherLectureView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	public TeacherLectureView() {
	}
	
	/**
	 * 로그인 액션
	 */
	class LoginStatusAction extends Action {
		public LoginStatusAction() {
			setId("loginStatusAction");
			setImageDescriptor(TeacherPlugin.getDefault().getImageRegistry().getDescriptor(TeacherPlugin.IMG_COG));
			setToolTipText("설정");
			setText("설정");
		}

		@Override
		public void run() {
			TeacherInfoDialog dialog = new TeacherInfoDialog(getSite().getShell());
			dialog.open();
		}
	}
	
	/**
	 * 강의 추가 액션
	 */
	class AddLectureAction extends Action {
		//
		public AddLectureAction() {
			//
			setId("addLectureAction");
			setImageDescriptor(TeacherPlugin.getDefault().getImageRegistry().getDescriptor(TeacherPlugin.IMG_HELP_TOPIC));
			setToolTipText("강의등록");
		}

		@Override
		public void run() {
			//
			InputDialog dialog = new InputDialog(getSite().getShell(), 
					"강의등록", "등록할 강의명을 입력하세요.", null, null);
			if (dialog.open() == Window.OK) {
				String lectureTitle = dialog.getValue();
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.createLecture(lectureTitle, getTeacherEmail());
					refresh();
					refreshStudents();
				} catch (NaiteException e) {
					e.printStackTrace();
				}
			}
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
			setImageDescriptor(TeacherPlugin.getDefault().getImageRegistry().getDescriptor(TeacherPlugin.IMG_HELP_TOPIC));
			setToolTipText("강의선택");
		}

		@Override
		public void run() {
			//
			NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
			List<Lecture> lectures = new ArrayList<Lecture>();
			try {
				lectures = service.getTeacherLectures(getTeacherEmail());
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
				refreshStudentsView();
				refreshStudents();
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
			setImageDescriptor(TeacherPlugin.getDefault().getImageRegistry().getDescriptor(TeacherPlugin.IMG_REFRESH));
			setToolTipText("Refresh");
		}

		@Override
		public void run() {
			//
			refresh();
		}
	}
	
	private void refreshStudentsView() {
		StudentsView view = (StudentsView) getSite().getPage().findView(StudentsView.ID);
		view.refresh();
	}

	private String getTeacherEmail() {
		//
		String teacherEmail = DialogSettingsUtils.get(DialogSettingsUtils.SECTION_TEACHER, DialogSettingsUtils.KEY_EMAIL);
		return teacherEmail;
	}
	
	
	@Override
	public void createPartControl(Composite parent) {
		//
		TeacherContext.CURRENT_LECTURE = getCurrentLecture();
		
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		createForm(parent);
		
		// section
		createBookSection(form);
		createExampleSection(form);
		form.reflow(true);
		
		// event
		EventManager.getInstance().addRefreshListener(this);
		NaiteWSClient.getInstance().connect(getTeacherEmail());
	}
	
	public void refreshStudents() {
		// TODO
//		MessageSender sender = NaiteNetClient.getInstance().getMessageSender();
//		SendMessage sendMessage = new SendMessage();
//		sendMessage.setCommand("refresh");
//		sender.sendAll(NaiteNetClient.getInstance().getClientId(), sendMessage);
	}

	/**
	 * @see kr.namoosori.naite.ri.plugin.netclient.facade.RefreshListener#refresh()
	 */
	public void refresh() {
		//
		NaiteWSClient.getInstance().connect(getTeacherEmail());
		
		TeacherContext.CURRENT_LECTURE = getCurrentLecture();
		
		if (bookSection != null && !bookSection.isDisposed()) {
			bookSection.dispose();
		}
		if (exampleSection != null && !exampleSection.isDisposed()) {
			exampleSection.dispose();
		}
		
		if (TeacherContext.CURRENT_LECTURE == null) {
			form.setText("진행중인 강의가 없습니다.");
			form.getParent().layout();
			return;
		}
		
		form.setText(TeacherContext.CURRENT_LECTURE.getName());
		
		// section
		createBookSection(form);
		createExampleSection(form);
		
		form.getParent().layout();
		bookSection.getParent().layout();
		exampleSection.getParent().layout();
		form.reflow(true);
	}
	
	private void createForm(Composite parent) {
		//
		String title = null;
		if (TeacherContext.CURRENT_LECTURE == null) {
			title = "진행중인 강의가 없습니다.";
		} else {
			title = TeacherContext.CURRENT_LECTURE.getName();
		}
		
		// scrolled form
		form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		form.setText(title);
		toolkit.decorateFormHeading(form.getForm());
		
		ToolBarManager toolbarManager = (ToolBarManager) form.getToolBarManager();
		toolbarManager.add(new LoginStatusAction());
		toolbarManager.add(new AddLectureAction());
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
				lecture = service.getCurrentLecture(getTeacherEmail());
				if (lecture != null) {
					currentLectureId = lecture.getId();
				}
			} else {
				lecture = service.getLecture(currentLectureId);
			}
			return lecture;
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Section bookSection;

	private void createBookSection(ScrolledForm parentForm) {
		//
		bookSection = toolkit.createSection(parentForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		//toolkit.createCompositeSeparator(section); 섹션 하단에 라인 생성
		bookSection.setText("강의교재");
		bookSection.setDescription("아래 링크를 선택하여 다운로드 하세요.");
		bookSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		bookSection.setExpanded(true);
		if (TeacherContext.CURRENT_LECTURE != null) {
			bookSection.setTextClient(createBookSectionToolbar(parentForm, bookSection));
		}
		
		Composite client = createBookSectionClient(bookSection);
		bookSection.setClient(client);
	}

	private Control createBookSectionToolbar(ScrolledForm parentForm,
			Section section) {
		//
		ToolBar toolbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		
		//
		ToolItem itemSelect = new ToolItem(toolbar, SWT.PUSH);
		itemSelect.setToolTipText("강의교재 선택");
		itemSelect.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_CLOUD));
		itemSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StandardTextbookSelectDialog dialog = new StandardTextbookSelectDialog(getSite().getShell());
				if (dialog.open() == Window.OK) {
					TeacherContext.CURRENT_LECTURE = getCurrentLecture();
					refreshBookSectionClient();
					refreshStudents();
				}
			}
		});
		
		//
		ToolItem item = new ToolItem(toolbar, SWT.PUSH);
		item.setToolTipText("강의교재 등록");
		item.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_FOLDER));
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
                fileDialog.setFilterExtensions(new String[] { "*.pdf", "*.*" });
                fileDialog.setFilterNames(new String[] { "PDF 파일", "All Files" });
                fileDialog.setText("등록할 강의교재 선택");
                String fileSelected = fileDialog.open();
                if (fileSelected == null || fileSelected.length() <= 0) return;
                
                createTextbook(fileSelected, TeacherContext.CURRENT_LECTURE.getId());
			}
		});
		return toolbar;
	}
	
	

	private Composite createBookSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(4, false));
		
		if (TeacherContext.CURRENT_LECTURE != null) {
			Lecture lecture = (Lecture) TeacherContext.CURRENT_LECTURE;
			for (Textbook textbook : lecture.getTextbooks()) {
				createTextbookLink(composite, textbook);
			}
		}
		
		return composite;
	}
	
	private void refreshBookSectionClient() {
		//
		Control client = bookSection.getClient();
		client.dispose();
		Composite newClient = createBookSectionClient(bookSection);
		bookSection.setClient(newClient);
		bookSection.getParent().layout();
		form.reflow(true);
	}

	private void createTextbookLink(Composite composite, final Textbook textbook) {
		//
		ImageHyperlink image = toolkit.createImageHyperlink(composite, SWT.NONE);
		image.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_HELP_TOPIC));
		
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
                
                downloadTextbook(fileSelected, textbook);
			}
		});
		
		ImageHyperlink pubLink = toolkit.createImageHyperlink(composite, SWT.WRAP);
		pubLink.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_USER_GO));
		pubLink.setToolTipText("강의교재 배포");
		pubLink.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				ContentsPublishDialog dialog = new ContentsPublishDialog(getSite().getShell());
				if (dialog.open() == Window.OK) {
					List<String> checkedStudentEmails = dialog.getCheckedStudentEmails();
					
					publishTextbook(textbook.getId(), TeacherContext.CURRENT_LECTURE.getId(), checkedStudentEmails);
				}
			}
		});
		
		ImageHyperlink delLink = toolkit.createImageHyperlink(composite, SWT.WRAP);
		delLink.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_REMOVE));
		delLink.setToolTipText("강의교재 삭제");
		delLink.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				boolean confirm = MessageDialog.openConfirm(getSite().getShell(), "강의교재 삭제", "강의교재를 삭제하시겠습니까?");
				if (confirm) {
					deleteTextbook(textbook);
				}
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
		if (TeacherContext.CURRENT_LECTURE != null) {
			exampleSection.setTextClient(createExampleSectionToolbar(parentForm, exampleSection));
		}
		
		Composite client = createExampleSectionClient(exampleSection);
		exampleSection.setClient(client);
	}

	private Control createExampleSectionToolbar(ScrolledForm parentForm,
			Section section) {
		//
		ToolBar toolbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		
		ToolItem itemSelect = new ToolItem(toolbar, SWT.PUSH);
		itemSelect.setToolTipText("실습예제 선택");
		itemSelect.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_CLOUD));
		itemSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StandardProjectSelectDialog dialog = new StandardProjectSelectDialog(getSite().getShell());
				if (dialog.open() == Window.OK) {
					TeacherContext.CURRENT_LECTURE = getCurrentLecture();
					refreshExampleSectionClient();
					refreshStudents();
				}
			}
		});
		
		
		ToolItem item = new ToolItem(toolbar, SWT.PUSH);
		item.setToolTipText("실습예제 등록");
		item.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_FOLDER));
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TeacherProjectUploadDialog dialog = new TeacherProjectUploadDialog(getSite().getShell());
				if (dialog.open() == Window.OK) {
					TeacherContext.CURRENT_LECTURE = getCurrentLecture();
					refreshExampleSectionClient();
					refreshStudents();
				}
			}
		});
		return toolbar;
	}

	private Composite createExampleSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(4, false));
		
		if (TeacherContext.CURRENT_LECTURE != null) {
			Lecture lecture = (Lecture) TeacherContext.CURRENT_LECTURE;
			for (ExerciseProject exerciseProject : lecture.getExerciseProjects()) {
				createExerciseProjectLink(composite, new NaiteProject(exerciseProject));
			}
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
					project.create();
				} catch (NaiteException e1) {
					e1.printStackTrace();
					MessageDialog.openWarning(getSite().getShell(), "실습예제 설치", e1.getMessage());
				}
			}
		});
		
		ImageHyperlink pubLink = toolkit.createImageHyperlink(composite, SWT.WRAP);
		pubLink.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_USER_GO));
		pubLink.setToolTipText("실습예제 배포");
		pubLink.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				ContentsPublishDialog dialog = new ContentsPublishDialog(getSite().getShell());
				if (dialog.open() == Window.OK) {
					List<String> checkedStudentEmails = dialog.getCheckedStudentEmails();
					
					publishExerciseProject(project.getId(), TeacherContext.CURRENT_LECTURE.getId(), checkedStudentEmails);
				}
			}
		});
		
		ImageHyperlink delLink = toolkit.createImageHyperlink(composite, SWT.WRAP);
		delLink.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_REMOVE));
		delLink.setToolTipText("실습예제 삭제");
		delLink.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				boolean confirm = MessageDialog.openConfirm(getSite().getShell(), "실습예제 삭제", "실습예제를 삭제하시겠습니까?");
				if (confirm) {
					NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
					try {
						service.deleteExerciseProject((ExerciseProject) project.getProjectObject());
						TeacherContext.CURRENT_LECTURE = getCurrentLecture();
						refreshExampleSectionClient();
						refreshStudents();
					} catch (NaiteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
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
		
		if (toolkit != null) {
			toolkit.dispose();
		}
		super.dispose();
	}
	
	//--------------------------------------------------------------------------
	// execute job process
	//--------------------------------------------------------------------------
	/**
	 * 강의교재 업로드 및 등록 job
	 * @param fileSelected 등록할 강의교재 파일명
	 * @param lectureId 강의아이디
	 */
	private void createTextbook(final String fileSelected, final String lectureId) {
		//
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
        BusyUIIndicateJob job = new BusyUIIndicateJob(manager, "업로드 중...") {
			@Override
			public Object job() {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.createTextbook(fileSelected, lectureId);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
				return null;
			}
			
			@Override
			public void doAfter() {
				TeacherContext.CURRENT_LECTURE = getCurrentLecture();
				refreshBookSectionClient();
				refreshStudents();
			}
		};
		job.start();
	}
	
	/**
	 * 강의교재 다운로드 job
	 * @param fileSelected 다운로드될 대상 파일명
	 * @param textbook 교재객체
	 */
	private void downloadTextbook(final String fileSelected, final Textbook textbook) {
		//
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
        BusyUIIndicateJob job = new BusyUIIndicateJob(manager, "다운로드 중...") {
			@Override
			public Object job() {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.downloadTextbook(fileSelected, textbook);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		};
		job.start();
	}
	
	/**
	 * 강의교재 배포 job
	 * @param textbookId 교재 아이디
	 * @param studentEmails 배포 대상 수강생 이메일 목록
	 */
	private void publishTextbook(final String textbookId, final String lectureId, final List<String> studentEmails) {
		//
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
        BusyUIIndicateJob job = new BusyUIIndicateJob(manager, "강의교재 배포중...") {
			@Override
			public Object job() {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.publishTextbook(textbookId, lectureId, studentEmails);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		};
		job.start();
	}
	
	/**
	 * 강의교재 삭제 job
	 * @param textbook 삭제할 강의교재 객체
	 */
	private void deleteTextbook(final Textbook textbook) {
		//
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
        BusyUIIndicateJob job = new BusyUIIndicateJob(manager, "삭제중...") {
			@Override
			public Object job() {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.deleteTextbook(textbook);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
				return null;
			}
			
			@Override
			public void doAfter() {
				TeacherContext.CURRENT_LECTURE = getCurrentLecture();
				refreshBookSectionClient();
				refreshStudents();
			}
		};
		job.start();
	}
	
	/**
	 * 강의 실습예제 배포 job
	 * @param textbookId 교재 아이디
	 * @param studentEmails 배포 대상 수강생 이메일 목록
	 */
	private void publishExerciseProject(final String exerciseProjectId, final String lectureId, final List<String> studentEmails) {
		//
		IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
        BusyUIIndicateJob job = new BusyUIIndicateJob(manager, "실습예제 배포중...") {
			@Override
			public Object job() {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.publishExerciseProject(exerciseProjectId, lectureId, studentEmails);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		};
		job.start();
	}
}
