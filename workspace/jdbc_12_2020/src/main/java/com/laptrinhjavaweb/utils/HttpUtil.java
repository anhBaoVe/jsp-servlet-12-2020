package com.laptrinhjavaweb.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {
	private String value;

	public HttpUtil(String value) {
		this.value = value;
	}
	
	//map từ string sang các biến của class
	public <T> T toModel(Class<T> tClass) {
		try {
			return new ObjectMapper().readValue(value, tClass);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	//convert từ json sang String 
	public static HttpUtil of(BufferedReader reader) {
		StringBuilder sb = new  StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}

		return new HttpUtil(sb.toString());
	}
}
