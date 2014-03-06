package kr.namoosori.naite.ri.plugin.netclient.facade.message;

import java.util.ArrayList;
import java.util.List;

public class NameValue {
	//
	private static final String DELIM_EQUAL = "=";
	private static final String DELIM_AND = "&";
	
	private String name;
	private String value;
	
	//--------------------------------------------------------------------------
	// 
	public NameValue(String name, String value) {
		//
		this.name = name;
		this.value = value;
	}
	
	private NameValue(String nameValueString) {
		//
		String[] strArr = nameValueString.split(DELIM_EQUAL);
		this.name = strArr[0];
		this.value = strArr[1];
	}

	//--------------------------------------------------------------------------
	//
	public String toMessageBlock() {
		//
		return name + DELIM_EQUAL + value;
	}
	
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("name:" + name); 
		builder.append(", value:" + value); 
		
		return builder.toString(); 
	}

	public boolean equalsName(String name) {
		// 
		if (this.name == null) {
			return false; 
		}
		
		if (this.name.equals(name)) {
			return true; 
		}
		
		return false; 
	}

	public static String getValue(String name, List<NameValue> pairs) {
		//
		for(NameValue nameValue : pairs) {
			if (nameValue.name().equals(name)) {
				return nameValue.value();
			}
		}

		return null;
	}

	public static String getValueStr(String name, List<NameValue> pairs) {
		//
		for(NameValue nameValue : pairs) {
			if (nameValue.name().equals(name)) {
				return (String)nameValue.value();
			}
		}

		return null;
	}

	public static boolean hasName(String name, List<NameValue> pairs) {
		//
		for(NameValue nameValue : pairs) {
			if (nameValue.name().equals(name)) {
				return true;
			}
		}

		return false;
	}

	public String name() {
		return name;
	}

	public String value() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setValue(String value){
		this.value = value;
	}

	public static List<NameValue> createList(String nameValuesString) {
		//
		List<NameValue> nameValues = new ArrayList<NameValue>();
		if (nameValuesString == null || nameValuesString.length() <= 0) {
			return nameValues;
		}
		
		String[] strArr = nameValuesString.split(DELIM_AND);
		for (String str : strArr) {
			NameValue nameValue = create(str);
			if (nameValue != null) {
				nameValues.add(nameValue);
			}
		}
		
		return nameValues;
	}
	
	public static NameValue create(String nameValueString) {
		//
		if (nameValueString == null || nameValueString.length() <= 0) {
			return null;
		}
		
		if (nameValueString.indexOf(DELIM_EQUAL) < 0) {
			return null;
		}
		return new NameValue(nameValueString);
	}

}
