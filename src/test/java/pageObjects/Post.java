package pageObjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Post extends PageObjectBaseClass {

	public Post() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = "//label[text()='Add title']/ancestor::div[@class='editor-post-title']//textarea")
	private WebElement txt_AddTitle;

	@FindBy(how = How.XPATH, using = "//textarea[text()='Start writing or type / to choose a block']")
	private WebElement txt_StartWritingOrType;

	@FindBy(how = How.XPATH, using = "//button[@aria-label='Disable tips']//*[@aria-hidden='true']")
	private WebElement btn_MessageAlert;

	@FindBy(how = How.XPATH, using = "//div[@class='edit-post-header__settings']//*[contains(text(),'Publish')]")
	private WebElement btn_Publish;

	private static By by_btn_IrAModulo(String moduloParaRevisar) {
		return By.xpath("//ul[@id='adminmenu']//div[text()='" + moduloParaRevisar + "']");
	}

	private static By by_btn_AddNew(String botonParaRevisar) {
		return By.xpath("//div[@class='wrap']//a[text()='" + botonParaRevisar + "']");
	}

	private static By by_btn_Published(String botonParaRevisar) {
		return By.xpath("//div[@class='components-notice__content' and text()='" + botonParaRevisar + "']");
	}

	/**
	 * Ir a modulo en opensourcecms
	 * 
	 * @param moduloParaRevisar
	 */
	public void irAmoduloEnopensourcecms(String moduloParaRevisar) {
		try {
			waitElement(driver.findElement(by_btn_IrAModulo("Posts")));

			assertTrue("El modulo Post no fue desplegado.",
					driver.findElement(by_btn_IrAModulo("Posts")).isDisplayed());
			highlightObject(driver.findElement(by_btn_IrAModulo("Posts")));

			captureScreenShot("irAmoduloEnopensourcecms");
			driver.findElement(by_btn_IrAModulo("Posts")).click();

		} catch (Exception | AssertionError e) {
			caseFailed("Hubo un error intentado ingresar en el modulo " + moduloParaRevisar + "." + e.getMessage());
		}
	}

	/**
	 * Clic en Add New
	 */
	public void clicEnAddNew() {
		try {
			highlightObject(driver.findElement(by_btn_AddNew("Add New")));
			assertTrue("El boton Post no fue encontrado", driver.findElement(by_btn_AddNew("Add New")).isEnabled());
			captureScreenShot("clicEnAddNew");
			driver.findElement(by_btn_AddNew("Add New")).click();
		} catch (Exception | AssertionError e) {
			caseFailed("Hubo un error haciendo clien en Add New" + e.getMessage());
		}
	}

	/**
	 * Crea un nuevo Post
	 */
	public void rellenarCamposParaNuevoPost() {
		try {
			if (btn_MessageAlert.isDisplayed()) {
				highlightObject(btn_MessageAlert);
				btn_MessageAlert.click();
			}
			assertTrue("El campo Add Title no fue encontrado.", txt_AddTitle.isDisplayed());
			highlightObject(txt_AddTitle);
			txt_AddTitle.sendKeys("Titulo Prueba");

			captureScreenShot("rellenarCamposParaNuevoPost");

		} catch (Exception e) {
			caseFailed("Hubo un error creando un nuevo Post." + e.getMessage());
		}
	}

	/**
	 * Publica un post
	 */
	public void publicarUnPost() {
		try {
			assertTrue("El boton Publish no esta habilitado.", btn_Publish.isEnabled());
			highlightObject(btn_Publish);
			
			captureScreenShot("publicarUnPost");
			Actions actions = new Actions(driver);
			actions.moveToElement(btn_Publish).click().perform();
			
			waitElement(btn_Publish);
			actions.moveToElement(btn_Publish).click().perform();
		} catch (Exception e) {
			caseFailed("Hubo un error publicando un nuevo Post." + e.getMessage());
		}
	}

	/**
	 * Verifica mensaje cuando es creado un post
	 * 
	 * @param mesajeParaVerificar
	 */
	public void verificarMensaje(String mesajeParaVerificar) {
		try {
			assertTrue("El mensaje de creación de un post no fue mostrado.",
					driver.findElement(by_btn_Published(mesajeParaVerificar)).isDisplayed());
			highlightObject(driver.findElement(by_btn_Published(mesajeParaVerificar)));
			captureScreenShot(mesajeParaVerificar);

			endBrowserSession();
		} catch (Exception e) {
			caseFailed("Hubo un error publicando un nuevo Post." + e.getMessage());
		}
	}
}
