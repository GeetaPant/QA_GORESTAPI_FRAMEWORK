package com.qa.gorestapi.client;

import java.io.File;
import java.util.Map;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

import com.qa.api.exceptions.FrameworkException;
import com.qa.gorestapi.constants.AuthTypes;
import com.qa.gorestapi.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;

public class RestClient {
	
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec401 = expect().statusCode(401);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec422 = expect().statusCode(422);
	private ResponseSpecification responseSpec500 = expect().statusCode(500);
	
	private String baseUrl = ConfigManager.get("baseUrl");
	
	private RequestSpecification setupRequest(String baseUrl,AuthTypes authType, ContentType contentType) {
		
			RequestSpecification request = RestAssured
											.given().log().all()
												.baseUri(baseUrl)
													.contentType(contentType);
			switch(authType) {
			case BEARER_TOKEN:
				request.header("Authorization","Bearer "+ConfigManager.get("bearerToken"));
				break;
			case OAUTH2:
				request.header("Authorization","Bearer "+generateOAuth2Token());
				break;
			case BASIC_AUTH:
				request.header("Authorization","Basic ");
				break;
			case API_KEY:
				request.header("api_key","Bearer "+ConfigManager.get("apiKey"));
				break;
			case NO_AUTH:
				System.out.println("No Auth required");
				break;
			default:
				System.out.println("This auth is not required.... Please pass the right auth type");
				 throw new FrameworkException("NO AUTH SUPPORTED");
				}
			return request;
	}
	
	private String generateOAuth2Token() {
		return RestAssured.given()
					.formParam("client_id", ConfigManager.get("clientId"))
					.formParam("client_secret", ConfigManager.get("clientSecret"))
					.formParam("grant_type", ConfigManager.get("grantType"))
					.post(ConfigManager.get("tokenUrl"))
						.then()
							.extract()
								.path("access_token");		
	}
	//**************************CRUD Methods****************************
	
	public Response get(String baseUrl,String endPoint, Map<String, String> queryParams, 
										Map<String, String> pathParams, 
										AuthTypes authType, ContentType contentType)
	{	
		RequestSpecification request = setupRequest(baseUrl,authType, contentType);
		applyParams(request, queryParams,queryParams);
		
		Response response =  request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T>Response post(String baseUrl, String endPoint, T body,  Map<String, String> queryParams, 
								Map<String, String> pathParams,
								AuthTypes authType, ContentType contentType)
	{	
		RequestSpecification request = setupRequest(baseUrl,authType, contentType);
		applyParams(request, queryParams,queryParams);
	
		Response response =  request.body(body).post(endPoint).then().spec(responseSpec201).extract().response();
		response.prettyPrint();
		return response;
	}
	public Response post(String baseUrl,String endPoint, File file,  Map<String, String> queryParams, 
													Map<String, String> pathParams,
													AuthTypes authType, ContentType contentType)
	{
			RequestSpecification request = setupRequest(baseUrl,authType, contentType);
			applyParams(request, queryParams,queryParams);

			Response response =  request.body(file).post(endPoint).then().spec(responseSpec201).extract().response();
			response.prettyPrint();
			return response;
	}

	public <T>Response patch(String baseUrl,String endPoint, T body,  Map<String, String> queryParams, 
						Map<String, Integer> pathParams,
						AuthTypes authType, ContentType contentType) {
		
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams,queryParams);
	
		Response response =  request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}
		public Response delete(String baseUrl,String endPoint, Map<String, String> queryParams, 
			Map<String, String> pathParams, 
			AuthTypes authType, ContentType contentType)
		{	
			RequestSpecification request = setupRequest(baseUrl,authType, contentType);
			applyParams(request, queryParams,queryParams);

			Response response =  request.delete(endPoint).then().spec(responseSpec204).extract().response();
			response.prettyPrint();
			return response;
		}
	private void applyParams(RequestSpecification request,Map<String, String> queryParams, Map<String, String> pathParams) {
		if(queryParams != null) {
			request.queryParams(queryParams);
		}
		if(pathParams != null) {
			request.pathParams(pathParams);
		}
	}
}
