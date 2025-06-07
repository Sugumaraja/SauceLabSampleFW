package sauce.qa.ecom.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import sauce.qa.ecom.exception.FrameWorkException;

public class DriverFactory {
	private WebDriver driver;
	private Properties prop;
	private static ThreadLocal<WebDriver> tld;
	public static String highlight;

	public WebDriver initDriver(Properties prop) {
		
		String browser = prop.getProperty("browser");
		highlight=prop.getProperty("highlight");
		OptionsManager option = new OptionsManager(prop);
		
		tld = new ThreadLocal<>();
		System.out.println("browser selected "+browser);
		
		switch (browser.toLowerCase().trim()) {
		
		case "chrome": {
//			driver = new ChromeDriver(option.getChromeOption());
			tld.set(new ChromeDriver(option.getChromeOption()));
			break;
		}
		case "firefox": {
//			driver = new FirefoxDriver(option.getFirefoxOption());
			tld.set(new FirefoxDriver(option.getFirefoxOption()));
			break;
		}
		case "edge": {
//			driver = new EdgeDriver(option.getEdgeOption());
			tld.set(new EdgeDriver(option.getEdgeOption()));
			break;
		}
		case "safari": {
//			driver = new SafariDriver();
			tld.set(new SafariDriver());
			break;
		}
		default: {
			throw new FrameWorkException("Plss pass the right browser: " + browser);
		}
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tld.get();
	}

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip;
		String env = System.getProperty("env");

		try {
			if (env == null) {
				ip = new FileInputStream("./src/test/resources/Config/config_qa.properties");
			} else {
				switch (env.toLowerCase().trim()) {

				case "qa": {
					ip = new FileInputStream("./src/test/resources/Config/config_qa.properties");
					break;
				}
				case "dev": {
					ip = new FileInputStream("./src/test/resources/Config/config_dev.properties");
					break;
				}
				case "stage": {
					ip = new FileInputStream("./src/test/resources/Config/config_stage.properties");
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + env.toLowerCase().trim());
				}
			}

			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
	/**
	 * take screenshot
	 */
	public static String getScreenshot(String methodName) {

	File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}