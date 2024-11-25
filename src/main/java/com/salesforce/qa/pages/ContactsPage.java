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

public class ContactsPage extends TestBase {

	@FindBy(xpath = "//button[@name='Global.NewContact']")
	WebElement newContactBtn;

	@FindBy(xpath = "//input[@placeholder='First Name']")
	WebElement firstName;

	@FindBy(xpath = "//input[@placeholder='Last Name']")
	WebElement lastName;

	@FindBy(xpath = "(//button[@data-aura-class='uiButton'])[2]")
	WebElement saveBtn;

	@FindBy(xpath = "//*[contains(@id,'toastDescription')]")
	WebElement contactsToastMsg;

	@FindBy(xpath = "//lightning-icon[@icon-name='utility:close']")
	WebElement toastMsgCloseIcon;

	@FindBy(xpath = "//*[@name='primaryField']/lightning-formatted-text")
	WebElement createdAccount;

	// Initializing the Page Objects:
	public ContactsPage() {
		PageFactory.initElements(driver, this);
	}

	public void createNewContact(String ftName, String ltName) throws InterruptedException {
		newContactBtn.click();
		waitForPageToLoad();
		firstName.sendKeys(ftName);
		Thread.sleep(2000);
		lastName.sendKeys(ltName);
		saveBtn.click();
		Thread.sleep(5000);
		String actualToastMsg = contactsToastMsg.getText();
		String expectedToastMsg = "Contact \"" + ftName + " " + ltName + "\" was created.";
		Assert.assertEquals(actualToastMsg, expectedToastMsg,
				actualToastMsg + " doesn't match with " + expectedToastMsg);
		toastMsgCloseIcon.click();
		waitForPageToLoad();
	}

	public void verifyContactAssociatedWithAccount(String accountName, String ftName, String ltName)
			throws InterruptedException {
		boolean actualContactDisplayed = driver.findElement(By.xpath("//lightning-formatted-text[text()='" + accountName
				+ "']//ancestor::records-record-layout-event-broker//article[@aria-label='" + ftName + " " + ltName
				+ "']")).isDisplayed();
		Assert.assertTrue(actualContactDisplayed, ftName + " " + ltName + " not displayed.");
		String actualAccountName = createdAccount.getText();
		Assert.assertEquals(actualAccountName, accountName, actualAccountName + " doesn't match with " + accountName);
	}
}