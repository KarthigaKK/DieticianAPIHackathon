package StepDefinition;

//<<<<<<< HEAD
//public class BaseClass {
//	
//	public static final String BASE_URL_DIETICIAN = "http://127.0.0.1:5000/";
//	public static final String USERNAME = "KMSASM2022";
//	public static final String PASSWORD = "Dietician1!";
//	public static final String USERS_EXCEL_Path = "./src/test/java/Utils/RecipeApi.xlsx";
//	public static final String getrecipeapi = "./src/test/resources/JsonSchemas/RecipeApi.json";
//	public static final String getrecipefc = "./src/test/resources/JsonSchemas/RecipeFC.json";
//	public static final String getrecipet = "./src/test/resources/JsonSchemas/RecipeT.json";
//	public static final String getrecipei = "./src/test/resources/JsonSchemas/RecipeT.json";
//	public static final String getrecipnp = "./src/test/resources/JsonSchemas/RecipeNP.json";
//	public static final String getrecipnn = "./src/test/resources/JsonSchemas/RecipeNN.json";
//=======
//
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import Utils.Logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;

import java.util.*;
import Utils.Logs;


public class BaseClass {
	
	public static final String BASE_URL_DIETICIAN = "http://127.0.0.1:5000";
	public static final String USERNAME = "KMSASM2022";
	public static final String PASSWORD = "Dietician1!";

	public static final String USRNAME = "KMSASM";
	public static final String PASSWD = "Dietician!";
	
	public static final String USERS_EXCEL_Path_Recipe = "./src/test/java/Utils/RecipeApi.xlsx";
	public static final String getrecipeapi = "./src/test/resources/JsonSchemas/RecipeApi.json";
	public static final String getrecipefc = "./src/test/resources/JsonSchemas/RecipeFC.json";
	public static final String getrecipet = "./src/test/resources/JsonSchemas/RecipeT.json";
	public static final String getrecipei = "./src/test/resources/JsonSchemas/RecipeT.json";
	public static final String getrecipnp = "./src/test/resources/JsonSchemas/RecipeNP.json";
	public static final String getrecipnn = "./src/test/resources/JsonSchemas/RecipeNN.json";

	public static final String USERS_EXCEL_FILE = System.getProperty("user.dir") + "/src/test/java/Utils/MorbidityAPI.xlsx";	
	public static final String getAllMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Mobidity_Schema_API_Get_MorbidityData.json";
	public static final String getByMorbididtyNameMobidityDataSChema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Mobidity_Schema_API_Get_MorbidityData.json";
	public static final String getByMorbididtyTestIdMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Mobidity_Schema_API_Get_MorbidityData.json";
	public static final String getPostInvalidMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_Post_InvalidData.json";
	public static final String getInvalidMorTestIdSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_Get_InvalidMorTestId.json";

	
	public static final String postMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_Post.json";
	public static final String putMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_Put.json";
	public static final String deleteMobidityDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_Delete.json";
	public static final String deleteMobidityInvalidDataSchema = System.getProperty("user.dir") + "/src/test/resources/MobidityAPI_Schema/Morbidity_Schema_API_DeleteInvalid.json";
	
	//public static final String EXPORT_EXCEL_FILE = "C:\\Temp\\MorbidityAPI.xlsx";
	public static final String EXPORT_EXCEL_FILE =System.getProperty("user.dir") + "/src/test/java/Utils/MorbidityGetData.xls";
	public static Response get(RequestSpecification requestSpec, String endpoint, String queryString)
	{
		Logs.Log.info("********* Get Request started *************");
		Logs.Log.info(" URL : " + BASE_URL_DIETICIAN+endpoint);
		Response getMorbididtyResponse= requestSpec				
				.when()
				.get(BASE_URL_DIETICIAN + endpoint + queryString);
	
				
		getOutputResponse(getMorbididtyResponse, true);	
		Logs.Log.info("********* Get Request Ended *************");
		return getMorbididtyResponse;
	}
	
	public static Response post(RequestSpecification requestSpec, String endpoint, String requestParams)
	{
		Logs.Log.info("********* Post Request started *************");
		Logs.Log.info(" URL : " + BASE_URL_DIETICIAN+endpoint);
		Response postMorbididtyResponse= requestSpec
				.header("Content-Type","application/json")
				.body(requestParams)
				.when()
				.post(BASE_URL_DIETICIAN + endpoint);

		getOutputResponse(postMorbididtyResponse, true);	
		Logs.Log.info("********* Post Request Ended *************");
		return postMorbididtyResponse;
	}
	
	public static Response delete(RequestSpecification requestSpec, String endpoint, String deleteQueryString)
	{
		Logs.Log.info("********* Delete Request started *************");
		Logs.Log.info(" URL : " + BASE_URL_DIETICIAN+endpoint);
		Response deleteMorbididtyResponse= requestSpec
				.header("Content-Type","application/json")
				.when()
				.delete(BASE_URL_DIETICIAN + endpoint + deleteQueryString);

		getOutputResponse(deleteMorbididtyResponse, true);	
		Logs.Log.info("********* Delete Request Ended *************");
		return deleteMorbididtyResponse;
	}
	
	public static Response put(RequestSpecification requestSpec, String endpoint, String requestParams, String queryString)
	{
		
		Logs.Log.info("********* Put Request started *************");
		Logs.Log.info(" URL : " + BASE_URL_DIETICIAN+endpoint);
		Response putMorbidityResponse= requestSpec
				.header("Content-Type","application/json")
				.body(requestParams)
				.when()
				.put(BASE_URL_DIETICIAN + endpoint + queryString);

		getOutputResponse(putMorbidityResponse, true);	
		Logs.Log.info("********* Put Request Ended *************");
		return putMorbidityResponse;
	}
	
	
	@SuppressWarnings("unchecked")
	public void createJsonToExcelFile(Response response, String sheetName) throws ParseException, IOException {
		HSSFWorkbook new_workbook = new HSSFWorkbook();
		HSSFSheet sheet = new_workbook.createSheet(sheetName);		
		ResponseBody responseBody = response.getBody();
		String responseData = responseBody.asPrettyString(); // {JSON OBJ [JSON ARRAY {JSON OBJ} ]}		
		JSONParser parser = new JSONParser();
		JSONArray tempArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		Object object = (Object) parser.parse(responseData);
		jsonObj = (JSONObject) object;
		tempArray.add(jsonObj.get("Items"));		
		JSONArray jsonArray = new JSONArray();
		jsonArray = (JSONArray) tempArray.get(0);
		
		JSONObject excelHeader = new JSONObject();
		excelHeader.put("MorbidityTestName","MorbidityTestName");
		excelHeader.put("MorbidityTestUnit","MorbidityTestUnit");
		excelHeader.put("MorbidityMarkerRef","MorbidityMarkerRef");
		excelHeader.put("MorbidityName","MorbidityName");
		excelHeader.put("MorbidityTestId","MorbidityTestId");		
		createData(0, excelHeader, sheet);		
		for (int i = 0; i < jsonArray.size() ; i++) {
		  JSONObject jsonObject = (JSONObject) jsonArray.get(i);
		  createData(i+1, jsonObject, sheet); 
		}
		FileOutputStream output_file = new FileOutputStream(new File(EXPORT_EXCEL_FILE));
		new_workbook.write(output_file);
		output_file.close();
	}
	
	private void createData(int rowNum, JSONObject jsonObject, HSSFSheet sheet)  {
	   Row row = sheet.createRow(rowNum++);
	   int cellnum = 0;
	   for (Object obj : jsonObject.keySet()) {
	     Cell cell = row.createCell(cellnum++);
	     cell.setCellValue((String) jsonObject.get((String) obj));
	   }
	}
	
	
	private static void getOutputResponse(Response response, boolean isResMessage) {
		Logs.Log.info("********* Response Body Validation Started *************");
		ResponseBody responseBody = response.getBody();
		String responsemessage=responseBody.asPrettyString();
		Logs.Log.info("Response Body : ");
		Logs.Log.info(responsemessage);
		if (isResMessage && responseBody.asString() != "") {
			JsonPath jPath = responseBody.jsonPath();
			if (jPath != null && jPath.get("Message") != null) {
				Logs.Log.info(" Output Message : " + jPath.get("Message").toString());
			}
		}
		Logs.Log.info("********* Response Body Validation Ended *************");
	}

	
	public static final String USERS_EXCEL_Path = "./src/test/java/Utils/UserApi.xlsx";
	
	public static final String USERS_GET_SCHEMA = "./src/test/resources/JsonSchemas/UsersApi/UsersGetSchema.json";
	public static final String USERS_GETEMAIL_SCHEMA = "./src/test/resources/JsonSchemas/UsersApi/UserGetByEmailSchema.json";
	public static final String USERS_GETFNAME_SCHEMA = "./src/test/resources/JsonSchemas/UsersApi/UserGetByFnameSchema.json";
	public static final String USERS_POST_SCHEMA = "./src/test/resources/JsonSchemas/UsersApi/UserPostSchema.json";
	public static final String USERS_PUT_SCHEMA = "./src/test/resources/JsonSchemas/UsersApi/UserPutSchema.json";
	
	
	public static String postUserJSONData(Map<String,String> testData, int rownumber) {
		JSONObject body_json = new JSONObject();
		
		body_json.put("FirstName",testData.get("FirstName"));
		body_json.put("LastName",testData.get("LastName"));
		
		Map<String,String> address = new HashMap<String,String>();
		address.put("Address1",testData.get("Address1"));
		address.put("Address2",testData.get("Address2"));
		address.put("City",testData.get("City"));
		address.put("State",testData.get("State"));
		address.put("Country",testData.get("Country"));
		body_json.put("Address", address);
		
		body_json.put("Contact",testData.get("Contact"));
		body_json.put("Email",testData.get("Email"));
		body_json.put("FoodCategory",testData.get("FoodCategory"));
		body_json.put("Allergy",testData.get("Allergy"));
		body_json.put("LoginUsername",testData.get("LoginUsername"));
		body_json.put("Password",testData.get("Password"));
		body_json.put("UserType",testData.get("UserType"));
		body_json.put("DieticianId",testData.get("DieticianId"));

		Logs.Log.debug(body_json.toJSONString());
		return body_json.toJSONString();		
	} 
}
