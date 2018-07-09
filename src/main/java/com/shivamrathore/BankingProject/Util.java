package com.shivamrathore.BankingProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Util {
	class Record {
		String userName;
		String password;
		String result;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getUserName() {
			return userName;
		}

		void setUserName(String userName) {
			this.userName = userName;
		}

		@Override
		public String toString() {
			return "Record [userName=" + userName + ", password=" + password + ", result=" + result + "]";
		}

	}

	WebDriver driver;
	Actions action;
	String baseUrl;
	String userName;
	String password;
	String driverLocation;
	String userIdXpath;
	String passwordXpath;
	String loginXpath;
	boolean readFromExcel;
	String excelFile;
	final String loginTitle = "Guru99 Bank Manager HomePage";

	void initialize() {

		loadConfiguration();
		System.setProperty("webdriver.chrome.driver", driverLocation);

	}

	List<Record> loadExcel() {
		List<Record> records = new ArrayList<Record>();
		Record record = null;
		FileInputStream file;
		Workbook workbook = null;
		try {
			file = new FileInputStream(new File(excelFile));
			workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				record = new Record();
				record.setUserName(row.getCell(0).toString());
				record.setPassword(row.getCell(1).toString());
				System.out.println(record);

				records.add(record);
			}

			System.out.println("Load Excel returns : " + records);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return records;
	}

	void writeExcel(ArrayList<Record> records) {

		FileInputStream file;
		XSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(new File(excelFile));
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				Cell cell = row.createCell(2);
				cell.setCellValue(records.get(i - 1).getResult());

			}

			file.close();

			FileOutputStream outFile = new FileOutputStream(excelFile);
			workbook.write(outFile);
			workbook.close();
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			excelFile = props.getProperty("Input_Excel");
			if (props.getProperty("Read_From_Excel").equalsIgnoreCase("Y")) {
				readFromExcel = true;
			}
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
