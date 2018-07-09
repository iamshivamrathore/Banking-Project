package com.shivamrathore.BankingProject;

import org.openqa.selenium.By;

public class App extends Util {

	void openBaseUrl() {
		driver.get(baseUrl);
		driver.manage().window().maximize();
	}

	public static void main(String[] args) {

		App ob = new App();
		ob.initialize();
		ob.openBaseUrl();
		if (ob.login()) {
			System.out.println("Login Successful");
		} else {
			System.out.println("Loggin Unsuccessful");
		}

	}

	private boolean login() {
		driver.findElement(By.xpath(userIdXpath)).sendKeys(userName);
		driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
		action.moveToElement(driver.findElement(By.xpath(loginXpath))).click().perform();

		if (driver.getTitle().equalsIgnoreCase(loginTitle)) {
			// System.out.println("Login Successful");
			return true;
		}

		return false;
	}
}
