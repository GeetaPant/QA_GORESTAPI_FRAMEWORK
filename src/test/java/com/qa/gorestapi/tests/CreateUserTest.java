package com.qa.gorestapi.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest
{	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Vedant", "male", "active"},
			{"Deepa", "female", "active"},
			{"Alia", "female", "active"},
			{"Vyom", "male", "active"}
			
		};
	}
	
	@Test(dataProvider = "getUserData")
	public void createUserTest(String name, String gender, String status) {
		
		User user = new User(null, name,StringUtil.getRandomEmailID(),gender, status);			
		Response response = restClient.post(BASE_URL,GOREST_ENDPOINT,user, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
	@Test(dataProvider = "getUserData")
	public void createUserWithBuilderTest(String name, String gender, String status) {
		
	User user = User.builder()
			.name(name)
			.email(StringUtil.getRandomEmailID())
			.gender(gender)
			.status(status)
			.build();
		Response responsePost = restClient.post(BASE_URL,GOREST_ENDPOINT,user, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePost.getStatusCode(), 201);
		String id = responsePost.jsonPath().getString("id");
		System.out.println("Created User ID is --"+ id);

		// GET the created user
		Response responseGet = restClient.get(BASE_URL,GOREST_ENDPOINT+id, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), id);
		Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(responseGet.jsonPath().getString("email"), user.getEmail());
		
	}
	
	@Test
	public void createUserWithJSONFile() {
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
			
			Response response = restClient.post(BASE_URL, GOREST_ENDPOINT, updatedJsonString,null,null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(response.statusCode(), 201);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
}	
