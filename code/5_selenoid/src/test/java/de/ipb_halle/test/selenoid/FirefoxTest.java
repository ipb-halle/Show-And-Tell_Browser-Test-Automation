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
package de.ipb_halle.test.selenoid;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.Configuration;

import de.ipb_halle.test.pageobjects.LoginPage;

public class FirefoxTest {
	@Before
	public void setUp() {
		Configuration.remote = "http://localhost:4444/wd/hub";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "firefox");
		capabilities.setCapability("browserVersion", "93.0");
		capabilities.setCapability("enableVNC", true);
		Configuration.browserCapabilities = capabilities;

		clearBrowserCookies();

		// the URL from the perspective of the docker container running the browser test
		open("https://nwc04177.ipb-halle.de");
	}

	@Test
	public void testAddAcetone() throws IOException {
		new LoginPage().login("admin", "admin")
					   .navigateToMaterials()
					   .newMaterial()
					   .selectProject("Franks Projekt")
					   .selectMaterialType("STRUCTURE")
					   .setMaterialName("Acetone")
					   .setStructure(readResourceFile("Acetone.mol"))
					   .saveMaterial();

		// assertion
		$(byText("Acetone")).shouldBe(visible);
	}

	private String readResourceFile(String fileName) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
	}
}