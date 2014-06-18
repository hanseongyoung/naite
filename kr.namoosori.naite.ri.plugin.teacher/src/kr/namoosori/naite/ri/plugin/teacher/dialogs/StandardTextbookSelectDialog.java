package kr.namoosori.naite.ri.plugin.teacher.dialogs;

import java.util.List;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;
import kr.namoosori.naite.ri.plugin.core.service.domain.StandardTextbook;
import kr.namoosori.naite.ri.plugin.teacher.TeacherContext;

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

public class StandardTextbookSelectDialog extends TitleAreaDialog {
	//
	private CheckboxTableViewer tableViewer;
	
	public StandardTextbookSelectDialog(Shell parentShell) {
		//
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		//
		setTitle("강의교재 선택");
		setMessage("준비된 강의교재를 해당 강의로 등록합니다.");
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
		tableViewer.setLabelProvider(new StandardProjectTableLabelProvider());
		//List<NaiteProject> projects = NaiteWorkspace.getInstance().getProjects();
		
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		
		List<StandardTextbook> textbooks = null;
		try {
			textbooks = service.findAllStandardTextbooks();
		} catch (NaiteException e) {
			e.printStackTrace();
		}
		
		tableViewer.setInput(textbooks);
	}

	@Override
	protected void okPressed() {
		//
		Object[] checkedElement = tableViewer.getCheckedElements();
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		for (Object element : checkedElement) {
			StandardTextbook textbook = (StandardTextbook) element;
			try {
				service.createTextbookByStandard(textbook.getId(), TeacherContext.CURRENT_LECTURE.getId());
			} catch (NaiteException e) {
				e.printStackTrace();
			}
		}
		
		super.okPressed();
	}

	class StandardProjectTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		//
		WorkbenchLabelProvider provider = new WorkbenchLabelProvider();
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			//
			if (element instanceof StandardTextbook) {
				StandardTextbook textbook = (StandardTextbook) element;
				//return provider.getImage(project.getResource());
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			//
			if (element instanceof StandardTextbook) {
				StandardTextbook textbook = (StandardTextbook) element;
				return textbook.getName();
			}
			return "";
		}
		
	}

}
