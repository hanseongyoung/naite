package kr.namoosori.naite.ri.plugin.teacher.network;

import java.net.InetAddress;

public class InetAddressTest {
	
	public static void main(String[] args) throws Exception {
		InetAddress local = InetAddress.getLocalHost();
		System.out.println("InetAddress:" + local);
		System.out.println(" - hostAddress:"+local.getHostAddress());
		System.out.println(" - hostName:"+local.getHostName());
		System.out.println(" - canonicalHostName:"+local.getCanonicalHostName());
		System.out.println(" - address:");
		byte[] bytes = local.getAddress();
		for (int i = 0; i < bytes.length; i++) {
			System.out.println("["+bytes[i]+"]");
		}
	}

}
