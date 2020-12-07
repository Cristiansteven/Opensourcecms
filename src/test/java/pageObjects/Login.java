package pageObjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Login extends PageObjectBaseClass {
	
	String Url = "https://s1.demo.opensourcecms.com/wordpress/wp-login.php";
	
	public Login(boolean launchBrowser, boolean navigateToPage) {
		if (launchBrowser)
			initiateBrowser();
		if (navigateToPage)
			driver.get(Url);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "user_login")
	private WebElement txt_User;
	
	@FindBy(how = How.ID, using = "user_pass")
	private WebElement txt_Password;
	
	@FindBy(how = How.XPATH, using = "//h1//a[text()='Powered by WordPress']")
	private WebElement btn_WordPress;
	
	@FindBy(how = How.XPATH, using = "//input[@value='Log In']")
	private WebElement btn_LogIn;

	/**
	 * Inicio sesion en opensourcecms
	 */
	public void loginOpensourcecms() {
		try {
			assertTrue("El boton WordPress no fue encontrado", btn_WordPress.isDisplayed());

			txt_User.sendKeys("opensourcecms");
			txt_Password.sendKeys("opensourcecms");

			highlightObject(btn_LogIn);
			captureScreenShot("loginOpensourcecms");

			btn_LogIn.click();

		} catch (Exception | AssertionError e) {
			caseFailed("Hubo un error iniciado sesion en opensourcecms." + e.getMessage());
		}
	}
}
