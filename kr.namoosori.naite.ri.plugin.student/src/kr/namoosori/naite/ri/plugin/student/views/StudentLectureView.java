package kr.namoosori.naite.ri.plugin.student.views;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.ExerciseProject;
import kr.namoosori.naite.ri.plugin.core.service.domain.Lecture;
import kr.namoosori.naite.ri.plugin.core.service.domain.Textbook;
import kr.namoosori.naite.ri.plugin.student.StudentPlugin;
import kr.namoosori.naite.ri.plugin.student.event.RefreshEventListener;
import kr.namoosori.naite.ri.plugin.student.event.TeacherEventHandler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
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

public class StudentLectureView extends ViewPart implements RefreshEventListener {
	//
	public static final String ID = StudentLectureView.class.getName();
	
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	private Lecture lecture;

	@Override
	public void createPartControl(Composite parent) {
		//
		toolkit = new FormToolkit(getSite().getShell().getDisplay());
		
		createForm(parent, "강사가 준비중입니다.");
		TeacherEventHandler.getInstance().addRefreshEventListener(this);
	}
	
	@Override
	public void refresh() {
		//
		System.out.println("***************** refresh...."+ID);
		lecture = getCurrentLecture();
		
		if (bookSection != null && !bookSection.isDisposed()) {
			bookSection.dispose();
		}
		if (exampleSection != null && !exampleSection.isDisposed()) {
			exampleSection.dispose();
		}
		
		if (lecture == null) {
			form.setText("진행중인 강의가 없습니다.");
			form.getParent().layout();
			return;
		}
		
		form.setText(lecture.getName());
		
		// section
		createBookSection(form);
		createExampleSection(form);
		
		form.getParent().layout();
		bookSection.getParent().layout();
		exampleSection.getParent().layout();
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
		TeacherEventHandler.getInstance().removeRefreshEventListener(this);
		toolkit.dispose();
		super.dispose();
	}

}
