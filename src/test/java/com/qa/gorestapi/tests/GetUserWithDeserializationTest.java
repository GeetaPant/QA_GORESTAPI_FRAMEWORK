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
		Response response = restClient.get(BASE_URL,"/public/v2/users", null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		User[] use= JsonUtils.deserialize(response, User[].class);
		
		System.out.println(Arrays.toString(use));
		for(User u: use)
		{
			System.out.println("ID is:  "+ u.getId());
			System.out.println("User's Name :  "+ u.getName());
			System.out.println("User's Email :  "+ u.getEmail());
			System.out.println("User's Gender :  "+ u.getGender());
			System.out.println("*************************************");
			
		}
		
	}
	

}
