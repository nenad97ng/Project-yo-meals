package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.AuthPage;
import pages.CartSummaryPage;
import pages.LocationPopupPage;
import pages.LoginPage;
import pages.MealPage;
import pages.NotificationSystemPage;
import pages.ProfilePage;

public class MealItemTest extends BasicTest {

	
	@Test(priority=1)
	public void addMealToCartTest() throws InterruptedException {
		this.driver.navigate().to(this.baseUrl + "/meal/lobster-shrimp-chicken-quesadilla-combo");
		
		LocationPopupPage locationPopup = new LocationPopupPage(driver, wait, js);
		NotificationSystemPage notificationSystem = new NotificationSystemPage(driver, wait, js);
		MealPage meal = new MealPage(driver, wait, js);
		
		locationPopup.closePopup();
		meal.addMealToCart("3");
		Assert.assertTrue(notificationSystem.getMessage().contains("The Following Errors Occurred:"),"[ERROR]: The Following Errors Occurred:");
		Assert.assertTrue(notificationSystem.getMessage().contains("Please Select Location"), "[ERROR]: Please Select Location");
		
		notificationSystem.notificationInvisible();
		
		locationPopup.openPopup();
		locationPopup.setLocation("City Center - Albany");
		
		Thread.sleep(2000);
		meal.addMealToCart("3");
		
		Assert.assertTrue(notificationSystem.getMessage().contains("Meal Added To Cart"), "[ERROR]: Meal not added to cart.");

		
	}
	@Test(priority=2)
	public void addMealToFavoriteTest() throws InterruptedException {
		Thread.sleep(5000);
		this.driver.navigate().to(this.baseUrl + "/meal/lobster-shrimp-chicken-quesadilla-combo");
		

		LoginPage login = new LoginPage(driver, wait, js);
		NotificationSystemPage notificationSystem = new NotificationSystemPage(driver, wait, js);
		MealPage meal = new MealPage(driver, wait, js);

		
		

		Thread.sleep(2000);
		meal.addMealToFavorite();
		
		
		Assert.assertTrue(notificationSystem.getMessage().contains("Please login first!"));
		
		this.driver.navigate().to(this.baseUrl + "/guest-user/login-form");
		
		login.login(email, password);
		
		this.driver.navigate().to(this.baseUrl + "/meal/lobster-shrimp-chicken-quesadilla-combo");
		
		meal.addMealToFavorite();
		
		Thread.sleep(1000);
		Assert.assertTrue(notificationSystem.getMessage().contains("Product has been added to your favorites."), "[ERROR]: Message was not displayed.");

		

		

	}
	
	@Test(priority=3)
	public void clearCartTest() throws InterruptedException, IOException {
		Thread.sleep(5000);
		this.driver.navigate().to(this.baseUrl + "/meals");
	
		LocationPopupPage locationPopup = new LocationPopupPage(driver, wait, js);
		NotificationSystemPage notificationSystem = new NotificationSystemPage(driver, wait, js);
		MealPage meal = new MealPage(driver, wait, js);
		CartSummaryPage cartSummary = new CartSummaryPage(driver, wait, js);
		AuthPage auth = new AuthPage(driver, wait, js);
		
		SoftAssert sa = new SoftAssert();
		
		
		auth.logout();
		Thread.sleep(2000);
		
		this.driver.navigate().to(this.baseUrl + "/meals");
		Thread.sleep(3000);
		locationPopup.openPopup();
		locationPopup.setLocation("City Center - Albany");
		
		
		
		File file = new File("data/Data.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Meals");
		
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet.getRow(i);
			
			XSSFCell meals = row.getCell(0);
			String links = meals.getStringCellValue();
			
			this.driver.navigate().to(links);			
			Thread.sleep(5000);
			meal.addMealToCart("1");
			
			sa.assertTrue(notificationSystem.getMessage().contains("Meal Added To Cart"));
			Thread.sleep(1000);
		}
		sa.assertAll();
		cartSummary.clearAll();
		
		Assert.assertTrue(notificationSystem.getMessage().contains("All meals removed from Cart successfully"), "[ERROR]: Meals not removed.");
		Thread.sleep(5000);
	}
}
