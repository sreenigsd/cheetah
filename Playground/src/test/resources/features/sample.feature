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
@login @regression @smoke @training
Feature: Login Feature
  This feature is to test the login functionality

	Background: Background title
	describe 
	Given I launch the browser URL "www.google.com" 
	
	@google
	Scenario: google
	Then I enter the query as "automation"
	
	@failed-login
	Scenario: Title of your scenario
  	Description of the scenario
    Then I enter the username as "asdasd"
    Then I enter the password as "adfsvg3"
    Then I click the Login button
    Then the site should not successfully login

	@successful-login
  Scenario Outline: Title of your scenario
  	Description of the scenario
    Then I enter the username as <username> 
    And I enter the password as <password>
    And I click the Login button
    Then the site should successfully login
   
    
   Examples: 
      | username  | password |
      | abcde     |  w2123   |
      | name1     |  asfqa   | 
