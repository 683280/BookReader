package com.carljay.cjlibrary.uitls;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;

public class JsonUtils {

	private static Gson gson = new Gson();
	public static String mapToJson(Map map) {
		String jsonStr = gson.toJson(map);
		return jsonStr;
	}
	public static String objectToJson(Object map) {
		String jsonStr = gson.toJson(map);
		return jsonStr;
	}

	public static <T> T jsonToBean(Class<T> c,String json){
		try {
			return gson.fromJson(json, c);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static <T> T jsonToBean(Type c, String json){
		try {
			return gson.fromJson(json, c);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
