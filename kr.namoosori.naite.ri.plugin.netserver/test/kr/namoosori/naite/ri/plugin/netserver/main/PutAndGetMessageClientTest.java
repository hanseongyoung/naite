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
		// 1. hong�� ���� �����Ͽ� Ŭ���̾�Ʈ�� ����� �ȴ�.
		System.out.println("[hong]");
		SocketClientStub clientHong = new SocketClientStub();
		String receive = clientHong.send("hong::GET:MESSAGE:");
		
		// 2. yumi�� hong���� ����� �޽����� ����Ѵ�.
		System.out.println("[yumi]");
		SocketClientStub clientYumi = new SocketClientStub();
		receive = clientYumi.send("yumi:hong:PUT:MESSAGE:hello");
		
		System.out.println("[yumi]");
		clientYumi = new SocketClientStub();
		receive = clientYumi.send("yumi:hong:PUT:MESSAGE:안녕!");
		
		// 3. hong�� �޽����� �����Ѵ�.
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		receive = clientHong.send("hong::GET:MESSAGE:");
		
		// 4. hong�� �� �޽��� ���ſ�û�� ������ ���ŵ� �޽����� ����
		System.out.println("[hong]");
		clientHong = new SocketClientStub();
		receive = clientHong.send("hong::GET:MESSAGE:");
	}
}
