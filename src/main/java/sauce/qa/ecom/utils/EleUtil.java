package sauce.qa.ecom.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.aspectj.weaver.patterns.ThrowsPattern;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import sauce.qa.ecom.constants.AppConstants;
import sauce.qa.ecom.exception.FrameWorkException;
import sauce.qa.ecom.factory.DriverFactory;

public class EleUtil {
	private WebDriver driver;
	private JsUtil js;

	public EleUtil(WebDriver driver) {
		this.driver = driver;
		js = new JsUtil(driver);
	}

	public void launchUrl(String url) {
		if (url == null) {
			throw new FrameWorkException("the url is null");
		}
		if (url.toLowerCase().contains("http")) {
			driver.get(url);
		} else {
			throw new FrameWorkException("The protocol http is missing");
		}
	}

	/**
	 * This method return the current page url
	 * 
	 */
	public String getUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	/**
	 * ValidatePageSource is validating the page contains the value passed in
	 * argument.
	 * 
	 */
	public boolean ValidatePageSource(String value) {
		if (driver.getPageSource().contains(value)) {
			return true;
		}
		return false;
	}

	/**
	 * Getting the page title and print and return
	 * 
	 * @return
	 */
	public String getPageTitle() {
		@Nullable
		String title = driver.getTitle();
		System.out.println(title);
		return title;
	}

	/**
	 * Closing the browser if not null
	 */
	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
		System.out.println("driver not available");
	}

	public void isEleHighlight(WebElement ele) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			try{js.drawBorderJs(ele);}
			catch (JavascriptException e) {
				e.printStackTrace();
//				throw new FrameWorkException("JAVASCRIPT FAILED CHECK SCRIPT"); 
			}
			catch (Exception e) {
				throw new FrameWorkException("highlight not possible"); 
			}
		}
	}

	public WebElement getEle(By locator) {
		WebElement WebEle = driver.findElement(locator);
		isEleHighlight(WebEle);
		return WebEle;
	}

	public void doSendKey(By locator, String value) {
		getEle(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		WebElement ele = getEle(locator);
		isEleHighlight(ele);
		ele.click();
	}

	public String doGetText(By locator) {
		String text = getEle(locator).getText();
		return text;
	}

	public String doGetAttributeValue(By locator, String attribute) {
		String attributeText = getEle(locator).getAttribute(attribute);
		return attributeText;
	}

	public List<WebElement> getElements(By locator) {
		List<WebElement> eleList = driver.findElements(locator);
		return eleList;
	}

	public int getElementsCount(By locator) {
		int count = getElements(locator).size();
		return count;
	}

	public List<String> doElesGetText(By locator) {
		List<WebElement> elements = getElements(locator);
		List<String> textList = new ArrayList<>();

		for (WebElement e : elements) {
			String text = e.getText();
			if (text.length() != 0) {
				textList.add(text);
			}
		}
		return textList;
	}

	public List<String> getElementsAttribValue(By locator, String Attribute) {
		List<WebElement> elements = getElements(locator);
		List<String> attribs = new ArrayList<>();
		for (WebElement e : elements) {
			String attribValue = e.getAttribute(Attribute);
			attribs.add(attribValue);
		}
		return attribs;
	}

	public void listElesClickOne(By dropDownEle, By listElements, String expValue) {
		List<WebElement> elements = getElements(listElements);
		for (WebElement e : elements) {
			String textValues = e.getText();
			if (textValues.contains(expValue)) {
				e.click();
			}
		}
	}

	public void googleSearchClick(By searchEle, By listSuggestion, String searchKey, String expKey) {
		driver.findElement(searchEle).sendKeys(searchKey);
		List<WebElement> findElements = driver.findElements(listSuggestion);
		for (WebElement e : findElements) {
			String textVal = e.getText();
			if (textVal.contains(expKey)) {
				e.click();
			}

		}
	}

	public boolean checkSingleElePresent(By locator) {
		return driver.findElements(locator).size() == 1 ? true : false;
	}

	public boolean checkMultiElePresent(By locator) {
		return driver.findElements(locator).size() > 0 ? true : false;
	}

	private Select selObject(By locator) {//////// *************************
		Select sel = new Select(getEleWithWaitForVisiblility(locator, AppConstants.LONG_WAIT));
		return sel;
	}

	public void selDrpDwnByIndex(By locator, int index) {
//		Select sel = new Select(getEle(locator));
		selObject(locator).selectByIndex(index);
	}

	public void selDrpDwnByValue(By locator, String value) {
//		Select sel = new Select(getEle(locator));
		selObject(locator).selectByValue(value);
	}

	public void selDrpDwnByVisibleText(By locator, String text) {
//		Select sel = new Select(getEle(locator));
		selObject(locator).selectByVisibleText(text);
	}

	public void selDropSelectEle(By locator, String value) {
//		Select sel = new Select(getEle(locator));
		List<WebElement> options = selObject(locator).getOptions();
		System.out.println(options.size());
		for (WebElement e : options) {
			String text = e.getText();
//		System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public ArrayList<String> selDropDwnOptionText(By locator) {
//		Select sel = new Select(getEle(locator));
		List<WebElement> options = selObject(locator).getOptions();
		ArrayList<String> textList = new ArrayList<>();
		for (WebElement e : options) {
			String text = e.getText();
			textList.add(text);
			System.out.println(text);
		}
		return textList;
	}

	public int selDrpDownOptionCount(By locator) {
//		Select sel = new Select(getEle(locator));
		List<WebElement> options = selObject(locator).getOptions();
		int size = options.size();
		return size;
	}

	public void DropDownSelectOneValue(By locator, String value) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void DropDownSelectMultipleValue(By locator, String... values) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			for (String value : values) {
				value.contains(text);
				System.out.println(value);
				e.click();
			}

		}
	}

	public boolean isDropDownMultipleCheck(By locator) {
//		Select sel = new Select(getEle(locator));
		return selObject(locator).isMultiple() ? true : false;
	}

	/**
	 * This method is mainly used for select the drop in the below case 1.Select all
	 * the elements by passing All 2.Select one element with visible text 3.Select
	 * multiple element pass at the end with , Note: the locator 1st one should
	 * point select -2nd one should point the options to gell all elements
	 * 
	 * @param locator
	 * @param locatorAll-
	 * @param values
	 */
	public void selMultipleDropDownValues(By locator, By locatorAll, String... values) {
		if (isDropDownMultipleCheck(locator)) {
			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> options = driver.findElements(locatorAll);
				for (WebElement e : options) {
					e.click();
				}
			}
			for (String value : values) {

				selObject(locator).selectByVisibleText(value);
			}
		}
	}

	/**
	 * This will check the presence of the element in the DOM. this is not mandatory
	 * to visible in the page or not.
	 * 
	 * @param locator
	 * @param time
	 * @return
	 */
	public WebElement waitforPresenceOfElement(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isEleHighlight(ele);
		return ele;
	}

	public String getTextWithWaitForPresence(By locator, int time) {
		return waitforPresenceOfElement(locator, time).getText();
	}

	/**
	 * return elements:: check for the elements presence and return list.
	 * 
	 * @param locator
	 * @param time
	 * @return
	 */
	public List<WebElement> getEleWithWaitForPresence(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * return Element:This will check the visiblity in the interface not only the
	 * DOM not only the visible and also check for hight and width greater than 0
	 * 
	 * @param locator
	 * @param time
	 * @return
	 */
	public WebElement getEleWithWaitForVisiblility(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isEleHighlight(ele);
		return ele;
	}

	/**
	 * return Elements::This will check wait for all the elements and provide the
	 * list of web Elements
	 * 
	 * @param locator
	 * @param time
	 * @return
	 */
	public List<WebElement> getElesWithwaitforVisiblility(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		List<WebElement> listEle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return listEle;
	}

	public String getTextWithWaitForVisiblility(By locator, int time) {
		return getEleWithWaitForVisiblility(locator, time).getText();
	}

	public void doClickWithWait(By locator, int time) {
		WebElement ele=getEleWithWaitForVisiblility(locator, time);
		isEleHighlight(ele);
		ele.click();
				
	}

	public void doMoveToElement(By locator, int time) {

		getEleWithWaitForVisiblility(locator, time);
	}

	public void doSendKeyWithWait(By locator, int time, String msg) {
		getEleWithWaitForVisiblility(locator, time).sendKeys(msg);
	}

	public void waitForFrameByElement(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public void waitForFrameByIndex(int index, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}

	public void waitForFrameByIDOrName(String IDOrName, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDOrName));
	}

	public void waitForFrameByElement(WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}

	public String waitWithGetTitle(String fractionTitle, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle))) {
				return driver.getTitle();

			}
		} catch (InvalidElementStateException e) {
			throw new InvalidElementStateException(e);
		}
		return null;
	}

	public String waitAndGetTitle(String fullTitle, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			if (wait.until(ExpectedConditions.titleIs(fullTitle))) {
				return driver.getTitle();

			}
		} catch (TimeoutException e) {
			System.out.println("the title is not available");
			e.printStackTrace();
		}
		return null;
	}

	public String waitAndGetURLwithfraction(String fractionURL, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			if (wait.until(ExpectedConditions.urlContains(fractionURL))) {
				return driver.getCurrentUrl();

			}
		} catch (TimeoutException e) {
			System.out.println("the title is not available");
			e.printStackTrace();
		}
		
		return null;
	}

	public String waitAndGetURL(String fullURL, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			if (wait.until(ExpectedConditions.urlToBe(fullURL))) {
				return driver.getCurrentUrl();

			}
		} catch (TimeoutException e) {
			System.out.println("the title is not available");
			e.printStackTrace();
		}
		return null;
	}

	public Alert waitForAlert(int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public void waitForJSAlertAccept(int time) {
		waitForAlert(time).accept();
	}

	public void waitForJSAlertDismiss(int time) {
		waitForAlert(time).accept();
	}

	public void waitForJSPromptSendMsg(String msg, int time) {
		waitForAlert(time).sendKeys(msg);
		waitForAlert(time).accept();
	}

	public WebElement customWaitVisibility(By locator, int time, int sleep) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time), Duration.ofSeconds(sleep));
		WebElement ele= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isEleHighlight(ele);
		return ele;
	}

	public boolean getWindowCount(int windowsCount, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			if (wait.until(ExpectedConditions.numberOfWindowsToBe(windowsCount))) {
				return true;
			}
		} catch (TimeoutException e) {
			System.out.println("no of windows are not same");
			e.printStackTrace();
		}
		return false;
	}

	public void waitWithClickableElementClick(By locator, int windowsCount, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public WebElement retryElement(By locator, int durationMilliSec, int noOfTry) {
		WebElement ele = null;
		int attempt = 0;
		while (attempt < noOfTry) {
			try {
				ele = getEle(locator);
				System.out.println("ele is found");
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Element is not found");
				try {
					Thread.sleep(durationMilliSec);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempt++;
		}
		if (ele == null) {
			System.out.println("element not found after the " + noOfTry + "trying also..ElementUtil.");
		}
		return ele;

	}

	public void pageLoadTimeOut(int time) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
	}

	public void moveToEleByAction(By locator, int time) {
		Actions act = new Actions(driver);
		act.moveToElement(getEleWithWaitForVisiblility(locator, time)).build();
	}

	public void moveToEleAndClickByAction(By locator, int time) {
		Actions act = new Actions(driver);
		act.moveToElement(getEleWithWaitForVisiblility(locator, time))
				.click(getEleWithWaitForVisiblility(locator, time)).build().perform();
	}

	public void clickByAction(By locator, int time) {
		Actions act = new Actions(driver);
		act.click(getEleWithWaitForVisiblility(locator, time)).build().perform();
	}

}
