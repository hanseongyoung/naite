package kr.namoosori.naite.ri.plugin.teacher.views;

import kr.namoosori.naite.ri.plugin.core.service.domain.student.StudentProject;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public class StudentDetailTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		//
		if (element instanceof StudentProject) {
			StudentProject project = (StudentProject) element;
			if (columnIndex == 0) {
				return TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_PRJ);
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		//
		if (element instanceof StudentProject) {
			StudentProject project = (StudentProject) element;
			if (columnIndex == 0) {
				return project.getProjectName();
			} else if (columnIndex == 1) {
				return "";
			}
		}
		return null;
	}

}
