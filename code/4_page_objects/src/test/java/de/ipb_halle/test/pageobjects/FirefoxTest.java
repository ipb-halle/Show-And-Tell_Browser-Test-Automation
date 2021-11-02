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
package de.ipb_halle.test.pageobjects;

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
	public void testAddGlycine() throws IOException {
		new LoginPage().login("admin", "admin")
					   .navigateToMaterials()
					   .newMaterial()
					   .selectProject("Franks Projekt")
					   .selectMaterialType("STRUCTURE")
					   .setMaterialName("Glycine")
					   .setStructure(readResourceFile("Glycine.mol"))
					   .saveMaterial();

		// assertion
		$(byText("Glycine")).shouldBe(visible);
	}

	private String readResourceFile(String fileName) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
	}
}