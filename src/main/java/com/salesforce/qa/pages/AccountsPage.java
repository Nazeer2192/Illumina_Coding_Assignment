/*
 * @author Nazeer Ahmed
 * 
 */

package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.salesforce.qa.base.TestBase;

public class AccountsPage extends TestBase {

	@FindBy(xpath = "//a[@title='New']")
	WebElement newAccountsLink;

	@FindBy(xpath = "//input[@name='Name']")
	WebElement accountNameInputField;

	@FindBy(xpath = "//button[@name='SaveEdit']")
	WebElement accountSaveBtn;

	@FindBy(xpath = "//*[contains(@id,'toastDescription')]")
	WebElement accountsToastMsg;

	@FindBy(xpath = "//*[@title='Delete']")
	WebElement deleteLink;

	@FindBy(xpath = "//button[@title='Delete']")
	WebElement confirmationDeleteBtn;

	@FindBy(xpath = "//*[contains(@id,'toastDescription')]")
	WebElement accountDeleteToastMsg;

	@FindBy(xpath = "//lightning-icon[@icon-name='utility:close']")
	WebElement toastMsgCloseIcon;

	// Initializing the Page Objects:
	public AccountsPage() {
		PageFactory.initElements(driver, this);
	}

	public void createAccount(String accountName) throws InterruptedException {
		newAccountsLink.click();
		Thread.sleep(2000);
		accountNameInputField.sendKeys(accountName);
		accountSaveBtn.click();
		Thread.sleep(3000);
		String actualToastMsg = accountsToastMsg.getText();
		String expectedToastMsg = "Account \"" + accountName + "\" was created.";
		Assert.assertEquals(actualToastMsg, expectedToastMsg,
				actualToastMsg + " doesn't match with " + expectedToastMsg);
		toastMsgCloseIcon.click();
		waitForPageToLoad();
	}

	public void deleteAccount(String accountName) throws InterruptedException {
		WebElement wrenchIcon = driver.findElement(By.xpath("//a[@title='" + accountName
				+ "']//ancestor::th//following-sibling::td//span[@class='slds-icon_container slds-icon-utility-down']"));
		wrenchIcon.click();
		deleteLink.click();
		confirmationDeleteBtn.click();
		waitForPageToLoad();
		String actualDeleteToastMsg = accountDeleteToastMsg.getText();
		String expectedDeleteToastMsg = "Account \"" + accountName + "\" was deleted. Undo";
		Assert.assertEquals(actualDeleteToastMsg, expectedDeleteToastMsg,
				actualDeleteToastMsg + " doesn't match with " + expectedDeleteToastMsg);
		toastMsgCloseIcon.click();
		waitForPageToLoad();
	}
}