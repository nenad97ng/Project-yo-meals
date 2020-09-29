package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage extends BasicPage{

	public ProfilePage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}
	
	//Getters for basic informations
	public WebElement getFirstName() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[1]/div[1]/fieldset/input"));
	}
	public WebElement getLastName() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[1]/div[2]/fieldset/input"));
	}
	public WebElement getAddress() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[2]/div[2]/fieldset/input"));
	}
	public WebElement getPhoneNo() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[3]/div[1]/fieldset/input"));
	}
	public WebElement getZipCode() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[3]/div[2]/fieldset/input"));
	}
	public void getCountry(String country) {
		WebElement countryElement = driver.findElement(By.xpath("//*[@id=\"user_country_id\"]"));
		Select countrySelect = new Select(countryElement);
		countrySelect.selectByVisibleText(country);
	}
	public void getState(String state) {
		WebElement stateElement = driver.findElement(By.xpath("//*[@id=\"user_state_id\"]"));
		Select stateSelect = new Select(stateElement);
		stateSelect.selectByVisibleText(state);
	}
	public void getCity(String city) {
		WebElement cityElement = driver.findElement(By.xpath("//*[@id=\"user_city\"]"));
		Select citySelect = new Select(cityElement);
		citySelect.selectByVisibleText(city);
	}
	public WebElement getInfoSaveButton() {
		return driver.findElement(By.xpath("//*[@id=\"profileInfoFrm\"]/div[5]/div/fieldset/input"));
	}
	
	//Profile picture 
	public void getUploadImage() {
		WebElement upload = driver.findElement(By.xpath("//*[@id=\"profileInfo\"]/div/div[1]/div/a[1]"));
		js.executeScript("arguments[0].click();", upload);
	}
	public void uploadImage(String image) {
		this.getUploadImage();
		WebElement input = driver.findElement(By.xpath("//input[@type='file']"));
		input.sendKeys(image);
	}
	public void deleteImage() {
		WebElement delete = driver.findElement(By.xpath("//*[@id=\"profileInfo\"]/div/div[1]/div/a[2]"));	
		js.executeScript("arguments[0].click();", delete);
	}
	
	//Method for change informations
	public void updateProfile(String firstName, String lastName, String address, String phoneNo, String zipCode,
			String country, String state, String city) {
		
		this.getFirstName().sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		this.getLastName().sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		this.getAddress().sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		this.getPhoneNo().sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		this.getZipCode().sendKeys(Keys.CONTROL + "a", Keys.DELETE);
	
		
		this.getFirstName().sendKeys(firstName);
		this.getLastName().sendKeys(lastName);
		this.getAddress().sendKeys(address);
		this.getPhoneNo().sendKeys(phoneNo);
		this.getZipCode().sendKeys(zipCode);
		this.getCountry(country);
		this.getState(state);
		this.getCity(city);
		
		this.getInfoSaveButton().click();
	}
}
