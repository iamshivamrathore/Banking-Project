package com.shivamrathore.BankingProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Util {

	WebDriver driver;
	Actions action;
	String baseUrl;
	String userName;
	String password;
	String driverLocation;
	String userIdXpath;
	String passwordXpath;
	String loginXpath;

	final String loginTitle = "Guru99 Bank Manager HomePage";

	void initialize() {
		loadConfiguration();
		System.setProperty("webdriver.chrome.driver", driverLocation);
		driver = new ChromeDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	boolean loadConfiguration() {

		Properties props = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			props.load(input);
			baseUrl = props.getProperty("Base_URL");
			userName = props.getProperty("User_Name");
			password = props.getProperty("Password");
			driverLocation = props.getProperty("Driver_Location");
			userIdXpath = props.getProperty("UserID_xpath");
			passwordXpath = props.getProperty("Password_xpath");
			loginXpath = props.getProperty("Login_xpath");

			// System.out.println("URL : " + baseUrl);
			// System.out.println("User : " + userName);
			// System.out.println("Password : " + password);
			// System.out.println("Driver : " + driverLocation);
			// System.out.println("User Xpath : "+userIdXpath);
			// System.out.println("Pass Xpath : "+passwordXpath);
			// System.out.println("Loggin Btn Xpath : "+loginXpath);

			return true;
		} catch (IOException ioex) {
			System.out.println("Error while opening configuration file");
			return false;
		} finally {
			try {
				input.close();

			} catch (IOException e) {
				return false;
			}
		}
	}
}
