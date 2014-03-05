package kr.namoosori.naite.ri.plugin.student;

import java.net.URL;

import kr.namoosori.naite.ri.plugin.netclient.NetClientPlugin;

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
public class StudentPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.namoosori.naite.ri.plugin.student"; //$NON-NLS-1$

	// The shared instance
	private static StudentPlugin plugin;
	
	/**
	 * The constructor
	 */
	public StudentPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
//		StudentContext.init();
//		MulticastClientThread thread = MulticastClientThread.getInstance();
//		thread.start();
//		TeacherEventHandler teacher = TeacherEventHandler.getInstance();
//		teacher.start();
		
		NetClientPlugin.getDefault();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
//		MulticastClientThread thread = MulticastClientThread.getInstance();
//		thread.end();
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static StudentPlugin getDefault() {
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
