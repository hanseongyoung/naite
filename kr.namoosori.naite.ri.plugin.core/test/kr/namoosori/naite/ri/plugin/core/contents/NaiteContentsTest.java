package kr.namoosori.naite.ri.plugin.core.contents;

import kr.namoosori.naite.ri.plugin.core.service.NaiteService;
import kr.namoosori.naite.ri.plugin.core.service.NaiteServiceFactory;

public class NaiteContentsTest {
	
	public static void main(String[] args) throws Exception {
//		TestContext.getInstance().setServerIp("10.0.1.45");
//		TestContext.getInstance().setServerPort(9000);
		TestContext.getInstance().setServerIp("playapp-syhan.rhcloud.com");
		TestContext.getInstance().setServerPort(80);
		
		//createNaiteUsers();
		//createStandardTextbook();
		createStandardProjects();
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
