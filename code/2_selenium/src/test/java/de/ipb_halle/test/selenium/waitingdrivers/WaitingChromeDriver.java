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
package de.ipb_halle.test.selenium.waitingdrivers;

import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Response;

public class WaitingChromeDriver extends ChromeDriver {
	private final long waitingTime;

	public WaitingChromeDriver(ChromeOptions options, long millis) {
		super(options);
		waitingTime = millis;
	}

	// from https://sqa.stackexchange.com/a/13359
	@Override
	protected Response execute(String driverCommand, Map<String, ?> parameters) {
		try {
			Thread.sleep(waitingTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return super.execute(driverCommand, parameters);
	}
}