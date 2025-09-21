package com.qa.gorestapi.tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithDeserializationTest extends BaseTest {
	
	@Test
	public void getDeserilizedUserTest() {
		Response response = restClient.get(BASE_URL,GOREST_ENDPOINT, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		User[] user= JsonUtils.deserialize(response, User[].class);
		
		System.out.println(Arrays.toString(user));
		for(User u: user)
		{
			System.out.println("ID is:  "+ u.getId());
			System.out.println("User's Name :  "+ u.getName());
			System.out.println("User's Email :  "+ u.getEmail());
			System.out.println("User's Gender :  "+ u.getGender());
			System.out.println("*************************************");
			
		}
		
	}
	

}
