package com.postwatch.base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ReusableLibrary {

	public static String getScreenshot(WebDriver driver, String className) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destFile= new File(".....");
		FileUtils.copyFile(srcFile, destFile);
		return "";
	}
}
