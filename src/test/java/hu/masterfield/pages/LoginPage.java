package hu.masterfield.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private static final By COOKIES_OK_BUTTON = By.xpath("/html/body/div[1]/div/div/div[3]/button[1]");
    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By SIGN_IN_BUTTON = By.xpath("//*[@id='submit']");
    private static final By HOMEPAGE_WELCOME = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/ol/li");
    private static final By ERROR_ALERT = By.xpath("//div[contains(@class,'alert')]");

    public LoginPage(WebDriver driver, WebDriverWait wait) { super(driver, wait); }
    public void open() { driver.navigate().to(BASE_URL + "/bank/login"); waitVisible(USERNAME_INPUT); }
    public void acceptCookiesIfPresent() { if (isVisible(COOKIES_OK_BUTTON)) click(COOKIES_OK_BUTTON); }
    public void login(String username, String password) { type(USERNAME_INPUT, username); type(PASSWORD_INPUT, password); click(SIGN_IN_BUTTON); }
    public String getWelcomeText() { return getText(HOMEPAGE_WELCOME); }
    public boolean isHomePageOpened() { try { wait.until(ExpectedConditions.visibilityOfElementLocated(HOMEPAGE_WELCOME)); return isVisible(HOMEPAGE_WELCOME); } catch (TimeoutException e) { return false; } }
    public boolean isOnLoginPage() { return currentUrl().contains("/bank/login"); }
    public String getErrorMessage() { return getText(ERROR_ALERT); }
    public boolean isErrorVisible() { return isVisible(ERROR_ALERT); }
}
