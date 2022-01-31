package com.postwatch.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Base {
	public static Properties prop;
	public static FileInputStream ip;
	public static WebDriver driver;
	public static ExtentHtmlReporter extentHtmlReporter;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;

	@BeforeSuite
	public void setBase(ITestContext ctx) throws IOException {
		String suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		String timeStamp = new SimpleDateFormat("DD-MM-YY-HH-mm-ss").format(Calendar.getInstance().getTime());

		// Initializing Property Files
		prop = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir") + File.separator + "config.property");
		prop.load(ip);

		// Extent Report Structure Creation/Initialization
		extentHtmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + File.separatorChar + "Results" + File.separatorChar + "ExtentReport"
						+ File.separatorChar + suiteName + "Report- " + timeStamp + ".html");

		extentReports = new ExtentReports();
		extentReports.attachReporter(extentHtmlReporter);

		extentReports.setSystemInfo("Application Name", prop.getProperty("application"));
		extentReports.setSystemInfo("Environment", prop.getProperty("environment"));
		extentReports.setSystemInfo("User Name", prop.getProperty("id"));

		extentHtmlReporter.config().setChartVisibilityOnOpen(true);
		extentHtmlReporter.config().setDocumentTitle("POSTWATCH Automation");
		extentHtmlReporter.config().setReportName("POSTWATCH Automation Report");
		extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		extentHtmlReporter.config().setTheme(Theme.STANDARD);

		// Initializing Browsers
		switch (prop.getProperty("browser").toUpperCase()) {
		case "CHROME":
			System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
			System.out.println("Executing in Chrome browser");
			driver = new ChromeDriver();
			break;

		case "FIREFOX":
			System.setProperty("webDriver.gecko.driver", "geckodriver.exe");
			System.out.println("Executing in Firefox browser");
			driver = new FirefoxDriver();
			break;

		case "IE":
			System.setProperty("webDriver.ie.driver", "IEDriverServer.exe");
			System.out.println("Executing in IE browser");
			driver = new InternetExplorerDriver();
			break;
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	// Initializing extentTest object
	@BeforeClass
	public void reportSetup() {
		extentTest = extentReports.createTest(this.getClass().getSimpleName());
		System.out.println("Class under execution: " + extentTest);
	}

	// Launching URL before every Test
	@BeforeMethod()
	public void lauchURL() {
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		extentTest.log(Status.INFO,
				MarkupHelper.createLabel("Launching URL: " + prop.getProperty("url"), ExtentColor.CYAN));
		extentTest.log(Status.INFO, MarkupHelper
				.createLabel("Browser used: " + prop.getProperty("browser").toUpperCase(), ExtentColor.CYAN));
	}

	@AfterMethod
	public void logStatus(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(Status.INFO,
					MarkupHelper.createLabel(result.getName() + " Test Scenario Failed", ExtentColor.RED));
		}

	}

}
