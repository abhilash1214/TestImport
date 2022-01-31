package com.postwatch.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadTestData {

	public static void readDataFromSheet(String sheetName) throws IOException {
		String inputExcel = "input.xlsx";
		File file = new File(System.getProperty("user.dir") + inputExcel);
		FileInputStream fileinput = new FileInputStream(file);

		XSSFWorkbook workbook = new XSSFWorkbook(fileinput);
		XSSFSheet sheet = workbook.getSheet(sheetName);

		int lastRowNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		Object[][] objects = new Object[lastRowNum][1];

		for (int i = 0; i < lastRowNum; i++) {
			HashMap<Object, Object> datamap = new HashMap<Object, Object>();
			for (int j = 0; j < lastCellNum; j++) {
			}
		}

	}
}
