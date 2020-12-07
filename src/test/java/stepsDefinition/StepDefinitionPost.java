package stepsDefinition;

import cucumber.api.java8.En;
import pageObjects.Post;

public class StepDefinitionPost implements En{

	Post post;
	
	public StepDefinitionPost() {
		
		When("Ingreso al modulo de Post", () -> {
			post = new Post();
			post.irAmoduloEnopensourcecms("Post");
		});
		
		When("Creo un nuevo Post", () -> {
			post = new Post();
			post.clicEnAddNew();
		});
		
		When("Deberia ser capaz de crear un Post", () -> {
			post = new Post();
			post.rellenarCamposParaNuevoPost();
			post.publicarUnPost();
			post.verificarMensaje("Post published.");
			
		});
	}

}
