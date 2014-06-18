package kr.namoosori.naite.ri.plugin.core.service.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StandardTextbook {
	//
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static List<StandardTextbook> createDomainsByJson(String jsonStr) {
		//
		Type listType = new TypeToken<ArrayList<StandardTextbook>>() {
		}.getType();
		List<StandardTextbook> textbooks = new Gson().fromJson(jsonStr, listType);
		return textbooks;
	}
}
