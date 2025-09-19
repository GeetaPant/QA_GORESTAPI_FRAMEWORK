package com.qa.gorestapi.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUserUpdateTest extends BaseTest
{
	@Test
	public void updateUserTest()
	{
		User user = User.builder()
				.name("Vyom Pant")
				.email(StringUtil.getRandomEmailID())
				.gender("male")
				.status("active")
				.build();
		
			Response responsePost = restClient.post(BASE_URL,"/public/v2/users",user, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responsePost.getStatusCode(), 201);
			String id = responsePost.jsonPath().getString("id");
			System.out.println("Created User ID is --"+ id);

			// GET the created user
			Response responseGet = restClient.get(BASE_URL,"/public/v2/users/"+id, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGet.getStatusCode(), 200);
			Assert.assertEquals(responseGet.jsonPath().getString("id"), id);
			Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
			
			// UPDATE same created user
			
				user.setEmail(StringUtil.getRandomEmailID());
				user.setStatus("inactive");
				
				Response responsePatch = restClient.patch(BASE_URL,"/public/v2/users/"+id, user, null, null,  AuthTypes.BEARER_TOKEN, ContentType.JSON);
				Assert.assertEquals(responsePatch.getStatusCode(), 200);
				Assert.assertEquals(responsePatch.jsonPath().getString("id"), id);
				Assert.assertEquals(responsePatch.jsonPath().getString("name"), user.getName());
				Assert.assertEquals(responsePatch.jsonPath().getString("email"), user.getEmail());
	}
}
