package com.shivamrathore.BankingProject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class App extends Util {

	void openBaseUrl() {
		driver = new ChromeDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
	}

	public static void main(String[] args) {

		App ob = new App();
		ob.initialize();
		// ob.openBaseUrl();

		if (!(ob.readFromExcel)) {

			if (ob.login(ob.userName, ob.password)) {
				System.out.println("Login Successful");
			} else {
				System.out.println("Loggin Unsuccessful");
			}
		} else {
			System.out.println("Excel");
			ArrayList<Record> records = new ArrayList<Record>(ob.loadExcel());
			for (int i = 0; i < records.size(); i++) {
				if (ob.login(records.get(i).getUserName(), records.get(i).getPassword())) {
					records.get(i).setResult("Pass");
				} else {
					records.get(i).setResult("Fail");
				}
			}
			System.out.println("With Result : " + records);
			ob.writeExcel(records);
		}

	}

	private boolean login(String userName, String password) {
		openBaseUrl();
		driver.findElement(By.xpath(userIdXpath)).sendKeys(userName);
		driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
		action.moveToElement(driver.findElement(By.xpath(loginXpath))).click().perform();

		try {
			if (driver.getTitle().equalsIgnoreCase(loginTitle)) {
				// System.out.println("Login Successful");

				return true;
			} else {
				return false;
			}
		} catch (UnhandledAlertException e) {
			System.out.println("Unhandled alert at this stage usually signifies incorrect credentials (UserName : "
					+ userName + " )");
			return false;
		} finally {
			driver.quit();
		}

	}
}
