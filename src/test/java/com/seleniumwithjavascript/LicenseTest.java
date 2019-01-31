package com.seleniumwithjavascript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;
import com.seleniumwithjavascript.testbase.Base;
import com.seleniumwithjavascript.util.PuttyCommandExecution;
import com.seleniumwithjavascript.util.ReadFileNameUtil;
import com.seleniumwithjavascript.util.TestUtil;
import com.seleniumwithjavascript.util.UnzipUtil;

public class LicenseTest extends Base {
	Base baseObject;
	UnzipUtil unzipObject = new UnzipUtil();
	ReadFileNameUtil readObject = new ReadFileNameUtil();
	String userName;
	String password;
	String ip;

	public LicenseTest() {

		super();

		userName = prop.getProperty("labUserName");
		password = prop.getProperty("labPassword");
		ip = prop.getProperty("IP");
	}

	@BeforeTest

	public void init() throws InterruptedException {

		initialization();
		driver.get(prop.getProperty("laburl"));
		
		WebElement userName = driver.findElement(By.id("login:username"));
		userName.clear();
		userName.sendKeys(prop.getProperty("userName"));
		Thread.sleep(2000);

		WebElement passWord = driver.findElement(By.id("login:password"));
		passWord.clear();
		passWord.sendKeys(prop.getProperty("passWord"));

		Thread.sleep(2000);
		WebElement loginButton = driver.findElement(By.id("login:loginButton"));
		loginButton.click();
		Thread.sleep(1000);
		WebElement acceptButton = driver.findElement(By.xpath("//input[@type='submit']"));

		acceptButton.click();

		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

		WebElement arrowButton = driver.findElement(By.xpath("(//span[@class='arrow'])[2]"));
		arrowButton.click();

		WebElement showAllApplication = driver.findElement(By.xpath("//span[text()='Show all applications']"));
		showAllApplication.click();

		WebElement licenseManagerTool = driver
				.findElement(By.xpath("//span[text()='Manages licenses for network elements and software.']"));
		licenseManagerTool.click();

		ArrayList tabs = new ArrayList(driver.getWindowHandles());
		System.out.println("Total Tab Size" + tabs.size());
		driver.switchTo().window((String) tabs.get(1));

		String expectedlicenseTabText = "License Browser";

	}

	@Test(priority = 1, enabled = true)
	public void backupLicense() throws InterruptedException {
		// backUp Of All the Lisence

		// Scroll down The Window
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0,200)", "");

		// Select 25 per page

		WebElement arrowDown = driver.findElement(By.xpath("(//table//span[contains(@class,'arrow')])[5]"));
		arrowDown.click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//a[@title='25']/span[text()='25']")).click();
		Thread.sleep(1000);

		// select all the license

		WebElement checkBox = driver.findElement(
				By.xpath("//span/a[@id='licenseTabForm:TAB_SW_licenseDataTable:checkmark_selectAllCheckBox']"));

		checkBox.click();
		Thread.sleep(4000);

		// Here we have to add the code for to delete existing zip file in download
		// folder

		File file = new File("C:\\Users\\rajepath\\Downloads\\LICENSES.ZIP");
		if (file.exists()) {
			file.delete();
		}

		// click on export button

		WebElement exportButton = driver.findElement(
				By.xpath("//input[@id='licenseTabForm:TAB_SW_LicenseTogglePanel_licenseButtonForm:NEWexportButton']"));

		exportButton.click();
		Thread.sleep(2000);

		// Logic to unzip the file
		unzipObject.unzipFunction("C:\\Users\\rajepath\\Documents\\LicenseBackUp","C:\\Users\\rajepath\\Downloads\\LICENSES.ZIP\\");

	}

	@Test(priority = 2, enabled = true ,dependsOnMethods="backupLicense")
	public void deleteLicense() throws InterruptedException, JSchException, FileNotFoundException {

		// Delete all the license

		String actaulText = driver.findElement(By.xpath("//h1[@class='applicationTitle licSetHeader']")).getText();

		// Assert.assertEquals(expectedlicenseTabText, actaulText);

		List<WebElement> element = driver.findElements(
				By.xpath("//table[contains(@class,'rf-dt lic-full-width-table lic-selectable')]/tbody/tr/td[5]/a"));

		for (WebElement e : element) {
			String licenseSerialNumber = e.getAttribute("id");
			// System.out.println("licenseSerialNumber "+licenseSerialNumber);

			String licenseNumbers = licenseSerialNumber.replaceAll("[^0-9]", ""); //
			System.out.println("licenseSerialNumber " + licenseNumbers);

			String commandCommand = "/opt/nokia/oss/bin/LicenseUtil.sh -d";
			String deleteCommand = commandCommand + " " + licenseNumbers;

			System.out.println(deleteCommand);

			// Store in a File

			try {

				FileWriter fw = new FileWriter("D:\\DeleteCommand.txt");
				fw.write(deleteCommand);
				fw.close();

			} catch (Exception e1) {
				System.out.println(e1);
			}
			System.out.println("Success...");

			// Connect to putty and run the command

			PuttyCommandExecution.deleteCommandExecution(userName, password, ip);

		}
	}

	@Test(priority = 3, enabled = false)
	public void addLicense() throws IOException {

		// Move file to Lab
		try {
			String[] command = { "cmd.exe", "/C", "Start", "C:\\Users\\rajepath\\Documents\\command.bat" };
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException ex) {
		}

		// Now Read the File name one by One
		List<String> fileName = readObject.readFilename("C:\\Users\\rajepath\\Documents\\LicenseBackUp");
		for (String name : fileName) {

			String licenseName = name;
			String commandCommand = "/opt/nokia/oss/bin/LicenseUtil.sh -i /tmp/LicenseBackUp";
			String AddCommand = commandCommand + " " + licenseName;
			System.out.println(AddCommand);

			// Store in a File

			try {
				FileWriter fw = new FileWriter("D:\\AddCommand.txt");
				fw.write(AddCommand);
				fw.close();
			} catch (Exception e1) {
				System.out.println(e1);
			}
			System.out.println("Success...");

			// Connect to putty and run the command

			PuttyCommandExecution.addCommandExecution(userName, password, ip);

		}

	}

	/*
	 * @AfterTest
	 * 
	 * public void closeBrowser() { driver.quit();
	 * 
	 * }
	 */

}
