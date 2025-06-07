package sauce.qa.ecom.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import sauce.qa.ecom.basetest.BaseTest;
import sauce.qa.ecom.constants.AppConstants;

public class SauceLoginPageTest extends BaseTest {
	SoftAssert softAssert = new SoftAssert();
	@Test
	public void LoginUrlTest() {
		Assert.assertTrue(lp.getUrl().contains("sauce"));
	}

	@DataProvider
	public Object[][] userRegister() {
//		return new Object[][]{{"standard_user","secret_sauce"},{"problem_user","secret_sauce"}};
		return lp.getExcelData(AppConstants.REGISER_DATA_SHEET);
	}
	
	@Test(dataProvider = "userRegister")
			
	public void loginSuccessTest(String un, String pwd) {
		System.out.println(un+":"+pwd);
		hp = lp.doLogin(un,pwd);		
		String title = hp.getProductPageTitle();
		softAssert.assertTrue(title.contains("roduct"));
		softAssert.assertAll();
		hp.doLogout();
		
	}
}
