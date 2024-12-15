package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.bytebuddy.asm.MemberSubstitution.FieldValue;

public class HomePage extends BasePage {

	// constructor

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// locators
	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement lnkMyaccount;

	@FindBy(xpath = "(//a[normalize-space()='Register'])")
	WebElement lnkRegister;

	@FindBy(xpath = "(//a[normalize-space()='Login'])[1]")
	WebElement linkLogin;

	public void clickMyAccount() {
		lnkMyaccount.click();
	}

	public void clickRegister() {
		lnkRegister.click();
	}

	public void clickLogin() {
		linkLogin.click();
	}
}
