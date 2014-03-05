package kr.namoosori.naite.ri.plugin.netserver;

import kr.namoosori.naite.ri.plugin.netserver.main.NaiteNetServer;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NetServerPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.namoosori.naite.ri.plugin.netserver"; //$NON-NLS-1$

	// The shared instance
	private static NetServerPlugin plugin;
	
	/**
	 * The constructor
	 */
	public NetServerPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		NaiteNetServer.getInstance().start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		NaiteNetServer.getInstance().stop();
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NetServerPlugin getDefault() {
		return plugin;
	}

}
