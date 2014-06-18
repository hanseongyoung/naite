package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NaiteUser {
	//
	private String id;
	private String email;
	private String name;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static List<NaiteUser> createDomainsByJson(String jsonStr) {
		Type listType = new TypeToken<ArrayList<NaiteUser>>() {
		}.getType();
		List<NaiteUser> users = new Gson().fromJson(jsonStr, listType);
		return users;
	}
	
	
}
