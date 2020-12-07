#Author: Cristian Ariza
@Automation
Feature: Automatizacion Opensourcecms

  @caso1
  Scenario: test
	Given Inicio sesion en Opensourcecms
	When Ingreso al modulo de Post
	And Creo un nuevo Post
	Then Deberia ser capaz de crear un Post 