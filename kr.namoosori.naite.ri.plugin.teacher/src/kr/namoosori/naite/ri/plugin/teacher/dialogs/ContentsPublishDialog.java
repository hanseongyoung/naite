package kr.namoosori.naite.ri.plugin.teacher.dialogs;

import java.util.ArrayList;
import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ContentsPublishDialog extends TitleAreaDialog {
	//
	private CheckboxTreeViewer treeViewer;
	
	private List<String> checkedStudentEmails;

	public ContentsPublishDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("강의자료 배포");
		setMessage("강의자료를 선택한 수강생에게 배포합니다.");
		Composite composite = (Composite) super.createDialogArea(parent);
		createForm(composite);
		return composite;
	}

	private void createForm(Composite parent) {
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createStudentListTree(composite);
	}

	private void createStudentListTree(Composite parent) {
		//
		treeViewer = new CheckboxTreeViewer(parent);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		treeViewer.setContentProvider(new StudentTreeContentProvider());
		treeViewer.setLabelProvider(new StudentLabelProvider());
		
		List<Student> students = getStudents();
		treeViewer.setInput(students);
	}
	
	
	private List<Student> getStudents() {
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		try {
			return service.findStudents(TeacherContext.CURRENT_LECTURE.getId());
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void okPressed() {
		Object[] checkedElements = treeViewer.getCheckedElements();
		this.checkedStudentEmails = new ArrayList<String>();
		for (Object element : checkedElements) {
			Student student = (Student)element;
			this.checkedStudentEmails.add(student.getEmail());
		}
		
		super.okPressed();
	}

	public List<String> getCheckedStudentEmails() {
		return checkedStudentEmails;
	}


	/**
	 * StudentTreeContentProvider
	 * @author syhan
	 */
	class StudentTreeContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object[] getElements(Object inputElement) {
			// TODO Auto-generated method stub
			return ((List<Student>)inputElement).toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	/**
	 * @author syhan
	 */
	class StudentLabelProvider implements ILabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_USER);
		}

		@Override
		public String getText(Object element) {
			//
			Student student = (Student) element;
			return student.getName() + "(" + student.getEmail() + ")";
		}
		
	}

}
