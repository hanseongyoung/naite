package kr.namoosori.naite.ri.plugin.teacher.views;

import java.io.IOException;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.job.BusyUIIndicateJob;
import kr.namoosori.naite.ri.plugin.core.project.NaiteProject;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;
import kr.namoosori.naite.ri.plugin.teacher.dialogs.TeacherProjectUploadDialog;
import kr.namoosori.naite.ri.plugin.teacher.network.MulticastServerThread;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
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

public class TeacherLectureView extends ViewPart {
	//
	public static final String ID = TeacherLectureView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	public TeacherLectureView() {
	}
	
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
					service.createLecture(lectureTitle);
					refresh();
					refreshStudents();
				} catch (NaiteException e) {
					e.printStackTrace();
				}
			}
		}
		
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
	}
	
	public void refreshStudents() {
		//
		try {
			MulticastServerThread.getInstance().setServeString("cmd:refresh");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		//
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
		toolbarManager.add(new AddLectureAction());
		toolbarManager.update(true);
	}
	
	private Lecture getCurrentLecture() {
		//
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		try {
			return service.getCurrentLecture();
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
		ToolItem item = new ToolItem(toolbar, SWT.PUSH);
		item.setToolTipText("강의교재 등록");
		item.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_HELP_TOPIC));
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
                fileDialog.setFilterExtensions(new String[] { "*.pdf", "*.*" });
                fileDialog.setFilterNames(new String[] { "PDF 파일", "All Files" });
                fileDialog.setText("등록할 강의교재 선택");
                String fileSelected = fileDialog.open();
                if (fileSelected == null || fileSelected.length() <= 0) return;
                NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.createTextbook(fileSelected, TeacherContext.CURRENT_LECTURE.getId());
					TeacherContext.CURRENT_LECTURE = getCurrentLecture();
					refreshBookSectionClient();
					refreshStudents();
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
			}
		});
		return toolbar;
	}

	private Composite createBookSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(2, false));
		
		if (TeacherContext.CURRENT_LECTURE != null) {
			for (Textbook textbook : TeacherContext.CURRENT_LECTURE.getTextbooks()) {
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
                
                IStatusLineManager manager = getViewSite().getActionBars().getStatusLineManager();
                BusyUIIndicateJob job = new BusyUIIndicateJob(manager) {
					@Override
					public Object job() {
						//
						NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
						try {
							service.downloadTextbook(fileSelected, textbook);
						} catch (NaiteException e1) {
							e1.printStackTrace();
						}
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
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
		ToolItem item = new ToolItem(toolbar, SWT.PUSH);
		item.setToolTipText("실습예제 등록");
		item.setImage(TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_HELP_TOPIC));
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
		composite.setLayout(new GridLayout(2, false));
		
		if (TeacherContext.CURRENT_LECTURE != null) {
			for (ExerciseProject exerciseProject : TeacherContext.CURRENT_LECTURE.getExerciseProjects()) {
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
		if (toolkit != null) {
			toolkit.dispose();
		}
		super.dispose();
	}

}
