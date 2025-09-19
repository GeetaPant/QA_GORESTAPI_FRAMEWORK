package com.qa.gorestapi.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserAPIWithDynamicJsonFile extends  BaseTest{

	@Test
	public void createUserwithJsonFileTest() {
		String jsonFilePath = "C:\\Users\\g_nai\\eclipse-workspace\\QA_RestAPIFramework01\\src\\test\\resources\\jsons\\user.json";
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode usernode = mapper.readTree(Files.readAllBytes(Paths.get(jsonFilePath)));
			String uniqueEmail= StringUtil.getRandomEmailID();
			ObjectNode obj = ((ObjectNode)usernode);
			obj.put("email", uniqueEmail);
			
			//convert json node to json string
			String updatedJsonString = mapper.writeValueAsString(usernode);
			System.out.println("updated Json String ---"+usernode);
			
			Response response = restClient.post(BASE_URL, "/public/v2/users", updatedJsonString,null,null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(response.statusCode(), 201);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
