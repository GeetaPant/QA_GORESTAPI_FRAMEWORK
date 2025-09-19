package com.qa.gorestapi.tests;

//import java.util.HashMap;
//import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest {
	
	@Test
	public void getAllUserTest() {
		
		/*
		 * Map<String, String> queryParams = new HashMap<String, String>();
		 * queryParams.put("name", "Naveen"); queryParams.put("status", "active");
		 */
		Response response = restClient.get(BASE_URL,"/public/v2/users", null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON );
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(enabled =false)
	public void getSingleUserTest() 
	{
	Response response = restClient.get(BASE_URL,"/public/v2/users/8103026", null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON );
	Assert.assertEquals(response.getStatusCode(), 200);
	}
	
}