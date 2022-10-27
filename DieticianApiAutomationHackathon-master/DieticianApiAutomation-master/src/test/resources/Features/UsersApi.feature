@All
Feature: UsersAPI
@Smoke @Regression
  Scenario: Validate GetUsers with wrong credentials
    Given Initilize base uri with wrong credentials
    When Get request is sent with endpoint as "/api/Users/"
    Then Verify statuscode is 401 unauthorised
    
@Smoke @Regression
  Scenario: Validate GetUsers with correct authentication
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrive data with endpoint as "/api/Users/"
    Then API should return all User records with success code 200

@Smoke @Regression
  Scenario Outline: Get request is to retrive specific user using contact
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrieve specific user data with endpoint as "/api/Users/Contact=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then API should return record for specific user with success code 200

    Examples: 
      | SheetName  | RowNumber |
      | GetContact |         0 |

@Smoke @Regression
  Scenario Outline: Get request is to retrive specific user using DieticianId
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrive specific user data by DieticianId with endpoint as "/api/Users/DieticianId=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then API should return record for specific user by DieticianId with success code 200

    Examples: 
      | SheetName      | RowNumber |
      | GetDieticianId |         0 |

@Smoke @Regression
  Scenario Outline: Get request is to retrive specific user using email
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrive specific user data by email with endpoint as "/api/Users/Email=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then API should return record for specific user by email with success code 200

    Examples: 
      | SheetName | RowNumber |
      | GetEmail  |         0 |
      
@Smoke @Regression
  Scenario Outline: Get request is to retrive specific user using FirstName
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrive specific user data by firstname with endpoint as "/api/Users/FirstName=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then API should return record for specific user by firstname with success code 200

    Examples: 
      | SheetName    | RowNumber |
      | GetFirstName |         0 |
@Smoke @Regression
  Scenario Outline: Get request is to retrive specific user using UserType
    Given UsersAPI is up and running for authorised user
    When User sends Get request to retrive specific user data by usertype with endpoint as "/api/Users/UserType=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then API should return record for specific user by usertype with success code 200

    Examples: 
      | SheetName   | RowNumber |
      | GetUserType |         0 |

@Smoke @Regression
  Scenario Outline: delete request is to delete existing user record
    Given UsersAPI is up and running for authorised user
    When User sends delete request with valid inputs and endpoint as "/api/Users/DieticianId=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then User record is successsfully deleted with successcode 200

    Examples: 
      | SheetName | RowNumber |
      | Delete    |         0 |

@Smoke @Regression
  Scenario Outline: Post request is to create new user record
    Given UsersAPI is up and running for authorised user
    When User sends post request with valid inputs and endpoint as "/api/Users/" from given sheetname "<SheetName>"
    Then New user record is created with success code 200

    Examples: 
      | SheetName | 
      | Post |

@Smoke @Regression
  Scenario Outline: Put request is to update user record
    Given UsersAPI is up and running for authorised user
    When User sends put request with valid inputs and endpoint as "/api/Users/DieticianId=" from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then User record is updated with success code 200

    Examples: 
      | SheetName | RowNumber |
      | Put       |         0 |
      | Put       |         1 |
      | Put       |         2 |
      | Put       |         3 |
      | Put       |         4 |
      | Put       |         5 |
      | Put       |         6 |
      | Put       |         7 |
