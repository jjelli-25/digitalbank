package hu.masterfield.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
protected final WebDriver driver;
protected final WebDriverWait wait;
protected static final String BASE_URL = System.getProperty("baseUrl", "https://eng.digitalbank.masterfield.hu");

protected BasePage(WebDriver driver, WebDriverWait wait) {
this.driver = driver;
this.wait = wait != null ? wait : new WebDriverWait(driver, Duration.ofSeconds(10));
}

protected WebElement waitVisible(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
protected WebElement waitClickable(By locator) { return wait.until(ExpectedConditions.elementToBeClickable(locator)); }
protected void click(By locator) { waitClickable(locator).click(); }
protected void type(By locator, String text) { WebElement el = waitVisible(locator); el.clear(); if (text != null) el.sendKeys(text); }
protected boolean isVisible(By locator) { try { return driver.findElement(locator).isDisplayed(); } catch (NoSuchElementException e) { return false; } }
protected String getText(By locator) { return waitVisible(locator).getText().trim(); }
public String currentUrl() { return driver.getCurrentUrl(); }
}
