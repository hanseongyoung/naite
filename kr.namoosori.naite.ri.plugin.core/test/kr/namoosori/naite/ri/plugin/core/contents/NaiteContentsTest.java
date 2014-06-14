package kr.namoosori.naite.ri.plugin.core.contents;

import java.util.HashMap;
import java.util.Map;

public class NaiteContentsTest {
	
	public static void main(String[] args) throws Exception {
		TestContext.getInstance().setServerIp("10.0.1.45");
		TestContext.getInstance().setServerPort(9000);
		
		NaiteContents naiteContents = new NaiteContents();
		
		Map<String, String> params = new HashMap<String, String>();
		//params.put("id", id);
		params.put("email", "tsong@nextree.co.kr");
		params.put("name", "송태국");
		params.put("password", "1234");
		naiteContents.doPost("students", params);
	}

}
