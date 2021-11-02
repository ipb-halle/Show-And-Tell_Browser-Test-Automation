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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;

import de.ipb_halle.test.pageobjects.util.Selectors;

public class MaterialsEditPage {
	private static final String PROJECT_SELECT = Selectors.cssSelector("select", "materialsEdit:project");
	private static final String MATERIALTYPE_SELECT = Selectors.cssSelector("select", "materialsEdit:materialType");
	private static final String MATERIALNAMES_TAB = Selectors.cssSelector("materialsEdit:namesTab");
	private static final String MATERIALNAMES_INPUT = Selectors.cssSelector("input", "materialsEdit:nameInput");
	private static final String STRUCTUREINFORMATION_TAB = Selectors.cssSelector("materialsEdit:structureinformationTab");
	private static final String SAVEMATERIAL_BUTTON = Selectors.cssSelector("materialsEdit:saveMaterial");

	public MaterialsEditPage selectProject(String name) {
		$(PROJECT_SELECT).selectOption(name);

		return this;
	}

	public MaterialsEditPage selectMaterialType(String name) {
		$(MATERIALTYPE_SELECT).selectOption(name);

		return this;
	}

	public MaterialsEditPage setMaterialName(String name) {
		$(MATERIALNAMES_TAB).click();
		$(MATERIALNAMES_INPUT).setValue(name);

		return this;
	}

	public MaterialsEditPage setStructure(String molfile) {
		$(STRUCTUREINFORMATION_TAB).click();
		sleep(100); // Chrome cannot find structurePlugin without this delay
		executeJavaScript("structurePlugin.then(plugin => plugin.setMolecule(\"" + escape(molfile) + "\"));");

		return this;
	}

	public MaterialsOverviewPage saveMaterial() {
		$(SAVEMATERIAL_BUTTON).click();

		return new MaterialsOverviewPage();
	}

	private String escape(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}
}