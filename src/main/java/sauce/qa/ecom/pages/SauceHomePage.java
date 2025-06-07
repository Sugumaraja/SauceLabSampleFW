package sauce.qa.ecom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import sauce.qa.ecom.constants.AppConstants;
import sauce.qa.ecom.utils.EleUtil;
import sauce.qa.ecom.utils.ExcelUtil;
import sauce.qa.ecom.utils.JsUtil;

public class SauceHomePage {
	private WebDriver driver;
	private EleUtil ele;
	private JsUtil js;
	ExcelUtil eu;
	
	private By title=By.className("title");
	private By burgerMenu=By.id("react-burger-menu-btn");
	private By logout=By.id("logout_sidebar_link");

	public SauceHomePage(WebDriver driver) {
		this.driver=driver;
		ele=new EleUtil(this.driver);
		js=new JsUtil(this.driver);
	}
	
	public String getProductPageTitle() {
		js.pageLoadReadyByJS(AppConstants.MEDIUM_WAIT);
		return ele.doGetText(title);
	}
	public void doLogout() {
		ele.doClick(burgerMenu);
		ele.doClickWithWait(logout,AppConstants.SHORT_WAIT);
	}
	

}
