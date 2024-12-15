package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;// Log4j
	public Properties p;

	/**
	 * @param os
	 * @param br
	 * @throws IOException
	 */
	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {

		// loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		logger = LogManager.getLogger(this.getClass());

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// os
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN10);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("no matching records");
				return;
			}

			// browser
			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("no matching browser");
				return;
			}

			if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

				switch (br.toLowerCase()) {
				case "chrome":
					capabilities.setBrowserName("chrome");
					break;
				case "edge":
					capabilities.setBrowserName("MicrosoftEdge");
					break;
				default:
					System.out.println("no matching browser");
					return;
				}
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			}
		}
		switch (br.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("invalid browser name");
			return;
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));// reading url from properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void tearDown() {
		driver.quit();
	}

	public String randomeString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}

	public String randomeNumber() {
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}

	public String randomeAlphaNumeric() {
		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatedstring + "@" + generatednumber);
	}

	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourFile = takescreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots" + tname + "_" + timeStamp + ".png";
		File targetfFile = new File(targetFilePath);

		sourFile.renameTo(targetfFile);
		return targetFilePath;
	}

}
