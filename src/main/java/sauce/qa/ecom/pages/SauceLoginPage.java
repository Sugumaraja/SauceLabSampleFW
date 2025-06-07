package sauce.qa.ecom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import sauce.qa.ecom.constants.AppConstants;
import sauce.qa.ecom.utils.EleUtil;
import sauce.qa.ecom.utils.ExcelUtil;
import sauce.qa.ecom.utils.JsUtil;

public class SauceLoginPage {

private WebDriver driver;
private EleUtil ele;
private JsUtil js;
private ExcelUtil eu;

public SauceLoginPage(WebDriver driver) {
	this.driver=driver;
	ele=new EleUtil(this.driver);
	js=new JsUtil(this.driver);
	eu = new ExcelUtil();
}
private By userNameField=By.id("user-name");
private By pwdField=By.id("password");
private By loginBtn=By.id("login-button");


public String getUrl() {
	js.pageLoadReadyByJS(AppConstants.LONG_WAIT);
	String url = ele.getUrl();;
	return url;
}
public Object[][] getExcelData(String sheetName) {
	return eu.getExcelData(sheetName);
}
public SauceHomePage doLogin(String un, String pwd) {
	ele.doSendKeyWithWait(userNameField, AppConstants.MEDIUM_WAIT, un);
	ele.doSendKey(pwdField, pwd);
	ele.doClick(loginBtn);
	return new SauceHomePage(driver);
}



}
