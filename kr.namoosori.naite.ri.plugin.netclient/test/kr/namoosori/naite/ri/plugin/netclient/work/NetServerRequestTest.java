package kr.namoosori.naite.ri.plugin.netclient.work;

public class NetServerRequestTest {
	public static void main(String[] args) {
		NetServerRequest req = NetServerRequest.asMessageRequest("yumi", "hong", "안녕");
		System.out.println(req.toMessageBlock());
	}
}
