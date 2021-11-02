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

import de.ipb_halle.test.pageobjects.util.Selectors;

public class LoginPage {
	private static final String LOGINNAME_INPUT = Selectors.cssSelector("input", "login:loginName_input");
	private static final String PASSWORD_INPUT = Selectors.cssSelector("input", "login:password_input");
	private static final String LOGIN_BUTTON = Selectors.cssSelector("login:loginButton");

	public SearchPage login(String loginName, String password) {
		$(LOGINNAME_INPUT).setValue(loginName);
		$(PASSWORD_INPUT).setValue(password);
		$(LOGIN_BUTTON).click();

		return new SearchPage();
	}
}