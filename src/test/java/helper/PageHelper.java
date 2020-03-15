package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static helper.CommonHelper.TIMEOUT_SECONDS_DEFAULT;
import static helper.CommonHelper.TIMEOUT_SECONDS_SIXTY;
import static helper.CommonHelper.setDriverTimeout;
import static helper.CommonHelper.takeScreenshot;
import static org.junit.Assert.fail;

public abstract class PageHelper {

    public WebDriver driver;
    private JavascriptExecutor js;

    public PageHelper(WebDriver webDriver) {
        this.driver = webDriver;
        driver.manage().timeouts().pageLoadTimeout(TIMEOUT_SECONDS_SIXTY, TimeUnit.SECONDS);
        setDriverTimeout(TIMEOUT_SECONDS_DEFAULT);
    }


    public boolean isElementFound(By by, int timeoutSeconds) {
        boolean elementFound = false;
        try {
            setDriverTimeout(0);
            new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
            elementFound = true;
        } catch (Exception ignore) {

        } finally {
            setDriverTimeout(TIMEOUT_SECONDS_DEFAULT);
        }
        return elementFound;
    }

    public void setField(By by, String string) {

        setField(TIMEOUT_SECONDS_DEFAULT, by, string);
    }

    public void setField(int timeoutSeconds, By by, String string) {
        findWebElementBy(timeoutSeconds, by).clear();
        findWebElementBy(timeoutSeconds, by).sendKeys(string);
    }

    public void clickOnElement(By by) {
        clickOnElement(TIMEOUT_SECONDS_DEFAULT, by);
    }

    public void clickOnElement(int timeoutSeconds, By by) {
        try {
            setDriverTimeout(0);
            new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();
        } catch (Exception e) {
            fail("!!!!!!!!!!!!!!! Failed to locate element by [" + by + "].\n" + e.getStackTrace());
        } finally {
            setDriverTimeout(TIMEOUT_SECONDS_DEFAULT);
        }
    }

    public void selectByVisibleText(By by, String text) {
        new Select(findWebElementBy(TIMEOUT_SECONDS_DEFAULT, by)).selectByVisibleText(text);
    }

    public String getText(int timeoutSeconds, By by) {
        return findWebElementBy(timeoutSeconds, by).getText().replaceAll("\n", "").trim();
    }

    public String getText(By by) {
        return getText(TIMEOUT_SECONDS_DEFAULT, by);
    }

    public List<String> getStringList(By by) {
        return getStringList(TIMEOUT_SECONDS_DEFAULT, by);
    }

    public List<String> getStringList(int timeoutSeconds, By by) {
        List<String> listStrings = new ArrayList<>();
        List<WebElement> webElements = findWebElementsBy(timeoutSeconds, by);
        for (WebElement element : webElements) {
            listStrings.add(element.getText().replaceAll("\\s+", " ").trim());
        }
        return listStrings;
    }

    public Map<String, String> getStringMap(List<String> headerName, List<String> columnValue) {
        Map<String, String> map = new HashMap<>();

        if (headerName.size() == columnValue.size()) {
            for (int i = 0; i < headerName.size(); i++) {
                map.put(headerName.get(i), columnValue.get(i));
            }
        } else {
            fail("Failed to create map, header and column value size not matched.\nHeader Value: " + headerName.size() + "\nRow Value: " + columnValue.size());
        }
        return map;
    }

    public WebElement findWebElementBy(By by) {
        return findWebElementBy(TIMEOUT_SECONDS_DEFAULT, by);
    }

    public WebElement findWebElementBy(int timeoutSeconds, By by) {
        WebElement element = null;
        try {
            setDriverTimeout(0);
            new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
            element = driver.findElement(by);
        } catch (Exception e) {
            takeScreenshot();
            fail("Failed to locate element by [" + by + "]\n" + e.getStackTrace());
        }
        return element;
    }

    public List<WebElement> findWebElementsBy(By by) {
        return findWebElementsBy(TIMEOUT_SECONDS_DEFAULT, by);
    }

    public List<WebElement> findWebElementsBy(int timeoutSeconds, By by) {
        return new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    /**
     * Set checkbox if not selected
     *
     * @param checkBox
     */
    public void setCheckbox(By checkBox) {
        WebElement webElement = findWebElementBy(checkBox);
        if (!webElement.isSelected()) {
            webElement.click();
        }
    }

    /**
     * Handle mouseover action using Actions moveToElement()
     *
     * @param locator
     * @return
     */
    public WebElement moveToElement(By locator) {
        WebElement element = findWebElementBy(locator);
        new Actions(driver).moveToElement(element).build().perform();
        return element;
    }

    /**
     * Set UI element attribute.
     *
     * @param element
     * @param attrName
     * @param contains
     * @param replace
     */
    public void setAttribute(WebElement element, String attrName, String contains, String replace) {
        if (element.getAttribute(attrName).contains(contains)) {
            element.getAttribute(attrName).replace(contains, replace);
        }
    }

    public void acceptBrowserAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SECONDS_DEFAULT);
            wait.until(ExpectedConditions.alertIsPresent()).accept();
            wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        } catch (Exception e) {
            //ignore
        }
    }

    /**
     * Switching to new window by Window index. Zero based window index.
     *
     * @param timeOutSec
     * @param expectedNumOfWin
     * @param switchToWindowIndex
     */
    public void switchToWindowByIndex(int timeOutSec, int expectedNumOfWin, int switchToWindowIndex) {
        try {
            setDriverTimeout(0);
            new WebDriverWait(driver, timeOutSec).until(ExpectedConditions.numberOfWindowsToBe(expectedNumOfWin));
            List<String> windows = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(windows.get(switchToWindowIndex));
        } catch (Exception e) {
            fail("Unable to switch to window by index: [" + switchToWindowIndex + "]\n" + e.getMessage());
        } finally {
            setDriverTimeout(TIMEOUT_SECONDS_DEFAULT);
        }
    }

    /**
     * JavascriptExecutor to interact with the browser elements.
     * Use these instead of sendKeys(), click() if facing challenges to interact on special UI framework.
     *
     * @param elementId
     * @param value
     */
    public void jsExecutorEnterById(String elementId, String value) {
        js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsById('" + elementId + "')[0].value='" + value + "'");
    }

    public void jsExecutorEnterByName(String elementName, String value) {
        js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByName('" + elementName + "')[0].value='" + value + "'");
    }

    public void jsExecutorClickById(String elementId) {
        if (findWebElementBy(By.id(elementId)).isDisplayed()) {
            js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('" + elementId + "').click();");
        }
    }

    public void jsExecutorClickByName(String elementName) {
        if (findWebElementBy(By.name(elementName)).isDisplayed()) {
            js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementByName('" + elementName + "').click();");
        }
    }


}
