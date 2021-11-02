# Browser tests with Selenium WebDriver

Requirements:
* [download the web driver(s) and them to PATH](https://www.selenium.dev/documentation/getting_started/installing_browser_drivers/) (Note for Chrome/Chromium: WebDriver version should match with your own browser installation)

Run tests with maven:
* all tests: `mvn clean test`
* single test: `mvn clean test -Dtest=ChromeTest`