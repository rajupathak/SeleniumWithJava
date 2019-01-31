package com.seleniumwithjavascript.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import com.seleniumwithjavascript.util.TestUtil;
import com.seleniumwithjavascript.util.WebEventListener;

public class Base {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static FileInputStream file1;
	public static FileInputStream file2;
	public static JavascriptExecutor js ;

	public Base() {
		try {
			prop = new Properties();
			file1 = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\seleniumwithjavascript\\properties\\labCredentials.properties");
			
			file2 = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\seleniumwithjavascript\\properties\\NiddCredentials.properties");

			prop.load(file1);
			prop.load(file2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		 
		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\rajepath\\Desktop\\NetAct Docs\\SeleniumWorkSpace\\seleniumwithjavascript\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\rajepath\\Desktop\\NetAct Docs\\SeleniumWorkSpace\\seleniumwithjavascript\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		js = (JavascriptExecutor) driver;
		
	}
	
	
	public static void highLighteBackground(WebDriver driver,WebElement element) {

		js.executeScript("arguments[0].style.border='6px groove yellow'",
				element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].style.border=''", element);

	}
	public static String getScreenShot(String screenshotName) {
		if (screenshotName.equals("")) {
			screenshotName = "blank";
		}

		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		TakesScreenshot tc = (TakesScreenshot) driver;

		File sourceFile = tc.getScreenshotAs(OutputType.FILE);
		
		try {

			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "\\src\\main\\java\\com\\seleniumwithjavacript\\screenShots\\";
		
			String actaulImageName = reportDirectory + screenshotName + "_"
					+ formater.format(calender.getTime()) + ".png";

			File desFile = new File(actaulImageName);
			FileUtils.copyFile(sourceFile, desFile);
			Reporter.log("<a href='" + desFile.getAbsolutePath()
					+ "'><img src='" + desFile.getAbsolutePath()
					+ "' height='100' width='100'/></a>");
			return actaulImageName;
		} catch (Exception e) {
			System.out.println("Exception is throwing during screen shot"
					+ e.getMessage());
		}
		return screenshotName;

	}
	
	
	
	

}

