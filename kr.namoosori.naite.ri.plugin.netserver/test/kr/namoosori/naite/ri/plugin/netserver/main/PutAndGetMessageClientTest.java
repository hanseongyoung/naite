package kr.namoosori.naite.ri.plugin.netserver.main;

//request message format  --> [sender ID]:[receiver ID]:[Method]:[Type]:[Message]
//response message format --> [sender ID]:[Type]:[Message]
//put message --> 'yumi:hong:PUT:MESSAGE:hello'    --> ok
//get message --> 'hong::GET:MESSAGE:'             --> yumi:MESSAGE:hello  --> ok
//put control --> 'yumi:hong:PUT:CONTROL:stop'     --> ok
//get control --> 'hong::GET:CONTROL:'             --> yumi:CONTROL:stop   --> ok
public class PutAndGetMessageClientTest {
	public static void main(String[] args) {
		//
		// 1. hong이 접속한다.
		System.out.println("[hong]");
		SocketClientStub clientHong = new SocketClientStub();
		clientHong.send("hong::GET:MESSAGE:");
		
		// 2. yumi가 hong에게 메시지를 전달한다.
		System.out.println("[yumi]");
		SocketClientStub clientYumi = new SocketClientStub();
		clientYumi.send("yumi:hong:PUT:MESSAGE:hello");
		
		System.out.println("[yumi]");
		clientYumi = new SocketClientStub();
		clientYumi.send("yumi:hong:PUT:MESSAGE:안녕!");
		
		// 3. hong이 메시지를 수신한다.
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		clientHong.send("hong::GET:MESSAGE:");
		
		// 4. hong이 메시지를 수신하나 수신된 메시지가 없다.
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		clientHong.send("hong::GET:MESSAGE:");
	}
}
