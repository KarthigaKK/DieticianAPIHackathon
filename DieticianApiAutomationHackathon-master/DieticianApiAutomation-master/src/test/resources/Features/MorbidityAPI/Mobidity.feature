@All
Feature: As a tester, I want to test Morbididty API for all Http methods
  
  @Smoke @Regression
  Scenario: Test the Get request for all Morbidity Data of Morbidity API
    Given Morbididty API up and running for authorized user
    When User made Get request with endpoint as "/api/Morbidity"
    Then Morbidity API should return all morbididty data with 200 status code
    Then Validate response body MorbididtyTestName as "\"MorbidityTestName\": \"Blood glucose\"" field
    Then User validated with response schema
  @Smoke @Regression
  Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityName
    Given Morbididty API up and running for authorized user
    When User made Get request by passing MorbididtyName from given sheetname "<SheetName>" and rownumber <RowNumber> with endpoint as "/api/Morbidity/" 
    Then Morbidity API should return morbididty details with 200 status code
    Then Validate response body MorbididtyTestId as "<ValidateBody>" field
    Then Received Morbidity details are validated with response schema
    
    Examples: 
      | SheetName  | RowNumber | ValidateBody |
      | Get |     0 | '"MorbidityTestId": "DIA_BLO"' |
      | Get |     1 | '"MorbidityTestId": "HYPO_T3"' |
  @Smoke @Regression
  Scenario Outline: Test the Get request to retrieve specific Morbididty data using invalid MorbidityTestId
    Given Morbididty API up and running for authorized user
    When User made Get request by passing invalid MorbidityTestId from given sheetname "<SheetName>" and rownumber <RowNumber> with endpoint as "/api/Morbidity/" 
    Then Morbidity API should return morbididty details with 500 status code
    Then Validate response body invalid message as "<ValidateBody>" field
    Then Get Morbidity data with invalid message using response schema
    
    Examples: 
      | SheetName  | RowNumber | ValidateBody |
      | Get |     0 | '"message": "Internal Server Error"' |
      | Get |     1 | '"message": "Internal Server Error"' |
  @Smoke @Regression    
  Scenario Outline: Test the Get request to retrieve specific Morbididty data using MorbidityTestId
    Given Morbididty API up and running for authorized user
    When User made Get request by passing MorbidityTestId from given sheetname "<SheetName>" and rownumber <RowNumber> with endpoint as "/api/Morbidity/" 
    Then Morbidity API should return morbididty details with 500 status code
    Then Validate response body MorbididtyName as "<ValidateBody>" field
    Then Received Morbidity details validated using response schema
    
    Examples: 
      | SheetName  | RowNumber | ValidateBody |
      | Get |     2 | '"message": "Internal Server Error"' |
      | Get |     3 | '"message": "Internal Server Error"' |
  @Smoke @Regression 
  Scenario Outline: Test the post request for Mobididty API
    Given Morbididty API up and running for authorized user
    When User sends the post request with valid inputs from given sheetname "<SheetName>" and endpoint as "/api/Morbidity/"
    Then New user record is successfully created with 200 status code
    Then Validate response body post Messages
    Then Mobidity API post request validated using response schema
        
    Examples: 
      | SheetName  |
      | Post |        

  @Smoke @Regression
  Scenario Outline: Test the update request for Morbidity API
    Given Morbididty API up and running for authorized user
    When User sends put request from given "<SheetName>" and rownumber <RowNumber> and endpoint as "/api/Morbidity/"
    Then User record is successfully updated with 200 status code
    Then Validate response body update as "<ValidateBody>" field
    Then Mobidity API put request validated using response schema

    Examples: 
      | SheetName  | RowNumber | ValidateBody |
      | Put |     0 | '"Message": "Successfully Updated."' |
      | Put |     1 | '"Message": "Successfully Updated."' |
      | Put |     2 | '"Message": "Successfully Updated."' |
      | Put |     3 | '"Message": "Successfully Updated."' |
      | Put |     4 | '"Message": "Successfully Updated."' |
      | Put |     5 | '"Message": "Successfully Updated."' |
    
	@Smoke @Regression  
  Scenario Outline: Test the delete request for Morbidity API
    Given Morbididty API up and running for authorized user
    When User sends delete request with valid input and endpoint as "/api/Morbidity/" from given "<SheetName>" and rownumber <RowNumber>
    Then User record is successfully updated with 200 status code
    Then Validate response body delete as "<ValidateBody>" field
    Then Mobidity API delete request validated using response schema "<ValidateBody>"

    Examples: 
      | SheetName  | RowNumber |  ValidateBody |
      | Delete |     0 | '"Message": "Successfully Deleted."' |      
      | Delete |     1 | '"Message": "Successfully Deleted."' |
      | Delete |     2 | '"Message": "Successfully Deleted."' |
      | Delete |     3 | '"Message": "Successfully Deleted."' |
      | Delete |     4 | '"Message": "Successfully Deleted."' |
      | Delete |     5 | '"Message": "Successfully Deleted."' |
      | Delete |     6 | '"Message": "Successfully Deleted."' |
      | Delete |     7 | '"Message": "Successfully Deleted."' |
      | Delete |     8 | '"Message": "Successfully Deleted."' |
      | Delete |     9 | '"Message": "Successfully Deleted."' |
      | Delete |     10 | '"Message": "Successfully Deleted."' |
      | Delete |     11 | '"Message": "Successfully Deleted."' |
      | Delete |     12 | '"Message": "Successfully Deleted."' |
      | Delete |     13 | '"Message": "Successfully Deleted."' |
      | Delete |     14 | '"Message": "Successfully Deleted."' |
      | Delete |     15 | '"Message": "Successfully Deleted."' |
      | Delete |     16 | '"Message": "Successfully Deleted."' |
      | Delete |     17 | '"Message": "Successfully Deleted."' |
      | Delete |     18 | '"Message": "Successfully Deleted."' |


      