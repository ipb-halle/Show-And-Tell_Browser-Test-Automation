package de.ipb_halle.test.selenium;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlakyTest {
	private WebDriver driver;
	private JavascriptExecutor js;

	@Before
	public void setUp() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		driver = new FirefoxDriver(new FirefoxOptions().setProfile(profile));
		js = (JavascriptExecutor) driver;
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	// This test will most probably fail.
	@Test
	public void testAddBenzene() throws IOException {
		driver.get("https://localhost");

		// Login
		driver.findElement(By.id("input_logInFormId:loginLogin")).click();
		driver.findElement(By.id("input_logInFormId:loginLogin")).sendKeys("admin");
		driver.findElement(By.id("input_logInFormId:loginPasswd")).sendKeys("admin");
		driver.findElement(By.id("logInFormId:loginCmdBtn")).click();

		// Enter Materials
		{
			WebElement element = driver.findElement(By.id("dtLj_id_s:j_id_11"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		driver.findElement(By.linkText("Materialien")).click();

		// Add a material
		driver.findElement(By.id("materialForm:j_id_1p_3i")).click();

		// Select project
		driver.findElement(By.id("materialEditForm:j_id_1p_cInner")).click();
		{
			WebElement dropdown = new WebDriverWait(driver, 10)
					.until(ExpectedConditions.elementToBeClickable(By.id("materialEditForm:j_id_1p_cInner")));
			dropdown.findElement(By.xpath("//option[. = 'Franks Projekt']")).click();
		}

		// Select material type
		driver.findElement(By.id("materialEditForm:j_id_1p_xInner")).click();
		{
			WebElement dropdown = new WebDriverWait(driver, 10)
					.until(ExpectedConditions.elementToBeClickable(By.id("materialEditForm:j_id_1p_xInner")));
			dropdown.findElement(By.xpath("//option[. = 'STRUCTURE']")).click();
		}

		// Name field in first tab
		driver.findElement(By.cssSelector("#materialEditForm\\3Aj_id_1p_xInner > option:nth-child(1)")).click();
		driver.findElement(By.id("input_materialEditForm:j_id_1p_14:materialNameTable:0:j_id_1p_19")).click();
		driver.findElement(By.id("input_materialEditForm:j_id_1p_14:materialNameTable:0:j_id_1p_19"))
				.sendKeys("Benzene");

		// Open structure tab
		driver.findElement(By.linkText("Strukturinformationen")).click();

		// insert molecular structure from molfile
		String molfile = readResourceFile("Benzene.mol");
		js.executeScript("structurePlugin.then(plugin => plugin.setMolecule(\"" + escape(molfile) + "\"));");

		// Create material
		driver.findElement(By.id("materialEditForm:j_id_1p_s")).click();

		// assertion
		driver.findElement(By.xpath("//span[contains(.,\'Benzene\')]"));
	}

	private String escape(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}

	private String readResourceFile(String fileName) throws IOException {
		return IOUtils.toString(FlakyTest.class.getResourceAsStream(fileName), "UTF-8");
	}
}