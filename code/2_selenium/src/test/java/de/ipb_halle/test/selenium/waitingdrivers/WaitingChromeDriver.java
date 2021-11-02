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