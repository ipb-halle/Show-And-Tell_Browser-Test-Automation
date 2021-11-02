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
package de.ipb_halle.test.selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class ChromeTest {
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/home/fllocal/WebDriver/chromedriver");
		System.setProperty("selenide.browser", "Chrome");

		clearBrowserCookies();
		open("https://localhost");
	}

	@Test
	public void testAddAspirin() throws IOException {
		// Login
		$("input[name=\"input_logInFormId:loginLogin\"]").setValue("admin");
		$("input[name=\"input_logInFormId:loginPasswd\"]").setValue("admin");
		$("button[name=\"logInFormId:loginCmdBtn\"]").click();

		// Enter Materials
		$(byText("LIMS")).hover();
		$(byText("Materials")).click();

		// Add a material
		$(byText("Create new material")).click();

		// Select project
		$("select[name=\"materialEditForm:j_id_1p_cInner\"]").selectOption(1);

		// Select material type
		$("select[name=\"materialEditForm:j_id_1p_xInner\"]").selectOption("STRUCTURE");

		// Name field in first tab
		$("[placeholder=\"Please insert name\"]").click();
		$("[placeholder=\"Please insert name\"]").setValue("Aspirin");

		// Open structure tab
		$(byText("Structure information")).click();

		// insert molecular structure from molfile
		String molfile = readResourceFile("Aspirin.mol");
		sleep(100); // Chrome cannot find structurePlugin without this delay
		executeJavaScript("structurePlugin.then(plugin => plugin.setMolecule(\"" + escape(molfile) + "\"));");

		// Create material
		$(byText("Create")).click();

		// assertion
		$(byText("Aspirin")).shouldBe(visible);
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