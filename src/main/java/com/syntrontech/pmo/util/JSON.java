package com.syntrontech.pmo.util;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JSON {
	public static String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T> Optional<T> toObject(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return Optional.ofNullable(mapper.readValue(json, clazz));
		} catch (IOException e) {
			System.err.println("to "+ clazz +", parse json error: "+json);
			e.printStackTrace();
			return Optional.ofNullable(null);
		}
	}
	
	public static <E> List<E> toObjects(String json, Class<E> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CollectionType _type = mapper.getTypeFactory().constructCollectionType(List.class, type);
			return mapper.readValue(json, _type);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
