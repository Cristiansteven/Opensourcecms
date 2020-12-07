#Author: Cristian Ariza

@Test @Automation
Feature: Automation Test
    
  @Caso1
  Scenario Outline: Authentication fail when you login with an unexisting user
  Given I sucessfully load the case data "<Case ID>"
	Given I'm in My store main page
	And I navigate to the log in page	
	When I try to login with an unexisting user
	Then An authentication failed error message appear
	
	Examples:
	|Case ID|
	|1|
	|2|
	
	@Caso2
	Scenario: Register a new user
	Given I sucessfully load the case data "3"
	Given I'm in My store main page
	And I navigate to the log in page
	When I try to register an unexisting user
	Then Welcome account appears message appear
	
	
	@Caso3
	Scenario: Add a product to the whislist
	Given I sucessfully load the case data "4"
	Given I'm on the main page of my store 
	When you add articles the word
	Then the product added to the list should appear
	
	@Caso4
	Scenario: Search a product and validate its data
	Given I sucessfully load the case data "5"
	Given I'm on the home page
	When Search articles by the word
	Then the web application should indicate a result
	
	@Case5
	Scenario: Add several products to the cart
	Given I sucessfully load the case data "6"
	Given I'm on the main page of my store 
	When you add several articles the word
	Then the products should appear on the list.