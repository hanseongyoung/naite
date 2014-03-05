package kr.namoosori.naite.ri.plugin.netclient;

import kr.namoosori.naite.ri.plugin.netclient.main.NaiteNetClient;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NetClientPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.namoosori.naite.ri.plugin.netclient"; //$NON-NLS-1$

	// The shared instance
	private static NetClientPlugin plugin;
	
	/**
	 * The constructor
	 */
	public NetClientPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		NaiteNetClient.getInstance().start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		NaiteNetClient.getInstance().stop();
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NetClientPlugin getDefault() {
		return plugin;
	}

}
