package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotificationSystemPage extends BasicPage {

	public NotificationSystemPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	public WebElement getElementMessage() {
		return driver.findElement(By.xpath(
				"//*[contains(@class, 'alert--success') or contains(@class, 'alert--danger')][contains(@style,'display: block')]"));
	}

	public String getMessage() {
		return this.getElementMessage().getText();
	}

	public void notificationInvisible() {
		WebElement systemMessage = driver.findElement(By.xpath("//*[contains(@class, 'system_message')]"));
		wait.until(ExpectedConditions.attributeToBe(systemMessage, "style", "display: none;"));
	}
}
