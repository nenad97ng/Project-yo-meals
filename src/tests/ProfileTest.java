package tests;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.AuthPage;
import pages.LocationPopupPage;
import pages.LoginPage;
import pages.NotificationSystemPage;
import pages.ProfilePage;

public class ProfileTest extends BasicTest{

	@Test(priority=0)
	public void editProfileTest() throws InterruptedException {
		this.driver.navigate().to(this.baseUrl + "/guest-user/login-form");
		
		LocationPopupPage locationPopup = new LocationPopupPage(driver, wait, js);
		LoginPage login = new LoginPage(driver, wait, js);
		NotificationSystemPage notificationSystem = new NotificationSystemPage(driver, wait, js);
		ProfilePage profile = new ProfilePage(driver, wait, js);
		AuthPage auth = new AuthPage(driver, wait, js);
		
		
		locationPopup.closePopup();
		login.login(email, password);
		Assert.assertTrue(notificationSystem.getMessage().contains("Login Successfull"));
		
		
		this.driver.navigate().to(this.baseUrl + "/member/profile");
		profile.updateProfile("Will", "W", "Serbia", "056482456", "12500", "United Kingdom", "Cambridge", "Cambridge");
		Assert.assertTrue(notificationSystem.getMessage().contains("Setup Successful"));
		notificationSystem.notificationInvisible();
		
		auth.logout();
		Assert.assertTrue(notificationSystem.getMessage().contains("Logout Successfull!"));
			
	}
	@Test(priority=5)
	public void changeProfileImageTest() throws InterruptedException, IOException {
		this.driver.navigate().to(this.baseUrl + "/guest-user/login-form");
	
		LocationPopupPage locationPopup = new LocationPopupPage(driver, wait, js);
		LoginPage login = new LoginPage(driver, wait, js);
		NotificationSystemPage notificationSystem = new NotificationSystemPage(driver, wait, js);
		ProfilePage profile = new ProfilePage(driver, wait, js);
		AuthPage auth = new AuthPage(driver, wait, js);
		
		login.login(email, password);
		Assert.assertTrue(notificationSystem.getMessage().contains("Login Successfull"));
		
		this.driver.navigate().to(this.baseUrl + "/member/profile");
		
		Thread.sleep(3000);
		String imgPath = new File("images/slika.png").getCanonicalPath();
		
		profile.uploadImage(imgPath);
		Assert.assertTrue(notificationSystem.getMessage().contains("Profile Image Uploaded Successfully"));
		
		notificationSystem.notificationInvisible();
		
		profile.deleteImage();		
		Assert.assertTrue(notificationSystem.getMessage().contains("Profile Image Deleted Successfully"));
		
		notificationSystem.notificationInvisible();
		
		auth.logout();
		Assert.assertTrue(notificationSystem.getMessage().contains("Logout Successfull!"));
	}
	
}
