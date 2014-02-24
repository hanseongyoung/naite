package kr.namoosori.naite.ri.plugin.teacher.views;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
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
	
	private Lecture lecture;

	public TeacherLectureView() {
	}
	
	class TmpAction extends Action {
		public TmpAction() {
			setId("tmpAction");
			setImageDescriptor(TeacherPlugin.getDefault().getImageRegistry().getDescriptor(TeacherPlugin.IMG_HELP_TOPIC));
			setToolTipText("테스트");
		}

		@Override
		public void run() {
			System.out.println("테스트");
		}
		
	}

	@Override
	public void createPartControl(Composite parent) {
		//
		lecture = getCurrentLecture();
		
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		// scrolled form
		form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		form.setText(lecture.getName());
		toolkit.decorateFormHeading(form.getForm());
		
		ToolBarManager toolbarManager = (ToolBarManager) form.getToolBarManager();
		toolbarManager.add(new TmpAction());
		toolbarManager.update(true);
		
		// section
		createBookSection(form);
		createExampleSection(form);
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
		bookSection.setTextClient(createBookSectionToolbar(parentForm, bookSection));
		
		Composite client = createBookSectionClient(bookSection);
		bookSection.setClient(client);
	}

	private Control createBookSectionToolbar(ScrolledForm parentForm,
			Section section) {
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
					service.createTextbook(fileSelected, lecture.getId());
					lecture = getCurrentLecture();
					refreshBookSectionClient();
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
		
		for (Textbook textbook : lecture.getTextbooks()) {
			createTextbookLink(composite, textbook);
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
                String fileSelected = fileDialog.open();
                if (fileSelected == null || fileSelected.length() <= 0) return;
				
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.downloadTextbook(fileSelected, textbook);
				} catch (NaiteException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void createExampleSection(ScrolledForm parentForm) {
		//
		Section section = toolkit.createSection(parentForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		//toolkit.createCompositeSeparator(section);
		section.setText("실습예제");
		section.setDescription("실습예제를 프로젝트 환경에 설치합니다.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setExpanded(true);
		
		Composite client = createExampleSectionClient(section);
		section.setClient(client);
	}

	private Composite createExampleSectionClient(Section section) {
		//
		Composite composite = toolkit.createComposite(section);
		composite.setLayout(new GridLayout(2, false));
		
		for (ExerciseProject exerciseProject : lecture.getExerciseProjects()) {
			createExerciseProjectLink(composite, exerciseProject);
		}
		
		return composite;
	}

	private void createExerciseProjectLink(Composite composite,
			final ExerciseProject exerciseProject) {
		//
		ImageHyperlink image = toolkit.createImageHyperlink(composite, SWT.NONE);
		image.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT));
		
		Hyperlink link = toolkit.createHyperlink(composite, exerciseProject.getProjectName(), SWT.WRAP);
	
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.addHyperlinkListener(new HyperlinkAdapter(){
			@Override
			public void linkActivated(HyperlinkEvent e) {
				//
				NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
				try {
					service.projectCreate(exerciseProject);
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
		toolkit.dispose();
		super.dispose();
	}

}
