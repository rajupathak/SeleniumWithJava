package com.seleniumwithjavascript;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seleniumwithjavascript.testbase.Base;

import junit.framework.Assert;

public class DemoJavaScript extends Base {

	JavascriptExecutor js;
	Base baseObject;

	public DemoJavaScript() {
		super();
	}

	@BeforeTest

	public void init() {

		initialization();
		js = (JavascriptExecutor) driver;
	}

	@Test
	public void executeJavascript() {

		WebElement userName = driver.findElement(By.id("os_username"));
		userName.clear();
		userName.sendKeys(prop.getProperty("userName"));

		WebElement passWord = driver.findElement(By.id("os_password"));
		passWord.clear();
		passWord.sendKeys(prop.getProperty("passWord"));

		WebElement loginButton = driver.findElement(By.id("loginButton"));

		loginButton.click();

		String exptectedTitle = "CM Adaptation generation - BSO OSS RD - Blr Organization Space - Dashboard";

		Assert.assertEquals(exptectedTitle, driver.getTitle());

		js.executeScript("alert('Welcome to confulencePage');");

	}

}
