package kr.namoosori.naite.ri.plugin.core.project;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;

public class NaiteWorkspace {
	//
	public static File getRootLocationAsFile() {
		//
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
	}
	
	public static String getRootLocation() {
		//
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	}
}
