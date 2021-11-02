package de.ipb_halle.test.selenium.waitingdrivers;

import java.util.Map;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Response;

public class WaitingFirefoxDriver extends FirefoxDriver {
	private final long waitingTime;

	public WaitingFirefoxDriver(FirefoxProfile profile, long millis) {
		super(new FirefoxOptions().setProfile(profile));
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