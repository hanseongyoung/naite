package kr.namoosori.naite.ri.plugin.teacher.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GatewayTest {
	public static void main(String[] args) throws IOException {
		//
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
		System.out.println("line:"+line);

		StringTokenizer st = new StringTokenizer(line);
		st.nextToken();
		st.nextToken();
		String gateway = st.nextToken();
		
		System.out.println("gateway["+gateway+"]");
	}

}
