package kr.namoosori.naite.ri.plugin.netserver.facade;

public interface ServerEventListener {
	//
	public void clientIn(String clientId);
	public void clientOut(String clientId);
}
