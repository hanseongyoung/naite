package kr.namoosori.naite.ri.plugin.netclient.facade;

public abstract class SecuredServerStateListener implements ServerStateListener {
	//
	private SecuredChecker checker;
	
	public SecuredServerStateListener(SecuredChecker checker) {
		//
		this.checker = checker;
	}

	@Override
	public void serverStateChanged(boolean serverState) {
		//
		if (!serverState) {
			serverStateChangedWithChecked(false);
		} else if (checker.check()) { // if serverState is false, don't invoke check().
			serverStateChangedWithChecked(serverState);
		} else {
			checker.notPermitted();
		}
	}

	public abstract void serverStateChangedWithChecked(boolean serverState);

}
