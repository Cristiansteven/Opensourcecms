package pageObjects;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import utilities.ConfigFileReader;

public class PageObjectBaseClass {

	public static WebDriver driver = null;
	public static ConfigFileReader configFileReader = new ConfigFileReader();
	public static Screen screen;

	/**
	 * This method initiates the browser set in the Configuration file.
	 * @return True or false depending on the outcome
	 */
	public static boolean initiateBrowser() {
		try {
			switch (configFileReader.getStringPropery("Browser")) {
			case "IE":
				System.setProperty("webdriver.ie.driver", configFileReader.getDriverPath());
				driver = new InternetExplorerDriver();
				break;
			case "FF":
				System.setProperty("webdriver.gecko.driver", configFileReader.getDriverPath());
				driver = new FirefoxDriver();
				break;
			case "CH":
				System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
				driver = new ChromeDriver();
				break;
			default:
				System.setProperty("webdriver.gecko.driver", configFileReader.getDriverPath());
				driver = new FirefoxDriver();
				break;
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(configFileReader.getLongPropery("Wait"), TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Start the sikuli Screen driver, and set the path for searching the images.
	 * 
	 * @param imagePath
	 * @return
	 */
	public static boolean initiateSikuli(String imagePath) {
		try {
			screen = new Screen();
			screen.setAutoWaitTimeout(configFileReader.getLongPropery("SikuliWait"));
			ImagePath.add(imagePath);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Perform the windows authentication for firefox and chrome, based on images
	 * @return True or false depending on the outcome
	 */
	public static boolean windowsBrowserAuthentication() {
		String username_txtPath = "";
		String password_txtPath = "";
		String submit_btnPath = "";
		try {
			Screen screen = new Screen();
			switch (configFileReader.getStringPropery("Browser")) {
			case "FF":
				username_txtPath = "/Firefox/Username.png";
				password_txtPath = "/Firefox/Password.png";
				submit_btnPath = "/Firefox/Ok_btn.png";
				break;
			case "CH":
				username_txtPath = "/Chrome/Username.png";
				password_txtPath = "/Chrome/Password.png";
				submit_btnPath = "/Chrome/SignIn_btn.png";
				break;
			case "IE":
				return true;
			default:
				break;
			}
			ImagePath.add("src//test//resources//AutomationByImagesRepository//WindowsAuthentication");
			screen.type(username_txtPath, configFileReader.getStringPropery("WindowsUsername"));
			screen.type(password_txtPath, configFileReader.getStringPropery("WindowsPassword"));
			screen.click(submit_btnPath);

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Change the current window to the one I want to work in
	 * 
	 * @param windowTitle
	 *            tile of the window that I want to switch to
	 */
	public static boolean switchToBrowserWindow(String windowTitle) {
		try {
			String subWindowHandler = null;
			Set<String> handles = driver.getWindowHandles(); // get all window handles
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()) {
				subWindowHandler = iterator.next();
				driver.switchTo().window(subWindowHandler);
				if (driver.getTitle().equals(windowTitle)) {
					driver.manage().window().maximize();
					return true;
				}
			}
			System.out.println("The window: " + windowTitle + ", was not found.");
			return false;
		} catch (Exception e) {
			System.out.println("Error trying to switch to the window " + windowTitle + ". " + e.getMessage());
			return false;
		}
	}

	/**
	 * Set the Test case as failed, logs a message for indicate the error and close
	 * the browser windows.
	 * 
	 * @param message
	 */
	public static void caseFailed(String message) {
		System.out.println();
		System.out.println(
				"###################################################### ERROR ######################################################");
		System.out.println("Error in the method: " + Thread.currentThread().getStackTrace()[1] + " - "
				+ Thread.currentThread().getStackTrace()[2]);
		System.out.println(message);
		System.out.println(
				"###################################################################################################################");
		System.out.println();
		endBrowserSession();
		Assert.fail();
	}

	/**
	 * Highlights the object in the page
	 * 
	 * @since 16/07/2018
	 */
	public static void highlightObject(WebElement object) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute('style', 'background: #e0ebeb; border: 2px solid blue;');",
					object);
		} catch (Exception e) {
			System.out.println("Was not possible to highligth the object. " + e.getMessage());
		}
	}

	/**
	 * Scroll down until the object in the web page is visible
	 */
	public static void scrollToObject(WebElement object) {
		try {
			String[] values;
			String strY;
			String scroll;
			int intY = 0;
			values = object.getLocation().toString().split(",");
			strY = values[1].toString().replace(')', ' ');
			intY = Integer.parseInt(strY.toString().trim());
			intY = intY - 500;
			scroll = "window.scrollBy(0," + intY + ")";
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(scroll, "");
		} catch (Exception e) {
			System.out.println("Was not possible to scroll to the object. " + e.getMessage());
		}
	}

	/**
	 * Waits until WebElement is available
	 */
	public boolean waitElement(WebElement object) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(object));
			return true;
		} catch (Exception e) {
			System.out.println(">>> Was not possible to wait the object. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Closes the current browser session
	 */
	public static void endBrowserSession() {
		try {
			driver.close();
			if (driver != null)
				driver.quit();
		} catch (Exception e) {
			System.out.println(">>> Occurred an error in the function endBrowserSession. " + e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public static void captureScreenShot(String nombreCaptura) {
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination
			File DestFile = new File("src//test//resources//data//" + nombreCaptura + ".png");

			// Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);

		} catch (IOException e) {
			System.out.println("Hubo un error intentado guardar la captura de pantalla"+ e.getMessage());
		}
	}
}
