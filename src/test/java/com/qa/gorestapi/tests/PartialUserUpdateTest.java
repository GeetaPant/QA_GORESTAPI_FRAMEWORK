package com.qa.gorestapi.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUserUpdateTest extends BaseTest
{
	@DataProvider
	public Object[][] getUserData() {
	return new Object[][] {
		{"Vedant", "male", "active", "Vedant Pant", "inactive"},
		{"Vyom", "male", "active", "Vyom Pro", "inactive"}	
	};
}
	@Test(dataProvider = "getUserData")
	public void updateUserTest(String name, String gender, String status, String updatedName, String updatedStatus)
	{
		//create a fresh user
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
			Response responseGet = restClient.get(BASE_URL, GOREST_ENDPOINT+id, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGet.getStatusCode(), 200);
			Assert.assertEquals(responseGet.jsonPath().getString("id"), id);
			Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
			
			// UPDATE same created user
				
				user.setName(updatedName);
				user.setEmail(StringUtil.getRandomEmailID());
				user.setStatus(updatedStatus);
				
				Response responsePatch = restClient.patch(BASE_URL,GOREST_ENDPOINT+id, user, null, null,  AuthTypes.BEARER_TOKEN, ContentType.JSON);
				Assert.assertEquals(responsePatch.getStatusCode(), 200);
				Assert.assertEquals(responsePatch.jsonPath().getString("id"), id);
				Assert.assertEquals(responsePatch.jsonPath().getString("name"), user.getName());
				Assert.assertEquals(responsePatch.jsonPath().getString("email"), user.getEmail());
				Assert.assertEquals(responsePatch.jsonPath().getString("status"), user.getStatus());
				
	}
}
