package kr.namoosori.naite.ri.plugin.teacher;

import java.net.URL;

import kr.namoosori.naite.ri.plugin.core.project.NaiteWorkspace;
import kr.namoosori.naite.ri.plugin.core.util.NaiteFileUtils;
import kr.namoosori.naite.ri.plugin.teacher.server.NaiteServer;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TeacherPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.namoosori.naite.ri.plugin.teacher"; //$NON-NLS-1$

	// The shared instance
	private static TeacherPlugin plugin;
	
	/**
	 * The constructor
	 */
	public TeacherPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		NaiteServer server = NaiteServer.getInstance();
		server.setResourceBase(getTeacherWorkspace());
		//server.registerServlet(TestServlet.class, "/date");
		server.start();
	}
	
	private String getTeacherWorkspace() {
		//
		String workspacePath = NaiteWorkspace.getRootLocation();
		String teacherWorkspace = workspacePath + TeacherConstants.TEACHER_WORKSPACE;
		NaiteFileUtils.checkDir(teacherWorkspace);
		return teacherWorkspace;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		NaiteServer.getInstance().stop();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TeacherPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static final String IMG_HELP_TOPIC = "helpTopic"; //$NON-NLS-1$
	
	protected void initializeImageRegistry(ImageRegistry registry) {
		registerImage(registry, IMG_HELP_TOPIC, "help_topic.gif"); //$NON-NLS-1$
	}

	private void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path("icons/" + fileName); //$NON-NLS-1$
			URL url = FileLocator.find(getBundle(), path, null);
			if (url!=null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}
}
