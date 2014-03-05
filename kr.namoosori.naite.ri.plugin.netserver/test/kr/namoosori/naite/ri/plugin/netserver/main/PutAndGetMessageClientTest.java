package kr.namoosori.naite.ri.plugin.netserver.main;

//message format --> [발신자ID]:[수신자ID]:[Method]:[Type]:[Message]
//put message --> 'yumi:hong:PUT:MESSAGE:hello'    --> ok
//get message --> 'hong::GET:MESSAGE:'             --> yumi:MESSAGE:hello  --> ok
//put control --> 'yumi:hong:PUT:CONTROL:stop'     --> ok
//get control --> 'hong::GET:CONTROL:'             --> yumi:CONTROL:stop   --> ok
public class PutAndGetMessageClientTest {
	public static void main(String[] args) {
		//
		// 1. hong이 먼저 접속하여 클라이언트로 등록이 된다.
		System.out.println("[hong]");
		SocketClientStub clientHong = new SocketClientStub();
		String receive = clientHong.send("hong::GET:MESSAGE:");
		
		// 2. yumi가 hong에게 전달할 메시지를 전송한다.
		System.out.println("[yumi]");
		SocketClientStub clientYumi = new SocketClientStub();
		receive = clientYumi.send("yumi:hong:PUT:MESSAGE:hello");
		
		System.out.println("[yumi]");
		clientYumi = new SocketClientStub();
		receive = clientYumi.send("yumi:hong:PUT:MESSAGE:안녕!");
		
		// 3. hong이 메시지를 수신한다.
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		receive = clientHong.send("hong::GET:MESSAGE:");
		
		// 4. hong이 또 메시지 수신요청을 보내나 수신된 메시지가 없음
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		receive = clientHong.send("hong::GET:MESSAGE:");
	}
}
