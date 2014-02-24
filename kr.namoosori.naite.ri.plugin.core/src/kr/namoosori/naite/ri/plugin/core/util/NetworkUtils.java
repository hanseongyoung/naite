package kr.namoosori.naite.ri.plugin.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class NetworkUtils {
	//
	public static String getHostAddress() {
		//
		String address = null;
		try {
			InetAddress local = InetAddress.getLocalHost();
			address = local.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	public static String getDefaultGatewayAddress() {
		//
		String gateway = null;
		try {
			Process result = Runtime.getRuntime().exec("netstat -rn");
			BufferedReader output = new BufferedReader(new InputStreamReader(
					result.getInputStream()));
			
			String line = null;
			while ((line = output.readLine()) != null) {
				if (line.startsWith("네트워크") == true) {
					line = output.readLine();
					break;
				}
			}
			
			// line:          0.0.0.0          0.0.0.0         10.0.1.1        10.0.1.83     20
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			st.nextToken();
			gateway = st.nextToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gateway;
	}
}
