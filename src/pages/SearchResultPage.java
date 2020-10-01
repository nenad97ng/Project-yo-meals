package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultPage extends BasicPage{

	public SearchResultPage(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
		super(driver, wait, js);
	}
	
	public WebElement getSearchResults() {
		return driver.findElement(By.xpath("//*[@class='product-name']/a"));
	}
	
	public List<String> searchMealsNames() {
		List<WebElement> meals = driver.findElements(By.xpath("//*[@id=\"listing\"]/div/div/div[2]/div[2]/a"));
		List<String> mealsNames = new ArrayList<String>();
		
		for(int i = 0; i < meals.size(); i++) {
			mealsNames.add(i, meals.get(i).getText().replaceAll("[.]",""));
		}
		return mealsNames;
		
	}
	
	public int getNumSearchResults() {
		List<WebElement> results = driver.findElements(By.xpath("//*[@class='product-grid-item']"));
		int numSearchResults = results.size();
		return numSearchResults;
	}
}
