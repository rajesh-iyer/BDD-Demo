package com.xpanxion.bdd.demo.pages;

import com.xpanxion.bdd.demo.DriverFactory;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author abc
 */
public class BasePage {
    protected WebDriver driver;
    // protected WebDriver driver1;
    //protected WebDriver driver2;

    public BasePage() {
        this.driver = DriverFactory.getWebDriver();
    }

    public WebElement waitForElement(By by) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver t) {
                        return ((SearchContext) t).findElements(by).size() > 0;
                    }

                });
        return driver.findElement(by);
    }

    public List<WebElement> waitForElementsBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver t) {
                return t.findElements(by).size() > 0;
            }
        });
        return driver.findElements(by);
    }

    public List<WebElement> waitForElementsBy(WebElement element, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.until((WebDriver t) -> element.findElements(by).size() > 0);
        return driver.findElements(by);
    }

    public void moveToElement(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForElement(by)).build().perform();
    }

    public void selectItemByValue(WebElement element, String itemToSelect) {
        getSelect(element).selectByValue(itemToSelect);
    }

    public void selectItemByText(WebElement element, String text) {
        getSelect(element).selectByVisibleText(text);
    }

    public void selectItemByIndex(WebElement element, int index) {
        getSelect(element).selectByIndex(index);
    }

    public void clickButton(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasElement(By by) {
        return countElements(by) != 0;
    }

    public boolean hasNoElementAsExpected(By by) {
        WebElement element;
        element = new WebDriverWait(driver, 5).until(ExpectedConditions
                .presenceOfElementLocated(by));
        return element == null || !element.isDisplayed();
    }

    public JavascriptExecutor getJavaScriptExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public WebElement waitForElementGone(By by) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver t) {
                        return ((SearchContext) t).findElements(by).size() > 0;
                    }
                });
        return waitForElement(by);
    }

    public void clickElementWithJavascript(WebElement element) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click()", element);
        }
    }

    public void handledSleep(int sleepInSeconds) {
        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, sleepInSeconds);
        while (cal1.after(cal)) {
            cal = Calendar.getInstance();
        }
    }

    public void waitTillMultipleTabOpens() {
        Set<String> allWindows = driver.getWindowHandles();
        while (allWindows.size() == 1) {
            allWindows = driver.getWindowHandles();
        }
    }

    public boolean verifyElementSelected(WebElement element, boolean selected) {
        return (new WebDriverWait(driver, 5)).until(ExpectedConditions
                .elementSelectionStateToBe(element, selected));
    }

    public void switchToLastTab() {
        List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
    }

    public void switchToFirstTab() {
        Set<String> handles = driver.getWindowHandles();
        for (int i = handles.size(); i > 1; i--) {
            driver.switchTo().window(
                    handles.toArray(new String[handles.size()])[i - 1]);
            driver.close();
        }
        driver.switchTo().window(
                handles.toArray(new String[handles.size()])[0]);
    }

    public void closeTab() {
        driver.close();
        List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
    }

    public void scrollToElementAndClick(By by) {
        scrollToElement(by);
        waitForElement(by).click();
    }

    public void scrollToElement(By by) {
        int scrollBy = waitForElement(by).getLocation().y + 25;
        getJavaScriptExecutor().executeScript(
                "window.scrollBy(0," + scrollBy + ");");
    }

    public boolean isFileOpened(File file) {
        boolean res = false;
        FileLock lock = null;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            FileChannel channel = raf.getChannel();
            // Get an exclusive lock on the whole file
            lock = channel.lock();

            //The file is not already opened
            lock = channel.tryLock();
        } catch (OverlappingFileLockException | IOException e) {
            // File is open by someone else
            res = true;
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (lock != null) {
                    lock.release();
                    lock.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return res;
    }

    public String getTextFromElement(By by) {
        return waitForElement(by).getText();
    }

    public void selectRadioButtonByValue(By radioGroup, String ValueToSelect) {
        // Find the radio group element
        List<WebElement> radioLabels = driver.findElements(
                radioGroup);
        for (int i = 0; i < radioLabels.size(); i++) {
            if (radioLabels.get(i).getText().trim()
                    .equalsIgnoreCase(ValueToSelect.trim())) {
                radioLabels.get(i).click();
                break;
            }
        }
    }

    public Select getSelect(WebElement element) {
        Select select = new Select(element);
        return select;
    }

    public int countElements(By by) {
        int result = 0;
        long currentWaitMillis = 30000;
        try {
            if (currentWaitMillis > 0) {
                driver.manage().timeouts()
                        .implicitlyWait(0, TimeUnit.MILLISECONDS);
            }
            result = driver.findElements(by).size();
        } finally {
            driver.manage().timeouts()
                    .implicitlyWait(currentWaitMillis, TimeUnit.MILLISECONDS);
        }
        return result;
    }

    public void sendValuesToWebElement(WebElement element, String value) {
        element.sendKeys(value);
    }

    public void sendDate(WebElement element, CharSequence[] tmrwdate) {
        element.sendKeys(tmrwdate);

    }

    public boolean isDisabled(WebElement element) {
        return element.getAttribute("disabled").equalsIgnoreCase("disabled");
    }

    public boolean isEnabled(WebElement element) {
        return element.getAttribute("enabled").equalsIgnoreCase("enabled");
    }

    public void implictwait(int i) {
        driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);

    }

    public void actionsendkeys(String data) {
        Actions a = new Actions(driver);
        a.sendKeys(data).build().perform();

    }

    public void actionenter() {
        Actions a = new Actions(driver);
        a.sendKeys(Keys.ENTER).build().perform();
    }

    public void actiondownkey() {
        Actions a = new Actions(driver);
        a.sendKeys(Keys.DOWN).build().perform();
    }

    public void alertaccept() {
        /*try {
			driver.switchTo().alert().wait();
		} catch (InterruptedException e) {}*/
        driver.switchTo().alert().accept();

    }

    public void scrollup() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        //js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        js.executeScript("window.scrollBy(0,-600)", "");
    }

    public void scrolldown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)", "");
    }

    public void navigatetobackpage() {
        driver.navigate().back();
    }

    public void refresh() {
        driver.navigate().refresh();

    }

    public void doubleclick(WebElement element) {
        Actions a = new Actions(driver);
        a.doubleClick().build().perform();

    }

    public void tab(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    public boolean isDisplayed(By by) {
        return waitForElement(by).isDisplayed();
    }

    public void selectradiobutton(WebElement allcheckbox) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", allcheckbox);

    }

    public void robotpageup() throws AWTException {
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_PAGE_UP);
        robot.keyRelease(KeyEvent.VK_PAGE_UP);

    }

    public boolean isgreaterthanone(By greaterthanone) {

        System.out.println("-----");
        return waitForElement(greaterthanone).isDisplayed();
    }

    public boolean isElementPresent(By by, int timeOut) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        try {
            wait.withTimeout(timeOut, TimeUnit.SECONDS)
                    .pollingEvery(2, TimeUnit.SECONDS)
                    .until(new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver t) {
                            return ((SearchContext) t).findElements(by).size() > 0;
                        }

                    });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementPresent(By by) {
        return isElementPresent(by, 10);
    }
}
