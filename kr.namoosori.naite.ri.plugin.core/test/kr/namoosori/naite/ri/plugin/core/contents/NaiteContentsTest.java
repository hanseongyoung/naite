package kr.namoosori.naite.ri.plugin.core.contents;

import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;

public class NaiteContentsTest {
	
	public static void main(String[] args) throws Exception {
//		TestContext.getInstance().setServerIp("10.0.1.45");
//		TestContext.getInstance().setServerPort(9000);
		TestContext.getInstance().setServerIp("playapp-syhan.rhcloud.com");
		TestContext.getInstance().setServerPort(80);
		
		createTestUser();
		//createNodejsUsers();
		//createNaiteUsers();
		//createStandardTextbook();
		//createStandardProjects();
	}

	private static void createTestUser() throws Exception {
		TestContext.getInstance().setServerIp("10.0.1.45");
		TestContext.getInstance().setServerPort(8888);
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createNaiteUser("한성영", "syhan@nextree.co.kr", "1234");
	}

	private static void createNaiteUsers() throws Exception {
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createNaiteUser("강형구", "kang@nextree.co.kr", "1234");
		service.createNaiteUser("이기왕", "lee@nextree.co.kr", "1234");
		service.createNaiteUser("송태국", "tsong@nextree.co.kr", "1234");
		service.createNaiteUser("이충헌", "chlee@nextree.co.kr", "1234");
		service.createNaiteUser("최인혜", "choi@nextree.co.kr", "1234");
		service.createNaiteUser("손문일", "son@nextree.co.kr", "1234");
	}
	
	private static void createNodejsUsers() throws Exception {
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createNaiteUser("김민성", "kaver99@gmail.com", "1234");
		service.createNaiteUser("김범주", "4xjida@naver.com", "1234");
		service.createNaiteUser("김병태", "byeongtae.kim@sycns.co.kr", "1234");
		service.createNaiteUser("김선미", "codus51251@naver.com", "1234");
		service.createNaiteUser("김성미", "sungmiya@gmail.com", "1234");
		service.createNaiteUser("김장우", "jwheat21c@hanmail.net", "1234");
		service.createNaiteUser("김태윤", "kimtayoon@gmail.com", "1234");
		service.createNaiteUser("노현주", "cutty1130@nate.com", "1234");
		service.createNaiteUser("박경희", "pkh731@nate.com", "1234");
		service.createNaiteUser("박새미", "zkdlffpem@naver.com", "1234");
		service.createNaiteUser("박성훈", "endfirst@gmail.com", "1234");
		service.createNaiteUser("박소영", "joynouchi@naver.com", "1234");
		service.createNaiteUser("박태훈", "oldamigo9@gmail.com", "1234");
		service.createNaiteUser("송준현", "hyun8510@empas.com", "1234");
		service.createNaiteUser("우지환", "wjh0907@mnwise.com", "1234");
		service.createNaiteUser("정규필", "darkstalker@nate.com", "1234");
		service.createNaiteUser("조성재", "sjjoe74@naver.com", "1234");
		service.createNaiteUser("최강남", "philastron@nate.com", "1234");
		service.createNaiteUser("황성진", "hssj4543@naver.com", "1234");
	}

	private static void createStandardTextbook() throws Exception {
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-Java-Fundamental(ver 1.44).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-Java-IO(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-JavaScript(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-JDBC(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-JQuery-Fundamental(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-MongoDB(ver 0.1).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-MyBatis(ver1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-NodeJS(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-SWT_JFace(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-RCP 프로그래밍 기초(v1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-MariaDB(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-Servlet&JSP(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-SpringFramework(ver 1.0).pdf");
		service.createStandardTextbook("D:\\naite_test\\test_contents\\textbook\\Namoosori-SQL-Fundamental(ver 1.0).pdf");
	}

	private static void createStandardProjects() throws Exception {
		NaiteService service = NaiteServiceFactory.getInstance().getNaiteService();
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.zip", "namoo.club.dom2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.bl1.zip", "namoo.club.dom2.bl1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.da1.zip", "namoo.club.dom2.da1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.da2.zip", "namoo.club.dom2.da2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.da3.zip", "namoo.club.dom2.da3");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.da5.zip", "namoo.club.dom2.da5");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.pr1.zip", "namoo.club.dom2.pr1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.pr2.zip", "namoo.club.dom2.pr2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.sp1.zip", "namoo.club.dom2.sp1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.sp2.zip", "namoo.club.dom2.sp2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom2.ui1.zip", "namoo.club.dom2.ui1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.zip", "namoo.club.dom3");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.bl1.zip", "namoo.club.dom3.bl1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.bl2.zip", "namoo.club.dom3.bl2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da1.zip", "namoo.club.dom3.da1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da2.zip", "namoo.club.dom3.da2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da3.zip", "namoo.club.dom3.da3");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da4.zip", "namoo.club.dom3.da4");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da5.zip", "namoo.club.dom3.da5");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da6.zip", "namoo.club.dom3.da6");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.da7.zip", "namoo.club.dom3.da7");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.pr1.zip", "namoo.club.dom3.pr1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.pr2.zip", "namoo.club.dom3.pr2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.pr3.zip", "namoo.club.dom3.pr3");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.sp1.zip", "namoo.club.dom3.sp1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.sp2.zip", "namoo.club.dom3.sp2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.ui1.zip", "namoo.club.dom3.ui1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.uipr1.zip", "namoo.club.dom3.uipr1");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.uipr2.zip", "namoo.club.dom3.uipr2");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.dom3.uipr3.zip", "namoo.club.dom3.uipr3");
		service.createStandardProject("D:\\naite_test\\test_contents\\project\\namoo.club.util.zip", "namoo.club.util");
	}

	
}
