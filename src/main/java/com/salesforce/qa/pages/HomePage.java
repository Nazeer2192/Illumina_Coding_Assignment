/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.util.TestUtil;

public class HomePage extends TestBase {

	@FindBy(xpath = "//div[contains(@class,'profile')]//span[@class='uiImage']")
	WebElement viewProfile;

	@FindBy(xpath = "(//a[@class='profile-link-label'])[1]")
	@CacheLookup
	WebElement userNameLabel;

	@FindBy(xpath = "//a[contains(@class,'logout')]")
	WebElement logout;

	@FindBy(xpath = "//div[@class='appLauncher slds-context-bar__icon-action']")
	WebElement appLauncherBtn;

	@FindBy(xpath = "//input[@placeholder='Search apps and items...']")
	WebElement appLauncherSearchInput;

	@FindBy(xpath = "//h1[contains(@class,'appName')]/span")
	WebElement appName;

	@FindBy(xpath = "//span[text()='Accounts']/parent::a[contains(@href,'lightning/o/Account/home')]")
	WebElement accountsLink;

	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String getHomePageTitle() {
		return driver.getTitle();
	}

	public String verifyCorrectUserName() {
		viewProfile.click();
		return userNameLabel.getText();
	}

	public void logoutFromApplication() throws InterruptedException {
		viewProfile.click();
		Thread.sleep(2000);
		logout.click();
		Thread.sleep(5000);
		String loginPageTitle = new LoginPage().getLoginPageTitle();
		Assert.assertEquals(loginPageTitle, "Login | Salesforce");
	}

	public void quickSearchAppLauncher(String appLauncher) {
		appLauncherBtn.click();
		appLauncherSearchInput.sendKeys(appLauncher);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		WebElement appLauncherEle = driver.findElement(By
				.xpath("//one-app-launcher-menu//one-app-launcher-menu-item/a[@data-label='" + appLauncher + "']//p"));
		String actualAppLauncher = appLauncherEle.getText();
		Assert.assertEquals(actualAppLauncher, appLauncher, actualAppLauncher + " doesn't match with " + appLauncher);
		appLauncherEle.click();
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		String actualAppName = appName.getText();
		Assert.assertEquals(actualAppName, appLauncher, actualAppName + " doesn't match with " + appLauncher);
	}

	public AccountsPage clickOnAccountsLink() throws InterruptedException {
		WebElement button = driver.findElement(
				By.xpath("//span[text()='Accounts']/parent::a[contains(@href,'lightning/o/Account/home')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
		Thread.sleep(5000);
		return new AccountsPage();
	}
}
