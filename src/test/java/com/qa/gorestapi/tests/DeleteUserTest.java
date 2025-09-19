package com.qa.gorestapi.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
	@Test
	public void deleteUserTest() 
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
			
			//DELETE the created user
			Response responseDelete = restClient.delete(BASE_URL,"/public/v2/users/"+id, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseDelete.getStatusCode(), 204);
			
			//Recheck the delete user 
			Response responseGetDeleted = restClient.get(BASE_URL,"/public/v2/users/"+id, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGetDeleted.getStatusCode(), 404);
			Assert.assertEquals(responseGetDeleted.jsonPath().getString("message"), "Resource not found");
	}

}
