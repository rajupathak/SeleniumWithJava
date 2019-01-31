package com.seleniumwithjavascript;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seleniumwithjavascript.testbase.Base;

public class AddNewAdapationTest extends Base {

	public AddNewAdapationTest() {

		super();

	}

	@BeforeTest

	public void init() throws InterruptedException {

		initialization();
		driver.get(prop.getProperty("Niddurl"));

		WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
		Base.highLighteBackground(driver, userName);
		userName.clear();
		userName.sendKeys(prop.getProperty("niddUserName"));

		WebElement passWord = driver.findElement(By.xpath("//input[@type='password']"));

		Base.highLighteBackground(driver, passWord);
		passWord.clear();
		passWord.sendKeys(prop.getProperty("niddPassWord"));
		Base.getScreenShot("LoginPage");

		WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
		Base.highLighteBackground(driver, loginButton);
		loginButton.click();
		Thread.sleep(500);
	}

	@Test(priority = 1, enabled = true)
	public void verifyAddingNewAdapataionInNidd_Test() {

		// Get All the List From Admin Drop Down
		// Click on Admin drop Down
		driver.findElement(By.xpath("//a[ contains(text(),'  Admin')]")).click();

		Base.getScreenShot("AdaptaitonPage");

		List<WebElement> list = driver
				.findElements(By.xpath("//ul[@class='nav navbar-nav navbar-right']//li//ul/li/a"));

		for (WebElement element : list) {
			if (element.getText().equalsIgnoreCase("Adaptations")) {
				Base.highLighteBackground(driver, element);
				element.click();
				break;
			}
		}

		// Now Select The Domain

		Select domain = new Select(driver.findElement(By.name("domainId")));
		List<WebElement> domainList = domain.getOptions();
		for (WebElement element : domainList) {
			if (element.getText().equalsIgnoreCase(prop.getProperty("domain"))) {
				Base.highLighteBackground(driver, element);
				element.click();
				break;
			}
		}
		Base.getScreenShot("DomainPage");
		// Click on Add Button

		WebElement addButton = driver.findElement(By.xpath("//a[@class='btn btn-link btn-action ']"));
		Base.highLighteBackground(driver, addButton);
		addButton.click();
		// Enter Adaptation Details
		Base.getScreenShot("AdaptationDetailPage");
		WebElement adaptationName = driver.findElement(By.xpath("//input[@id='Adaptation']"));

		adaptationName.clear();
		Base.highLighteBackground(driver, adaptationName);
		adaptationName.sendKeys(prop.getProperty("adaptationId"));

		WebElement presentationName = driver.findElement(By.xpath("//input[@id='PresentationName']"));
		Base.highLighteBackground(driver, presentationName);
		presentationName.clear();

		presentationName.sendKeys(prop.getProperty("presentationName"));

		WebElement description = driver.findElement(By.xpath("//input[@id='Description']"));
		Base.highLighteBackground(driver, description);
		description.clear();

		description.sendKeys(prop.getProperty("description"));

		WebElement Vendor = driver.findElement(By.xpath("//input[@id='Vendor']"));
		Base.highLighteBackground(driver, Vendor);
		Vendor.clear();

		Vendor.sendKeys(prop.getProperty("Vendor"));

		// Select Product

		driver.findElement(By.xpath("//input[@value='Select Some Options']")).click();

		WebElement product = driver.findElement(By.xpath("//ul[@class='chosen-results']/li"));

		product.click();
		Base.getScreenShot("AdaptationDetailPage_AfterValidInfo");
		// Click on Submit Button

		/*
		 * WebElement submitButton =
		 * driver.findElement(By.xpath("//input[@type='submit']"));
		 * baseObject.highLighteBackground(driver, submitButton); submitButton.click();
		 */

		// Click on Cancel Button

		WebElement cancelButton = driver.findElement(By.xpath("//a[text()='Cancel']"));
		Base.highLighteBackground(driver, cancelButton);
		cancelButton.click();

	}

	@Test(priority = 2, enabled = true)
	public void verifyAddingSubSystemDetails_Test() {

		// Get All the List From CM Drop Down
		// Click on CM drop Down
		driver.findElement(By.xpath("//a[ (text()='CM')]")).click();

		Base.getScreenShot("AdaptaitonPage");

		List<WebElement> list = driver.findElements(By.xpath("//ul[@class='dropdown-menu']//li//a"));

		for (WebElement element : list) {
			System.out.println(element.getText());
			if (element.getText().equalsIgnoreCase("CM Adaptations")) {
				Base.highLighteBackground(driver, element);
				element.click();
				break;
			}
		}
		// Now Select The Domain

		Select domain = new Select(driver.findElement(By.name("domainId")));
		List<WebElement> domainList = domain.getOptions();
		for (WebElement element : domainList) {
			if (element.getText().equalsIgnoreCase(prop.getProperty("domain"))) {
				Base.highLighteBackground(driver, element);
				element.click();
				break;
			}
		}

		// Now Click on CustomAttribute

		WebElement element = driver.findElement(By.xpath("//td//a[text()='CustAttr']"));
		Base.highLighteBackground(driver, element);
		element.click();

		// Adding SubsystemDetail

		WebElement CMHName = driver.findElement(By.xpath("//table[@class='formTable']//td//input[@id='custDataList_19__CustAttrData']"));
		Base.highLighteBackground(driver, CMHName);
		CMHName.clear();
		CMHName.sendKeys("abxssss");
		
		
		WebElement CMDName = driver.findElement(By.xpath("//table[@class='formTable']//td//input[@id='custDataList_115__CustAttrData']"));
		Base.highLighteBackground(driver, CMDName);
		CMDName.clear();
		CMDName.sendKeys("abxssss123");
		
		/*
		 * WebElement submitButton =
		 * driver.findElement(By.xpath("//input[@type='submit']"));
		 * baseObject.highLighteBackground(driver, submitButton); submitButton.click();
		 */

		// Click on Cancel Button

		WebElement cancelButton = driver.findElement(By.xpath("//a[text()='Cancel']"));
		Base.highLighteBackground(driver, cancelButton);
		cancelButton.click();
		}

	}


