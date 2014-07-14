package kr.namoosori.naite.ri.plugin.core.contents;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GsonTest {
	public static void main(String[] args) {
		List<String> emails = new ArrayList<>();
		emails.add("aaa@aaa.com");
		emails.add("bbb@bbb.com");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(emails));
	}
}
