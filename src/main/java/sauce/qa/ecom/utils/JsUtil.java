package sauce.qa.ecom.utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JsUtil {

	JavascriptExecutor js;
	private WebDriver driver;
	public JsUtil(WebDriver driver) {
		this.js=(JavascriptExecutor) driver;
		this.driver=driver;
	}
	
	public String getTitleByJS() {
//	JavascriptExecutor js=(JavascriptExecutor) driver;
	String title = js.executeScript("return document.title").toString();
	return title;
	}
	public String getURLByJS() {
//		JavascriptExecutor js=(JavascriptExecutor) driver;
		String url = js.executeScript("return document.URL").toString();
		return url;
		}
	/**
	 * This alert is generated in automation for demo manually generated alert
	 * 
	 * @param msg
	 */
	public void generateAlertByJS(String msg) {
		js.executeScript("alert('"+msg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
}
	
	
	/**
	 * This prompt is generated in automation for demo manually generated
	 * 
	 * @param msg
	 */
	public void generatePromptByJS(String msg) {
		js.executeScript("prompt('"+msg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
}
	/**
	 * This confirm is generated in automation for demo manually generated
	 * @param msg
	 */
	public void generateConfirmByJS(String msg) {
		js.executeScript("confirm('"+msg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
}
	public void pageFwdByJS() {
		js.executeScript("history.go(1)");
	}
	public void pageBackByJS() {
		js.executeScript("history.go(-1)");
	
	}
	public void pageRefreshByJS() {
		js.executeScript("history.go(0)");
	
	}
	public void jsScrollToBtm() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	
	}
	public void jsScrollToBtm(String height) {
		js.executeScript("window.scrollTo(0,"+height+")");
	
	}
	public void jsScrollToTop() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	
	}
	public void drawBorderJs(WebElement ele) {
//		js.executeScript("arguement[0].style.border='3px red'",ele);
		for(int i=0;i<6;i++) {
			js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid red;');", ele);
			js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid yellow;');", ele);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void flashEleByJs(WebElement ele) {
		String bgcolor = ele.getCssValue("color");
//		System.out.println(bgcolor);
		for(int i=0;i<10;i++) {
			changeColorJs("rgb(0,200,100)", ele);
			changeColorJs(bgcolor, ele);
		}
	}
	
	public void changeColorJs(String color, WebElement ele) {
		js.executeScript("arguement[0].style.color='"+color+"'",ele);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public Boolean pageLoadReadyByJS(int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState =='complete'")).toString();
		return Boolean.parseBoolean(flag);
	}
	

}
