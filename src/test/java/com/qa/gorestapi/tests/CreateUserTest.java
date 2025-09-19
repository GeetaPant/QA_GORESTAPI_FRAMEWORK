package com.qa.gorestapi.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest
{					
	@Test
	public void createUserTest() {
		
		User user = new User(null, "Geeta",StringUtil.getRandomEmailID(),"female", "active");			
		Response response = restClient.post(BASE_URL,GOREST_ENDPOINT,user, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
	@Test
	public void createUserWithBuilderTest() {
		
	User user = User.builder()
			.name("Vyom")
			.email(StringUtil.getRandomEmailID())
			.gender("male")
			.status("active")
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
	}
	
	@Test
	public void createUserWithJSONFile() {
		File userJsonFile = new File(".\\src\\test\\resources\\jsons\\user.json");
		Response response = restClient.post(BASE_URL,"/public/v2/users",userJsonFile, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
}	
