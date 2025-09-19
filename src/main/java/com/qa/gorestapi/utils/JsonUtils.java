package com.qa.gorestapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonUtils {

	private static ObjectMapper objectmapper = new ObjectMapper();

	public static <T> T deserialize(Response response, Class <T> targetClass ) {
	 try {
		return	objectmapper.readValue(response.getBody().asString(),targetClass);
	} catch (Exception e) {
			throw new RuntimeException("Deserialization failed" +targetClass.getName());
	} 
}
}
