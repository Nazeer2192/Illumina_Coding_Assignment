/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.testcases;

import java.util.Random;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.util.TestUtil;

public class AccountsPageTest extends TestBase {

	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;

	Random rnd = new Random();
	int n = 100000 + rnd.nextInt(900000);
	String accountName = "Test Account" + n;

	String sheetName = "searchAppLauncher";

	public AccountsPageTest() {
		super();

	}

	@BeforeMethod
	public void setUp() throws InterruptedException {

		initialization();
		testUtil = new TestUtil();
		accountsPage = new AccountsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getAppLauncherSearchTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 0, dataProvider = "getAppLauncherSearchTestData")
	public void verifyAccountCreationTest(String quickSearchAppLauncher) throws InterruptedException {
		homePage.quickSearchAppLauncher(quickSearchAppLauncher);
		accountsPage.createAccount(accountName);
		homePage.clickOnAccountsLink();
		accountsPage.deleteAccount(accountName);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}