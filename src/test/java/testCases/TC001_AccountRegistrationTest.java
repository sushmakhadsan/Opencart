package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegesrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = {"Regression","Master"})
	public void verify_account_regisration() {
		try {
			logger.info("******starting TC001_AccountRegistrationTest******");

			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("clicked on myAccount");
			hp.clickRegister();
			logger.info("clicked o register link");

			AccountRegesrationPage regpage = new AccountRegesrationPage(driver);
			logger.info("providing customer details");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com");
			regpage.setTelephone(randomeNumber());

			String password = randomeAlphaNumeric();
			regpage.setPassword(password);
			regpage.setConfirmPassword(password);
			regpage.setPrivacyPolicy();
			regpage.clickContinue();

			logger.info("Validating expected message...");

			String confmsg = regpage.getConfirmationMsg();
			// Assert.assertEquals(confmsg, "Your Account Has Been Created!");
			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.info("Test failed");
				logger.debug("debug logs");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {

			Assert.fail();
		}

		logger.info("******Finish TC001_AccountRegistrationTest******");
	}

}
