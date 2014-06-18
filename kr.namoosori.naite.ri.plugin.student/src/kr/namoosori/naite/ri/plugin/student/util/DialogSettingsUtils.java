package kr.namoosori.naite.ri.plugin.student.util;

import kr.namoosori.naite.ri.plugin.student.StudentPlugin;

import org.eclipse.jface.dialogs.IDialogSettings;

public class DialogSettingsUtils {
	//
	public static final String SECTION_STUDENT = "student";
	
	public static final String KEY_DOMAIN = "domain";
	public static final String KEY_PORT = "port";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASS = "pass";
	
	public static void put(String sectionName, String key, String value) {
		//
		IDialogSettings section = getSection(sectionName);
		section.put(key, value);
	}
	
	public static String get(String sectionName, String key) {
		//
		IDialogSettings section = getSection(sectionName);
		return section.get(key);
	}

	private static IDialogSettings getSection(String sectionName) {
		//
		StudentPlugin plugin = StudentPlugin.getDefault();
		IDialogSettings ds = plugin.getDialogSettings();
        IDialogSettings section = ds.getSection(sectionName);
        if (section == null) {
            section = ds.addNewSection(sectionName);
        }
        return section;
	}
}
