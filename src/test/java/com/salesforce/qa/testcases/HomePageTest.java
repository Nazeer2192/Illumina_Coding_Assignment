/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	String sheetName = "searchAppLauncher";

	public HomePageTest() {
		super();
	}

	// test cases should be separated -- independent with each other
	// before each test case -- launch the browser and login
	// @test -- execute test case
	// after each test case -- close the browser

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		testUtil = new TestUtil();
		contactsPage = new ContactsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getAppLauncherSearchTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 0)
	public void verifyHomePageTitleTest() {
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, "Home | Salesforce", "Home page title not matched");
	}

	@Test(priority = 1)
	public void verifyUserNameTest() {
		String expectedUserName = "Nazeer Ahmed";
		String actualUserName = homePage.verifyCorrectUserName();
		Assert.assertEquals(actualUserName, expectedUserName,
				actualUserName + " doesn't match with " + expectedUserName);
	}

	@Test(priority = 2)
	public void logoutTest() throws InterruptedException {
		homePage.logoutFromApplication();
	}

	@Test(priority = 3, dataProvider = "getAppLauncherSearchTestData")
	public void appLauncherSearchTest(String quickSearchAppLauncher) throws InterruptedException {
		homePage.quickSearchAppLauncher(quickSearchAppLauncher);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
