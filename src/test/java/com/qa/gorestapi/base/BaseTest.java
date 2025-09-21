package com.qa.gorestapi.base;

import org.testng.annotations.BeforeMethod;
import com.qa.gorestapi.client.RestClient;

import io.restassured.RestAssured;
import io.qameta.allure.restassured.AllureRestAssured;
public class BaseTest {
	
	protected final static String BASE_URL= "https://gorest.co.in";
	protected final static String GOREST_ENDPOINT = "/public/v2/users/";
	
	protected RestClient restClient;
	
	@BeforeMethod
	public void setup() {
		RestAssured.filters(new AllureRestAssured());
		 restClient = new RestClient();
	}
}
