package com.qa.gorestapi.schemaValidation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gorestapi.base.BaseTest;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.pojo.User;
import com.qa.gorestapi.utils.SchemaValidator;
import com.qa.gorestapi.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserAPISchemaValidationTest extends BaseTest {
	
	@Test
	public void createUserWithBuilderTest() {
		User user = User.builder()
				.name("Vyom")
				.email(StringUtil.getRandomEmailID())
				.gender("male")
				.status("active")
				.build();
			Response responsePost = restClient.post(BASE_URL,"/public/v2/users",user, null, null, AuthTypes.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responsePost.getStatusCode(), 201);
			String id = responsePost.jsonPath().getString("id");
			System.out.println("Created User ID is --"+ id);
	
			
			Response responseGet= restClient.get(BASE_URL, "/public/v2/users/"+ id, null, null, AuthTypes.BEARER_TOKEN, ContentType.ANY);
			Assert.assertEquals(SchemaValidator.validateSchema(responseGet, "schema\\gorest-schema.json"),true);
	}
	

}
