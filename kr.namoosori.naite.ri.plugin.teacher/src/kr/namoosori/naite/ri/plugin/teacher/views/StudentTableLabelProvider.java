package kr.namoosori.naite.ri.plugin.teacher.views;

import kr.namoosori.naite.ri.plugin.core.service.domain.Student;
import kr.namoosori.naite.ri.plugin.teacher.TeacherPlugin;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public class StudentTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		//
		TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_USER);
		if (element instanceof Student) {
			Student student = (Student) element;
			if (columnIndex == 0) {
				if (student.getId() != null && student.getId().length() > 0) {
					return TeacherPlugin.getDefault().getImageRegistry().get(TeacherPlugin.IMG_USER);
				}
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		//
		if (element instanceof Student) {
			Student student = (Student) element;
			if (columnIndex == 0) {
				return student.getName();
			} else if (columnIndex == 1) {
				return student.getEmail();
			}
		}
		return null;
	}

}
