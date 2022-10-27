package StepDefinition;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import Utils.ExcelReader;
import Utils.Logs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import junit.framework.Assert;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;

public class RecipeApi {

	TestContext testContext;

	public RecipeApi(TestContext testContext) {
		this.testContext = testContext;
	}

	@Given("User is in Url Url\\/api\\/")
	public void user_is_in_url_url_api() {
		// Write code here that turns the phrase above into concrete actions
//		testContext.requestSpec = given().baseUri("http://127.0.0.1:5000/api/");
		testContext.requestSpec = given().baseUri(BaseClass.BASE_URL_DIETICIAN).auth().preemptive().basic("user","pwd");
		Logs.Log.info("**** Log Scenario: User access request without login ****" );
		Logs.Log.info("LoggedIn with invalid credentials" );
		
	}

	@When("GET request is made in Url {string} without login")
	public void get_request_is_made_in_url_without_login(String endpoint) {
		// Write code here that turns the phrase above into concrete actions
		testContext.response = testContext.requestSpec.when().get(endpoint);

	}

	@Then("The request returned with Response code {int} Unauthorised Access")
	public void the_request_returned_with_response_code_unauthorised_access(Integer code) {
		// Write code here that turns the phrase above into concrete actions
		testContext.response.then().assertThat().statusCode(code);
		System.out.println("Response code is =>  " + testContext.response.getStatusCode());
		Logs.Log.info("Unauthorised Access login GET request status code : " + testContext.response.getStatusCode());
	}

	@Given("Dietician API is up and running for authorized user")
	public void dietician_api_is_up_and_running_for_authorized_user() {
		// Write code here that turns the phrase above into concrete actions
		testContext.requestSpec = given().baseUri(BaseClass.BASE_URL_DIETICIAN).auth().preemptive()
				.basic(BaseClass.USERNAME, BaseClass.PASSWORD);
		
		Logs.Log.info("LoggedIn with valid credentials" );

	}

	@When("GET request is made to enpoint as {string}")
	public void get_request_is_made_to_enpoint_as(String endpoint) {
		// Write code here that turns the phrase above into concrete actions
		testContext.response = testContext.requestSpec.when().get(endpoint);
		System.out.println(testContext.response.getBody().asPrettyString());
		Assert.assertEquals(testContext.response.getBody().asString().contains("Achari Paneer"), true);

	}

	@Then("The requested Recipes data is returned with Response code {int}")
	public void the_requested_recipes_data_is_returned_with_response_code(Integer code) {
		// Write code here that turns the phrase above into concrete actions
		testContext.response.then().assertThat().statusCode(code);
		System.out.println("Response code is =>  " + testContext.response.getStatusCode());
		Logs.Log.info("**** Log Scenario: User Retrieve Recipes Data ****" );
		Logs.Log.info("Recipes data GET request status code : " + testContext.response.getStatusCode());
		Logs.Log.info("** Schema Validation ** ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipeapi)));
			Logs.Log.info("Schema validation success");
		}

	}

	@When("GET request is made to with endpoint as {string} with input RecipeFoodCategory from excel sheetname {string} and rownumber {int}")
	public void get_request_is_made_to_with_endpoint_as_with_input_recipe_food_category_from_excel_sheetname_and_rownumber(
			String endpoint, String sheetname, int rownumber) throws InvalidFormatException, IOException {
		// Write code here that turns the phrase above into concrete actions
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(BaseClass.USERS_EXCEL_Path_Recipe, sheetname);
		testContext.response = testContext.requestSpec.when()
				.get(endpoint + testData.get(rownumber).get("RecipeFoodCategory"));
		System.out.println(testContext.response.getBody().asPrettyString());
		

	}

	@Then("The requested data with input RecipeFoodCategory is returned with Response code {int} and validate {string}")
	public void the_requested_data_with_input_recipe_food_category_is_returned_with_response_code_and_validate(Integer code, String val) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Response code =>  " + testContext.response.getStatusCode());
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info("**** Log Scenario: User Retrieve Recipes Data  based on Food Category ****" );
		Logs.Log.info("Recipes data with RecipeFoodCategory GET request status code : "
				+ testContext.response.getStatusCode());
		
		Logs.Log.info("** Schema Validation ** ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipefc)));
			Logs.Log.info("Schema validation success");
			
			Assert.assertEquals(testContext.response.getBody().asString().contains(val), true);
		}
	}

	@When("GET request is made to endpoint {string} with input RecipeType from excel sheetname {string} and rownumber {int}")
	public void get_request_is_made_to_endpoint_with_input_recipe_type_from_excel_sheetname_and_rownumber(
			String endpoint, String sheetname, int rownumber) throws InvalidFormatException, IOException {
		// Write code here that turns the phrase above into concrete actions
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(BaseClass.USERS_EXCEL_Path_Recipe, sheetname);
		testContext.response = testContext.requestSpec.when().get(endpoint + testData.get(rownumber).get("RecipeType"));
		System.out.println(testContext.response.getBody().asPrettyString());
	}

	@Then("The requested data with input RecipeType is returned with Response code {int} and validate {string}")
	public void the_requested_data_with_input_recipe_type_is_returned_with_response_code_and_validate(Integer code, String val) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Response code =>  " + testContext.response.getStatusCode());
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info("**** Log Scenario: User Retrieve Recipes Data based on Recipe Type ****" );
		Logs.Log.info("Recipes data with RecipeType GET request status code : " + testContext.response.getStatusCode());
		Logs.Log.info("** Schema Validation ** ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipei)));
			Logs.Log.info("Schema validation success");
			Assert.assertEquals(testContext.response.getBody().asString().contains(val), true);
		}
	}

	@When("GET request is made to endpoint {string} with input RecipeIngredient from excel sheetname {string} and rownumber {int}")
	public void get_request_is_made_to_endpoint_with_input_recipe_ingredient_from_excel_sheetname_and_rownumber(
			String endpoint, String sheetname, int rownumber) throws InvalidFormatException, IOException {
		// Write code here that turns the phrase above into concrete actions
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(BaseClass.USERS_EXCEL_Path_Recipe, sheetname);
		testContext.response = testContext.requestSpec.when()
				.get(endpoint + testData.get(rownumber).get("RecipeIngredient"));
		System.out.println(testContext.response.getBody().asPrettyString());
	}

	@Then("The requested data with input RecipeIngredient is returned with Response code {int} and validate {string}")
	public void the_requested_data_with_input_recipe_ingredient_is_returned_with_response_code_and_validate(Integer code, String val) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Response code =>  " + testContext.response.getStatusCode());
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info("**** Log Scenario: User Retrieve Recipes Data based on Recipe Ingredient ****" );
		Logs.Log.info(
				"Recipes data with RecipeIngredient GET request status code : " + testContext.response.getStatusCode());
		Logs.Log.info("** Schema Validation **");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipei)));
			Logs.Log.info("Schema validation success");
			Assert.assertEquals(testContext.response.getBody().asString().contains(val), true);
		}
	}

	@When("GET request is made to endpoint {string} with input RecipeNutrient from excel sheetname {string} and rownumber {int}")
	public void get_request_is_made_to_endpoint_with_input_recipe_nutrient_from_excel_sheetname_and_rownumber(
			String endpoint, String sheetname, int rownumber) throws InvalidFormatException, IOException {
		// Write code here that turns the phrase above into concrete actions
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(BaseClass.USERS_EXCEL_Path_Recipe, sheetname);
		testContext.response = testContext.requestSpec.when()
				.get(endpoint + testData.get(rownumber).get("RecipeNutrient"));
		System.out.println("Response code =>  " + testContext.response.getStatusCode());
		System.out.println(testContext.response.getBody().asPrettyString());
	}

	@Then("The requested data with input RecipeNutrient is returned with Response code {int} and validate {string}")
	public void the_requested_data_with_input_recipe_nutrient_is_returned_with_response_code_and_validate(Integer code, String val) {
		// Write code here that turns the phrase above into concrete actions
		testContext.response.then().assertThat().statusCode(code);
		Logs.Log.info("**** Log Scenario: User Retrieve Recipes Data based on Recipe Nutrient ****" );
		Logs.Log.info(
				"Recipes data with RecipeNutrient GET request status code : " + testContext.response.getStatusCode());
		Logs.Log.info("**Schema Validation** ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipnp)));
			Logs.Log.info("Schema validation success");
			Assert.assertEquals(testContext.response.getBody().asString().contains(val), true);
		}
	}

	@When("GET request is made to endpoint {string} with invalid input RecipeNutrient from excel sheetname {string} and rownumber {int}")
	public void get_request_is_made_to_endpoint_with_invalid_input_recipe_nutrient_from_excel_sheetname_and_rownumber(
			String endpoint, String sheetname, int rownumber) throws InvalidFormatException, IOException {
		// Write code here that turns the phrase above into concrete actions
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(BaseClass.USERS_EXCEL_Path_Recipe, sheetname);
		testContext.response = testContext.requestSpec.when()
				.get(endpoint + testData.get(rownumber).get("RecipeNutrient"));
		System.out.println(testContext.response.getBody().asPrettyString());
	}

	@Then("The request returned with Response code {int} with msg Item Not Found and validate {string}")
	public void the_request_returned_with_response_code_with_msg_item_not_found_and_validate(Integer code, String val) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Response code =>  " + testContext.response.getStatusCode());
		testContext.response.then().assertThat().statusCode(code);

		Logs.Log.info("****Log Scenario: User Retrieve Recipes Data based on Recipe Nutrient Negative****" );
		Logs.Log.info("Recipes data with invalid RecipeNutrient GET request status code : "
				+ testContext.response.getStatusCode());
		Logs.Log.info("** Schema Validation ** ");
		String responseBody = testContext.response.getBody().asString();
		int statusCode = testContext.response.getStatusCode();
		if (statusCode == 200) {
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(BaseClass.getrecipnn)));
			Logs.Log.info("Schema validation success");
			Assert.assertEquals(testContext.response.getBody().asString().contains(val), false);
		}
	}

}
