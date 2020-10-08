package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LocationPopupPage;
import pages.SearchResultPage;

public class SearchTest extends BasicTest {

	@Test
	public void searchResultsTest() throws IOException, InterruptedException {
		
		this.driver.navigate().to(this.baseUrl + "/meals");

		//Import pages
		LocationPopupPage locationPopup = new LocationPopupPage(driver, wait, js);
		SearchResultPage searchResult = new SearchResultPage(driver, wait, js);

		locationPopup.setLocation("City Center - Albany");

		// Import xlsx file
		File file = new File("data/Data.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Meal Search Results");
		
		//Num comparing
		for (int i = 1; i < sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);

			XSSFCell locationData = row.getCell(0);
			XSSFCell urlData = row.getCell(1);
			XSSFCell numResultsData = row.getCell(2);

			if (urlData.getStringCellValue().isEmpty()) {
				return;
			}

			String location = locationData.getStringCellValue();
			String url = urlData.getStringCellValue();
			int numResults = (int) numResultsData.getNumericCellValue();

			this.driver.navigate().to(url);
			locationPopup.openPopup();
			locationPopup.setLocation(location);

			Thread.sleep(2000);

			Assert.assertEquals(numResults, searchResult.getNumSearchResults(),
					"[ERROR]: Num of results is not correct on iterarion num " + i);
			
			//Names comparing 
			for (int n = 3; n < row.getLastCellNum(); n++) {

				XSSFCell mealNameData = row.getCell(n);
				String mealName = mealNameData.getStringCellValue();

				List<String> actualMealNames = searchResult.searchMealsNames();
				String actuaMeallName = actualMealNames.get(n - 3).toString();
				System.out.println(actuaMeallName);
				Assert.assertEquals(mealName, actuaMeallName,
						"[ERROR]: Names of results is not correct on iterarion num " + i);
			}
		}
		
		workbook.close();
		fis.close();
	}
}
