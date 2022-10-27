package StepDefinition;

import io.cucumber.java.en.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.ExcelReader;
import Utils.Logs;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hamcrest.Matchers;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class UsersApiSteps {
	
	int statuscode;
	String firstName;
	String lastName;
	String email;
	String city;
	String address1;
	String address2;
	String contact;
	String state;
	String country;
	String loginusername;
	String password;
	String  message_response;
	TestContext testContext;
	ExcelReader reader = new ExcelReader();
	List<Map<String,String>> testData;
	
	public UsersApiSteps(TestContext testContext) {
		this.testContext = testContext;
	}
	
	@Given("Initilize base uri with wrong credentials")
	public void initilize_base_uri_with_wrong_credentials() {
		testContext.requestSpec = given().baseUri(BaseClass.BASE_URL_DIETICIAN).auth().basic(BaseClass.USRNAME, BaseClass.PASSWD);
	}

	@When("Get request is sent with endpoint as {string}")
	public void get_request_is_sent_with_endpoint_as(String endpoint) {
		testContext.response = testContext.requestSpec.when().get(endpoint);
	}

	@Then("Verify statuscode is {int} unauthorised")
	public void verify_statuscose_is_unauthorised(Integer statuscode) {
		testContext.response.then().assertThat()
	    .statusCode(statuscode);
		Logs.info("Verifying status code for unauthorized user" );
	}

	@Given("UsersAPI is up and running for authorised user")
	public void users_api_is_up_and_running_for_authorised_user() {
		testContext.requestSpec = given().baseUri(BaseClass.BASE_URL_DIETICIAN).auth().preemptive().basic(BaseClass.USERNAME, BaseClass.PASSWORD);
		Logs.info("UsersAPI is up and running for authorised user" );
	}

	@When("User sends Get request to retrive data with endpoint as {string}")
	public void user_sends_get_request_to_retrive_data_with_endpoint_as(String endpoint) {
		testContext.response = testContext.requestSpec.when().get(endpoint);
	}

	@Then("API should return all User records with success code {int}")
	public void api_should_return_all_user_records_with_success_code(Integer statuscode) {
		testContext.response.then().assertThat()
	    .statusCode(statuscode);
		Logs.info("Verifying status code for authorized user" );
	}
	
	@When("User sends Get request to retrieve specific user data with endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_get_request_to_retrieve_specific_user_data_with_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
		
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("Contact"));
		Logs.info("Retrieve specific user data for Contact");
	}

	@Then("API should return record for specific user with success code {int}")
	public void api_should_return_record_for_specific_user_with_success_code(Integer statuscode) {
		
		testContext.response.then().assertThat()
	 	.statusCode(statuscode).and().body(JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_GET_SCHEMA)));
		testContext.res = testContext.response.getBody().asString();
		Logs.info("Verifying status code for authorized use");
		Logs.info(testContext.response.asString());
		assertEquals(testContext.response.asString().contains("Contact"), true);
			
	}
	
	@When("User sends Get request to retrive specific user data by DieticianId with endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_get_request_to_retrive_specific_user_data_by_DieticianId_with_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
	    
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("DieticianId"));
		Logs.info("Retrieve specific user data for DieticianId");
	}

	@Then("API should return record for specific user by DieticianId with success code {int}")
	public void api_should_return_record_for_specific_user_by_dietician_id_with_success_code(Integer statuscode) {
		testContext.response.then().assertThat()
	 	.statusCode(statuscode).and().body(JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_GET_SCHEMA)));
		Logs.info("Verifying status code for authorized use");
		assertEquals(testContext.response.asString().contains("UserId"), true);
	}

	@When("User sends Get request to retrive specific user data by email with endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_get_request_to_retrive_specific_user_data_by_email_with_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
	    
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("Email"));
		Logs.info("Retrieve specific user data for Email");
	}

	@Then("API should return record for specific user by email with success code {int}")
	public void api_should_return_record_for_specific_user_by_email_with_success_code(Integer statuscode) {
		testContext.response.then().assertThat()
	 	.statusCode(statuscode);
		String responseBody = testContext.response.asString();
		if (statuscode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_GETEMAIL_SCHEMA)));
			Logs.Log.info("Schema validation success");
		//.and().body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/JsonSchemas/UserGetByEmailSchema.json")));
		Logs.info("Verifying status code for authorized use");
		assertEquals(testContext.response.asString().contains("Email"), true);
		}
	}
	
	@When("User sends Get request to retrive specific user data by firstname with endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_get_request_to_retrive_specific_user_data_by_firstname_with_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
	   
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("FirstName"));
		Logs.info("Retrieve specific user data for FirstName");
	}

	@Then("API should return record for specific user by firstname with success code {int}")
	public void api_should_return_record_for_specific_user_by_firstname_with_success_code(Integer statuscode) {
	    
		testContext.response.then().assertThat()
	 	.statusCode(statuscode);
		String responseBody = testContext.response.asString();
		if (statuscode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_GETFNAME_SCHEMA)));
			Logs.Log.info("Schema validation success");
		}
	 	
		Logs.info("Verifying status code for authorized use");
		assertEquals(testContext.response.asString().contains("FirstName"), true);
	}
	
	@When("User sends Get request to retrive specific user data by usertype with endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_get_request_to_retrive_specific_user_data_by_usertype_with_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
	    
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("UserType"));
		Logs.info("Retrieve specific user data for UserType");
	}

	@Then("API should return record for specific user by usertype with success code {int}")
	public void api_should_return_record_for_specific_user_by_usertype_with_success_code(Integer statuscode) {
	    
		testContext.response.then().assertThat()
	 	.statusCode(statuscode).and().body(JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_GET_SCHEMA)));
		Logs.info("Verifying status code for authorized use");
		assertEquals(testContext.response.asString().contains("UserId"), true);
	}
	
	@When("User sends delete request with valid inputs and endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_delete_request_with_valid_inputs_and_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
		
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		JSONObject body_json = new JSONObject();
		
		body_json.put("DieticianId",testData.get(rownumber).get("DieticianId"));
		body_json.put("UserId",testData.get(rownumber).get("UserId"));
		
		//Add header to request body stating it is JSON
		testContext.requestSpec.header("Content-Type","application/json");
		//Add JSON to the request body
		testContext.requestSpec.body(body_json.toJSONString());
		String res = endpoint +testData.get(rownumber).get("DieticianId") +"&UserId="+testData.get(rownumber).get("UserId");
		System.out.println(res);
		//DELETE request
		testContext.response = testContext.requestSpec.when().delete(res);
		testContext.res = testContext.response.getBody().asString();
		System.out.println("Response :" +testContext.res);
	}

	@Then("User record is successsfully deleted with successcode {int}")
	public void user_record_is_successsfully_deleted_with_successcode(Integer actualstatuscode) {
		Integer expectedstatuscode = testContext.response.getStatusCode();
		//Add header to request body stating it is JSON
		testContext.requestSpec.header("Content-Type","application/json");
		testContext.res = testContext.response.getBody().asString();
		testContext.jsonPath = new JsonPath(testContext.res);
		
		assertEquals(actualstatuscode, expectedstatuscode);
		message_response = testContext.jsonPath.getString("Message");
		Assert.assertEquals(message_response, "Successfully Deleted.");
	}
	
	@When("User sends post request with valid inputs and endpoint as {string} from given sheetname {string}")
	public void user_sends_post_request_with_valid_inputs_and_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname)throws InvalidFormatException, IOException {
	    
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);	
		//Headers
    	Map<String,String> headers = new HashMap<String,String>();
		// Post all data from excel file
		for (int i = 0; i<testData.size()-1; i++) {
			String jsonData = BaseClass.postUserJSONData(testData.get(i), i);
			Logs.Log.info(" POST jsonData " + jsonData );
			// logging messages
		
		
		headers.put("Content-type","application/json");
  		testContext.response = testContext.requestSpec.headers(headers).body(jsonData).when().post(endpoint);
  		Logs.info(testContext.response.getBody().asString());
		}
	}

	@Then("New user record is created with success code {int}")
	public void new_user_record_is_created_with_success_code(Integer statuscode) {
	   
		 	String userId;
		    Integer expectedstatuscode;
		    testContext.response.then().assertThat()
		 	.statusCode(statuscode).and().body(JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_POST_SCHEMA)));

			expectedstatuscode= testContext.response.getStatusCode();
			testContext.res = testContext.response.getBody().asString();
			System.out.println("Response:" +testContext.res);
			testContext.jsonPath = new JsonPath(testContext.res);
			userId = testContext.jsonPath.getString("UserId");
			System.out.println(userId);
			message_response = testContext.jsonPath.getString("Message");
			Response response_get = given().baseUri(BaseClass.BASE_URL_DIETICIAN)
					.auth().preemptive().basic(BaseClass.USERNAME, BaseClass.PASSWORD)
					.when()
					.get(BaseClass.BASE_URL_DIETICIAN +"/api/Users/DieticianId=" +userId);
			System.out.println("Response from get Request:" +response_get.asString()); 
			
			//making asertions for input fields from response field	
			assertEquals(testContext.res.contains(userId), true);
			assertEquals(message_response, "Successfully Created.");
	}
	
	@When("User sends put request with valid inputs and endpoint as {string} from given sheetname {string} and rownumber {int}")
	public void user_sends_put_request_with_valid_inputs_and_endpoint_as_from_given_sheetname_and_rownumber(String endpoint, String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
		
		testData = reader.getData(BaseClass.USERS_EXCEL_Path, sheetname);
		JSONObject body_json = new JSONObject();
	
		body_json.put("FirstName",testData.get(rownumber).get("FirstName"));
		body_json.put("LastName",testData.get(rownumber).get("LastName"));
		
		Map<String,String> address = new HashMap<String,String>();
		address.put("Address1",testData.get(rownumber).get("Address1"));
		address.put("Address2",testData.get(rownumber).get("Address2"));
		address.put("City",testData.get(rownumber).get("City"));
		address.put("State",testData.get(rownumber).get("State"));
		address.put("Country",testData.get(rownumber).get("Country"));
		body_json.put("Address", address);
		
		body_json.put("Contact",testData.get(rownumber).get("Contact"));
		body_json.put("Email",testData.get(rownumber).get("Email"));
		body_json.put("FoodCategory",testData.get(rownumber).get("FoodCategory"));
		body_json.put("Allergy",testData.get(rownumber).get("Allergy"));
		
		String res = endpoint +testData.get(rownumber).get("DieticianId") +"&UserId="+testData.get(rownumber).get("UserId");
		Logs.info(res);
		//Headers
    	Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-type","application/json");
  		testContext.response = testContext.requestSpec.headers(headers).body(body_json.toJSONString()).when().put(res);
  		Logs.info(testContext.response.getBody().asString());
    			
	}

	@Then("User record is updated with success code {int}")
	public void user_record_is_updated_with_success_code(Integer statuscode) {
		
		String userid,firstName;
		Integer expectedstatuscode;
		System.out.println(testContext.response.getBody().asString());
		testContext.response.then().assertThat()
	 	.statusCode(statuscode).and().body(JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.USERS_PUT_SCHEMA)));
		Logs.info("Schema validation for Users Api Put method");
		
		testContext.res = testContext.response.getBody().asString();
		Logs.info("Response:" +testContext.res);
		testContext.jsonPath = new JsonPath(testContext.res);
		userid  = testContext.jsonPath.getString("UserId");
		firstName = testContext.jsonPath.getString("FirstName");
		 message_response = testContext.jsonPath.getString("Message");
		//checking for the user_id created by calling Get method for that user_id
		 Response response_get = given().baseUri(BaseClass.BASE_URL_DIETICIAN)
					.auth().preemptive().basic(BaseClass.USERNAME, BaseClass.PASSWORD)
					.when()
					.get(BaseClass.BASE_URL_DIETICIAN +"/api/Users/UserType="+userid);
		 Logs.info("Response from get Request:" +response_get.asString());
		//making asertions for input fields from response field	
		assertEquals(testContext.res.contains(userid), true);
		assertEquals(testContext.res.contains(firstName), true);
		assertEquals(testContext.res.contains(message_response), true);
		assertEquals(message_response, "User updated successful");
	}
}
