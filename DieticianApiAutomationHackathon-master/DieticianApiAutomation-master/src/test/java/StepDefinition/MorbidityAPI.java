package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Utils.ExcelReader;
import Utils.Logs;

public class MorbidityAPI extends BaseClass{
	TestContext testContext;

	HashMap<Integer, Response> postOutputMap = new HashMap<Integer, Response>();
	HashMap<Integer, String> ValidateBodyMessages = new HashMap<Integer, String>();
	// Dependency injection
	public MorbidityAPI(TestContext testContext) {
		this.testContext = testContext;
	}
	
	
	@Given("Morbididty API up and running for authorized user")
	public void morbididty_api_up_and_running_for_authorized_user() {
		testContext.requestSpec=RestAssured.given().baseUri(BASE_URL_DIETICIAN).auth().preemptive().basic(USERNAME, PASSWORD);
	}
	
	//Start --> Scenario: Test the Get request for all Morbidity Data of Morbidity API
	
	@When("User made Get request with endpoint as {string}")
	public void user_made_get_request_with_endpoint_as(String endpoint) {
		Logs.Log.info("********* GetAll Request started *************");
		testContext.response = get(testContext.requestSpec, endpoint, "");
	}

	@Then("Morbidity API should return all morbididty data with {int} status code")
	public void morbidity_api_should_return_all_morbididty_data_with_status_code(int code) throws ParseException, IOException {
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info(" Get request status code : " + testContext.response.getStatusCode());
		createJsonToExcelFile(testContext.response, "GetAll");
		assertEquals(code, testContext.response.getStatusCode());
	}
	
	@Then("Validate response body MorbididtyTestName as {string} field")
	public void validate_response_body_morbididty_test_name_as_field(String morbidityTestName) {
		assertEquals(testContext.response.getBody().asString().contains(morbidityTestName), true);
		
	}

	@Then("User validated with response schema")
	public void user_validated_with_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(getAllMobidityDataSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
		Logs.Log.info("********* GetAll Request Ended *************");
	}

	// End --> Scenario: Test the Get request for all Morbidity Data of Morbidity API
	
	
	// Start --> Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityName	
	@When("User made Get request by passing MorbididtyName from given sheetname {string} and rownumber {int} with endpoint as {string}")
	public void user_made_get_request_by_passing_morbididty_name_from_given_sheetname_and_rownumber_with_endpoint_as(String sheetname, int rowno, String endpoint) {
		Logs.Log.info("********* Get MorbididtyName Request started *************");
		ExcelReader reader=new ExcelReader();
		List<Map<String, String>> testData;
		try {
			testData = reader.getData(USERS_EXCEL_FILE, sheetname);
			String queryString = getByMorbididtyNameQueryString(testData, rowno);
			testContext.response = get(testContext.requestSpec, endpoint, queryString);
			Logs.Log.info(queryString);
			Logs.Log.info(testContext.response);

		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}	

	}	
	
	@Then("Validate response body MorbididtyTestId as \"{string}\" field")
	public void validate_response_body_morbididty_test_id_as_field(String ValidateBody) {
		assertEquals(testContext.response.getBody().asString().contains(ValidateBody), true);
	}

	
	@Then("Received Morbidity details are validated with response schema")
	public void received_morbidity_details_are_validated_with_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(getByMorbididtyNameMobidityDataSChema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
		Logs.Log.info("********* Get MorbididtyName Request ended *************");
	}
	// End -->  Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityName
	
	
	// Start --> Scenario Outline: Test the Get request to retrieve specific Morbididty data using invalid MorbidityTestId
	@When("User made Get request by passing invalid MorbidityTestId from given sheetname {string} and rownumber {int} with endpoint as {string}")
	public void user_made_get_request_by_passing_invalid_morbidity_test_id_from_given_sheetname_and_rownumber_with_endpoint_as(String sheetname, int rowno, String endpoint) {
		Logs.Log.info("********* Get Invalid MorbidityTestId Request started *************");
		ExcelReader reader=new ExcelReader();
		List<Map<String, String>> testData;
		try {
			testData = reader.getData(USERS_EXCEL_FILE, sheetname);
			String queryString = getByMorbididtyTestIdQueryString(testData, rowno);
			testContext.response = get(testContext.requestSpec, endpoint, queryString);
			Logs.Log.info(queryString);
			Logs.Log.info(testContext.response);
		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	@Then("Validate response body invalid message as \"{string}\" field")
	public void validate_response_body_invalid_message_as_field(String message) {
		assertEquals(testContext.response.getBody().asString().contains(message), true);
	   
	}
	
	@Then("Get Morbidity data with invalid message using response schema")
	public void get_morbidity_data_with_invalid_message_using_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(getInvalidMorTestIdSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
		Logs.Log.info("********* Get Invalid MorbidityTestId Request ended *************");
	}
	// End --> Scenario Outline: Test the Get request to retrieve specific Morbididty data using invalid MorbidityTestId
	
	
	
	// Start --> Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityTestId
	
	@When("User made Get request by passing MorbidityTestId from given sheetname {string} and rownumber {int} with endpoint as {string}")
	public void user_made_get_request_by_passing_morbidity_test_id_from_given_sheetname_and_rownumber_with_endpoint_as(String sheetname, int rowno, String endpoint) {
		Logs.Log.info("********* Get MorbidityTestId Request started *************");
		ExcelReader reader=new ExcelReader();
		List<Map<String, String>> testData;
		try {
			testData = reader.getData(USERS_EXCEL_FILE, sheetname);
			String queryString = getByMorbididtyTestIdQueryString(testData, rowno);
			testContext.response = get(testContext.requestSpec, endpoint, queryString);
			Logs.Log.info(queryString);
			Logs.Log.info(testContext.response);
		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	
	@Then("Validate response body MorbididtyName as \"{string}\" field")
	public void validate_response_body_morbididty_name_as_field(String MorbididtyName) {
		assertEquals(testContext.response.getBody().asString().contains(MorbididtyName), true);
	}
	
	
	@Then("Received Morbidity details validated using response schema")
	public void received_morbidity_details_validated_using_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(getByMorbididtyTestIdMobidityDataSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
		Logs.Log.info("********* Get MorbidityTestId Request ended *************");
	}
	
	// End --> Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityTestId
	
	// GET Common status code check 
	@Then("Morbidity API should return morbididty details with {int} status code")
	public void morbidity_api_should_return_morbididty_details_with_status_code(int code) {
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info(" Response status code : " + testContext.response.getStatusCode());
		assertEquals(code, testContext.response.getStatusCode());
	}
	

	

	
	// Start --> Scenario Outline: Test the post request for Mobididty API
	
	@When("User sends the post request with valid inputs from given sheetname {string} and endpoint as {string}")
	public void user_sends_the_post_request_with_valid_inputs_from_given_sheetname_and_endpoint_as(String sheetname, String endpoint) {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData;
		try {
			testData = reader.getData(USERS_EXCEL_FILE, sheetname);			
			// Post all data from excel file
			for (int i = 0; i<testData.size()-1; i++) {
				String jsonData = postJSONData(testData.get(i), i);
				Logs.Log.info(" POST jsonData " + jsonData );
				testContext.response = post(testContext.requestSpec, endpoint, jsonData);
				// logging messages
				addPostResponse(testContext.response, i+1);
				// Store all responses 
				postOutputMap.put(i, testContext.response);
			}

		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Then("New user record is successfully created with {int} status code")
	public void new_user_record_is_successfully_created_with_status_code(int code) {
		// Validate all responses 
		for (int i = 0; i < postOutputMap.size() ; i++) {
			postOutputMap.get(i).then().assertThat().statusCode(code);
			Logs.Log.info(" Post request row "+ (i+1) + " status code : "  + testContext.response.getStatusCode());
			assertEquals(code, postOutputMap.get(i).getStatusCode());
		}
	}
	
	@Then("Validate response body post Messages")
	public void validate_response_body_post_messages() {
		for (int i = 0; i < postOutputMap.size() ; i++) {
			assertEquals(postOutputMap.get(i).getBody().asString().contains(ValidateBodyMessages.get(i)), true);
		}
	}
	
	@Then("Mobidity API post request validated using response schema")
	public void mobidity_api_post_request_validated_using_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(getPostInvalidMobidityDataSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
	}
	
	// End --> Scenario Outline: Test the post request for Mobididty API
	
	
	
	
	
	// Start --> Scenario Outline: Test the update request for Morbidity API
	
	@SuppressWarnings("unchecked")
	@When("User sends put request from given {string} and rownumber {int} and endpoint as {string}")
	public void user_sends_put_request_from_given_and_rownumber_and_endpoint_as(String sheetname, int rownumber, String endpoint) {
		ExcelReader reader=new ExcelReader();
		JSONObject requestParams = new JSONObject();
		List<Map<String, String>> testData;
		try {
			testData = reader.getData(USERS_EXCEL_FILE, sheetname);
			String jsonData = putJSONData(testData, rownumber);
			String queryString = putQueryString(testData, rownumber);
			testContext.response = put(testContext.requestSpec, endpoint, jsonData, queryString); 
		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}		

	}	
	
	@Then("User record is successfully updated with {int} status code")
	public void user_record_is_successfully_updated_with_status_code(int code) {
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info(" Response status code : " + testContext.response.getStatusCode());
		assertEquals(code, testContext.response.getStatusCode());
	}
	
	@Then("Validate response body update as \"{string}\" field")
	public void validate_response_body_update_as_field(String ValidateBody) {
		assertEquals(testContext.response.getBody().asString().contains(ValidateBody), true);
	}
	
	@Then("Mobidity API put request validated using response schema")
	public void mobidity_api_put_request_validated_using_response_schema() {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(putMobidityDataSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
	}
	
	// End --> Scenario Outline: Test the update request for Morbidity API



	// Start --> Scenario Outline: Test the delete request for Morbidity API
	
	@SuppressWarnings("unchecked")
	@When("User sends delete request with valid input and endpoint as {string} from given {string} and rownumber {int}")
	public void user_sends_delete_request_with_valid_input_and_endpoint_as_from_given_and_rownumber(String endpoint,String sheetname, int rowno) {
		ExcelReader reader=new ExcelReader();
		try {
			List<Map<String,String>> testData =  reader.getData(USERS_EXCEL_FILE, sheetname);
			String deleteQueryString = deleteQueryString(testData, rowno);
			Logs.Log.debug(deleteQueryString);			
			testContext.response = delete(testContext.requestSpec, endpoint, deleteQueryString);
		} catch (InvalidFormatException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logs.Log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Then("Validate response body delete as \"{string}\" field")
	public void validate_response_body_delete_as_field(String ValidateBody) {
		assertEquals(testContext.response.getBody().asString().contains(ValidateBody), true);
	}
	
	@Then("Mobidity API delete request validated using response schema \"{string}\"")
	public void mobidity_api_delete_request_validated_using_response_schema(String ValidateBody) {
		Logs.Log.info( "************** Schema Validation ************************* ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			if (ValidateBody.contains("Successfully Deleted."))
				assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(deleteMobidityDataSchema)));
			else 
				assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(deleteMobidityInvalidDataSchema)));
			Logs.Log.info("Schema validation success");
		}
		Logs.Log.info("Schema validation status code : " + statusCode);
	}
	
	// End --> Scenario Outline: Test the delete request for Morbidity API
	


	@SuppressWarnings("unchecked")
	private String postJSONData(Map<String,String> testData, int rowNum) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("MorbidityName", testData.get("MorbidityName"));
		requestParams.put("MorbidityTestName", testData.get("MorbidityTestName"));
		requestParams.put("MorbidityMarkerRef", testData.get("MorbidityMarkerRef"));
		requestParams.put("MorbidityTestUnit", testData.get("MorbidityTestUnit"));
		Logs.Log.debug(requestParams.toJSONString());
		ValidateBodyMessages.put(rowNum, testData.get("ValidateBody"));
		return requestParams.toJSONString();		
	} 

	@SuppressWarnings("unchecked")
	private String postJSONInvalidData(Map<String,String> testData) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("MorbidityName", testData.get("MorbidityName"));
		requestParams.put("MorbidityTestName", testData.get("MorbidityTestName"));
		requestParams.put("MorbidityMarkerRef", testData.get("MorbidityMarkerRef"));
		Logs.Log.debug(requestParams.toJSONString());
		return requestParams.toJSONString();		
	} 

	@SuppressWarnings("unchecked")
	private String putJSONData(List<Map<String,String>> testData, int rowNumber) 
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("MorbidityMarkerRef",testData.get(rowNumber).get("MorbidityMarkerRef"));	
		requestParams.put("MorbidityTestUnit",testData.get(rowNumber).get("MorbidityTestUnit"));
		String MorbidityName=testData.get(rowNumber).get("MorbidityName");
		String MorbidityTestId=testData.get(rowNumber).get("MorbidityTestId");
		Logs.Log.debug(requestParams.toJSONString());
		return requestParams.toJSONString();	
	}


	@SuppressWarnings("unchecked")
	private String getByMorbididtyNameQueryString(List<Map<String,String>> testData, int rowNumber) 
	{
		String MorbidityName=testData.get(rowNumber).get("MorbidityName");
		return "MorbidityName="+MorbidityName;

	}

	@SuppressWarnings("unchecked")
	private String getByMorbididtyTestIdQueryString(List<Map<String,String>> testData, int rowNumber) 
	{
		String MorbidityTestId=testData.get(rowNumber).get("MorbidityTestId");
		return "MorbidityTestId="+MorbidityTestId;

	}


	@SuppressWarnings("unchecked")
	private String putQueryString(List<Map<String,String>> testData, int rowNumber) 
	{
		String MorbidityName=testData.get(rowNumber).get("MorbidityName");
		String MorbidityTestId=testData.get(rowNumber).get("MorbidityTestId");
		return "MorbidityName="+MorbidityName+"&MorbidityTestId="+MorbidityTestId;

	}

	private void addPostResponse(Response response, int rowNum) {
		ResponseBody responseBody = response.getBody();
		if(responseBody.asString() != "") {
			JsonPath jPath = responseBody.jsonPath();
			if (jPath != null && jPath.get("Message") != null) {
				String morbidityTestId = "";
				if (jPath.get("MorbidityTestId") != null) {
					morbidityTestId = jPath.get("MorbidityTestId").toString();
				}
				if (morbidityTestId!="")
					Logs.Log.info("Row : " +rowNum +"  --->  " + morbidityTestId +" : " +  jPath.get("Message").toString());
				else 
					Logs.Log.info("Row : " +rowNum +"  --->  " + "Error Message : " +  jPath.get("Message").toString());				
			}
		}
	} 

	@SuppressWarnings("unchecked")
	private String deleteQueryString(List<Map<String,String>> testData, int rowNumber)
	{
		String deleteQueryString = "MorbidityName="+testData.get(rowNumber).get("MorbidityName")+"&MorbidityTestId="+testData.get(rowNumber).get("MorbidityTestId");
		return deleteQueryString;		
	}

	
}
