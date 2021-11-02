package de.ipb_halle.test.selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FirefoxTest {
	@Before
	public void setUp() {
		//System.setProperty("webdriver.chrome.driver", "/home/fllocal/WebDriver/geckodriver");
		System.setProperty("selenide.browser", "Firefox");

		clearBrowserCookies();
		open("https://localhost");
	}

	@Ignore("Firefox fails to start")
	@Test
	public void testAddCaffeine() throws IOException {
		// Login
		$("input[name=\"input_logInFormId:loginLogin\"]").setValue("admin");
		$("input[name=\"input_logInFormId:loginPasswd\"]").setValue("admin");
		$("button[name=\"logInFormId:loginCmdBtn\"]").click();

		// Enter Materials
		$(byText("LIMS")).hover(); // Firefox ESR 78 fails here
		$(byText("Materials")).click();

		// Add a material
		$(byText("Create new material")).click();

		// Select project
		$("select[name=\"materialEditForm:j_id_1p_cInner\"]").selectOption(1);

		// Select material type
		$("select[name=\"materialEditForm:j_id_1p_xInner\"]").selectOption("STRUCTURE");

		// Name field in first tab
		$("[placeholder=\"Please insert name\"]").click();
		$("[placeholder=\"Please insert name\"]").setValue("Caffeine");

		// Open structure tab
		$(byText("Structure information")).click();

		// insert molecular structure from molfile
		String molfile = readResourceFile("Caffeine.mol");
		executeJavaScript("structurePlugin.then(plugin => plugin.setMolecule(\"" + escape(molfile) + "\"));");

		// Create material
		$(byText("Create")).click();

		// assertion
		$(byText("Caffeine")).shouldBe(visible);
	}

	private String escape(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}

	private String readResourceFile(String fileName) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
	}
}