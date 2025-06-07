package sauce.qa.ecom.basetest;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import sauce.qa.ecom.factory.DriverFactory;
import sauce.qa.ecom.pages.SauceHomePage;
import sauce.qa.ecom.pages.SauceLoginPage;

public class BaseTest {

	protected WebDriver driver;
	protected DriverFactory df;
	protected SauceLoginPage lp;
	protected SauceHomePage hp;
	protected Properties prop;
	
	@Parameters({"browser"})
	
	@BeforeTest
	public void setup(String browserName) {
		df=new DriverFactory();
		prop = df.initProp();
		if(browserName!=null) {prop.setProperty("browser", browserName);}
		driver=df.initDriver(prop);
		lp=new SauceLoginPage(driver);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
