/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.salesforce.qa.base.TestBase;

public class LoginPage extends TestBase {

	// Page Factory - OR:
	@FindBy(name = "username")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement loginBtn;

	@FindBy(xpath = "//img[contains(@class,'standard_logo')]")
	WebElement salesforceLogo;

	HomePage homePage;

	// Initializing the Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
		homePage = new HomePage();
	}

	// Actions:
	public String getLoginPageTitle() {
		return driver.getTitle();
	}

	public boolean validateSalesforceImage() {
		return salesforceLogo.isDisplayed();
	}

	public HomePage login(String un, String pwd) throws InterruptedException {
		username.sendKeys(un);
		password.sendKeys(pwd);
		loginBtn.click();
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, "Lightning Experience", "Home page title not matched");
		WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[contains(@class,'profile')]//span[@class='uiImage']")));
		return new HomePage();
	}

}
