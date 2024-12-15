package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;// UI of report
	public ExtentReports extent;// populate common info on the report
	public ExtentTest test;// creating test case entries in the report and update status of the test
							// methods
	String repName;

	public void onStart(ITestContext testcontext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");// title of report
		sparkReporter.config().setReportName("Functional Testing");// name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application ", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment ", "QA");

		String os = testcontext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testcontext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("browser ", browser);

		List<String> includedGroups = testcontext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups ", includedGroups.toString());
		}

	}
 
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());// create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());// to display group in report
		test.log(Status.PASS, result.getName() + "got successfully executed");// update status p/f/s

	}

	public void onTestFailure(ITestResult result) {
		test  = extent.createTest(result.getTestClass().getName());// create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + "got failed");// update status p/f/s
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());// create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + "got skipped");// update status p/f/s
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestResult result) {
		extent.flush();

		String pathofExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathofExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * try { URL url = new URL("file://" + System.getProperty("user.dir") +
		 * "reports\\repName");
		 * 
		 * // create the email message ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemail.com"); email.setSmtpPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("sushvairal01@gmail.com",
		 * "Enduser@123")); email.setSSLOnConnect(true);
		 * email.setFrom("sushvairal01@gmail.com");// sender
		 * email.setSubject("Test Result");
		 * email.setMsg("please find attached report...");
		 * email.addTo("khadsanavinash01@gmail.com");// receiver email.attach(url,
		 * "extent report", "please check report"); email.send(); }
		 * 
		 * catch (Exception e) { e.printStackTrace(); }
		 */
	}

}
