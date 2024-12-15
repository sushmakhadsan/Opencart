package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups = { "sanity", "Master" })
	public void verify_login() throws InterruptedException {
		logger.info("****Starting TC002_LoginTest****");

		try {
			// homepage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// loginpage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();

			// myaccountpage
			MyAccountPage mac = new MyAccountPage(driver);
			boolean targetPage = mac.isMyAccountPageExists();

			// Assert.assertEquals(targetPage,true,"Login failed");
			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}
		Thread.sleep(5000);
		logger.info("****Finished TC002_LoginTest****");
	}

}
