package stepsDefinition;

import cucumber.api.java8.En;
import pageObjects.Login;

public class StepDefinition implements En {

	Login login;

	public StepDefinition() {

		Given("Inicio sesion en Opensourcecms", () -> {
			login = new Login(true, true);
			login.loginOpensourcecms();
		});

	}
}