#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Validating Recipe API Request
  As a user, I must able to access data in Recipe API

  #Background: User is Logged In
  #Given User is in Url http://127.0.0.1:5000/
  #When Submit username and password
  #Then Successfuly logged In
  @RecipeAPI @Smoke @Regression
  Scenario: User access request without login
    Given User is in Url Url/api/
    When GET request is made in Url "api/Recipes/" without login
    Then The request returned with Response code 401 Unauthorised Access

  @Smoke @Regression
  Scenario: User Retrieve Recipes Data
    Given Dietician API is up and running for authorized user
    When GET request is made to enpoint as "api/Recipes/"
    Then The requested Recipes data is returned with Response code 200

  @Smoke @Regression
  Scenario Outline: User Retrieve Recipe Data based on Food Category
    Given Dietician API is up and running for authorized user
    When GET request is made to with endpoint as "api/Recipes/RecipeFoodCategory=" with input RecipeFoodCategory from excel sheetname "<SheetName>" and rownumber <RowNumber>
    Then The requested data with input RecipeFoodCategory is returned with Response code 200 and validate "<Validate>"

    Examples: 
      | SheetName | RowNumber | Validate   |
      | GETRFC    |         0 | vegetarian |

  # | GETRFC    |         1 |
  @Smoke @Regression
  Scenario Outline: User Retrieve Recipe Data based on Recipe Type
    Given Dietician API is up and running for authorized user
    When GET request is made to endpoint "api/Recipes/RecipeType=" with input RecipeType from excel sheetname "<SheetName>" and rownumber <RowNumber>
    Then The requested data with input RecipeType is returned with Response code 200 and validate "<Validate>"

    Examples: 
      | SheetName | RowNumber | Validate  |
      | GETRT     |         0 | Lunch     |
      | GETRT     |         1 | Breakfast |

  @Smoke @Regression
  Scenario Outline: User Retrieve Recipe Data based on Recipe Ingredient
    Given Dietician API is up and running for authorized user
    When GET request is made to endpoint "api/Recipes/RecipeIngredient=" with input RecipeIngredient from excel sheetname "<SheetName>" and rownumber <RowNumber>
    Then The requested data with input RecipeIngredient is returned with Response code 200 and validate "<Validate>"

    Examples: 
      | SheetName | RowNumber | Validate |
      | GETRI     |         0 | apple    |
      | GETRI     |         1 | flour    |

  @Smoke @Regression
  Scenario Outline: User Retrieve Recipe Data based on Recipe Nutrient
    Given Dietician API is up and running for authorized user
    When GET request is made to endpoint "api/Recipes/RecipeNutrient=" with input RecipeNutrient from excel sheetname "<SheetName>" and rownumber <RowNumber>
    Then The requested data with input RecipeNutrient is returned with Response code 200 and validate "<Validate>"

    Examples: 
      | SheetName | RowNumber | Validate  |
      | GETRNP    |         0 | Energy 86 |
      | GETRNP    |         1 | Protein 3 |

  @Smoke @Regression
  Scenario Outline: User Retrieve Recipe Data based on Recipe Nutrient Negative
    Given Dietician API is up and running for authorized user
    When GET request is made to endpoint "api/Recipes/RecipeNutrient=" with invalid input RecipeNutrient from excel sheetname "<SheetName>" and rownumber <RowNumber>
    Then The request returned with Response code 200 with msg Item Not Found and validate "<Validate>"

    Examples: 
      | SheetName | RowNumber | Validate |
      | GETRNN    |         0 | energy   |
      | GETRNN    |         1 | protien  |
