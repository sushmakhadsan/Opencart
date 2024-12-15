package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups = "Datadriven") // getting data provider from different
																				// class
	public void verify_loginDDT(String email, String pwd, String exp) {
		logger.info("****Starting TC_003_LoginDDT****");

		try {
			// homepage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// loginpage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// myaccountpage
			MyAccountPage mac = new MyAccountPage(driver);
			boolean targetPage = mac.isMyAccountPageExists();

			if (exp.equalsIgnoreCase("Valid")) {
				if (targetPage == true) {
					Assert.assertTrue(true);
					mac.clickLogout();
				} else {
					Assert.assertTrue(false);
				}
			}

			if (exp.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					mac.clickLogout();
					Assert.assertTrue(false);

				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("****Finished TC_003_LoginDDT****");
	}
}
