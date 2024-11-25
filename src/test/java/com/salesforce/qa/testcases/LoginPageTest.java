/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;

	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
	}

	@Test(priority = 0)
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		Assert.assertEquals(title, "Login | Salesforce");
	}

	@Test(priority = 1)
	public void salesforceLogoImageTest() {
		boolean flag = loginPage.validateSalesforceImage();
		Assert.assertTrue(flag);
	}

	@Test(priority = 2)
	public void loginTest() throws InterruptedException {
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		homePage.logoutFromApplication();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
