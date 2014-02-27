package kr.namoosori.naite.ri.plugin.teacher.dialogs;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.project.NaiteProject;
import kr.namoosori.naite.ri.plugin.core.project.NaiteWorkspace;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class TeacherProjectUploadDialog extends TitleAreaDialog {
	//
	private CheckboxTableViewer tableViewer;
	
	public TeacherProjectUploadDialog(Shell parentShell) {
		//
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		//
		setTitle("프로젝트 업로드");
		setMessage("선택한 프로젝트를 서버로 업로드합니다.");
		Composite composite = (Composite) super.createDialogArea(parent);
		createForm(composite);
		return composite;
	}
	
	private void createForm(Composite parent) {
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createProjectListTable(composite);
	}

	private void createProjectListTable(Composite parent) {
		//
		tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new ProjectTableLabelProvider());
		List<NaiteProject> projects = NaiteWorkspace.getInstance().getProjects();
		tableViewer.setInput(projects);
	}

	@Override
	protected void okPressed() {
		//
		Object[] checkedElement = tableViewer.getCheckedElements();
		for (Object element : checkedElement) {
			NaiteProject project = (NaiteProject) element;
			System.out.println("checked..."+project.getName());
			try {
				project.export();
			} catch (NaiteException e) {
				e.printStackTrace();
			}
		}
		
		super.okPressed();
	}

	class ProjectTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		//
		WorkbenchLabelProvider provider = new WorkbenchLabelProvider();
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			//
			if (element instanceof NaiteProject) {
				NaiteProject project = (NaiteProject) element;
				return provider.getImage(project.getResource());
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			//
			if (element instanceof NaiteProject) {
				NaiteProject project = (NaiteProject) element;
				return project.getName();
			}
			return "";
		}
		
	}

}
