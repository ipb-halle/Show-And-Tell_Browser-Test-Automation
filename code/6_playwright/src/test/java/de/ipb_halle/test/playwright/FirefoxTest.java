/*
 * Copyright 2021 Leibniz-Institut für Pflanzenbiochemie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package de.ipb_halle.test.playwright;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FirefoxTest {
	private BrowserContext context;
	private Page page;

	@Before
	public void setUp() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
		context = browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true).setLocale("de-DE"));
		page = context.newPage();
		page.navigate("http://localhost");
	}

	@After
	public void closeContext() {
		context.close();
	}

	@Test
	public void testAddEthanol() throws IOException {
		// Login
		page.click("input[name=\"input_logInFormId:loginLogin\"]");
		page.fill("input[name=\"input_logInFormId:loginLogin\"]", "admin");
		page.press("input[name=\"input_logInFormId:loginLogin\"]", "Tab");
		page.fill("input[name=\"input_logInFormId:loginPasswd\"]", "admin");
		page.click("button[name=\"logInFormId:loginCmdBtn\"]");

		// Enter Materials
		page.hover("text=LIMS");
		page.click("text=Materialien");

		// Add a material
		page.click("text=Neues Material erstellen");

		// Select project
		page.selectOption("select[name=\"materialEditForm:j_id_1p_cInner\"]", "1");

		// Select material type
		page.selectOption("select[name=\"materialEditForm:j_id_1p_xInner\"]", "STRUCTURE");

		// Name field in first tab
		page.click("[placeholder=\"Bitte Namen eingeben\"]");
		page.fill("[placeholder=\"Bitte Namen eingeben\"]", "Methanol");

		// Open structure tab
		page.click("text=Strukturinformationen");

		// insert molecular structure from molfile
		String molfile = readResourceFile("Methanol.mol");
		page.evaluate("structurePlugin.then(plugin => plugin.setMolecule(\"" + escape(molfile) + "\"));");

		// Create material
		page.click("text=Erstellen");

		// assertion
		page.click("text=Methanol");
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