package hu.masterfield.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DepositPage extends BasePage {
    private static final String DEPOSIT_PATH = "/bank/account/deposit";
    private static final String CHECKING_VIEW_PATH = "/bank/account/checking-view";

    private static final By ACCOUNT_SELECT = By.id("selectedAccount");
    private static final By AMOUNT_INPUT = By.id("amount");
    private static final By SUBMIT_BUTTON = By.xpath("//*[@id='right-panel']/div[2]/div/div/div/div/form/div[2]/button[1]");
    private static final By SUCCESS_ALERT = By.xpath("//div[contains(@class,'alert') and contains(@class,'success')]");

    public DepositPage(WebDriver driver, WebDriverWait wait) { super(driver, wait); }

    public void open() {
        driver.navigate().to(BASE_URL + DEPOSIT_PATH);
        waitVisible(ACCOUNT_SELECT);
        waitVisible(AMOUNT_INPUT);
        waitVisible(SUBMIT_BUTTON);
    }

    public void selectAccount(String accountName) {
        Select select = new Select(waitVisible(ACCOUNT_SELECT));
        select.selectByVisibleText(accountName);
    }

    public void enterAmount(String amount) { type(AMOUNT_INPUT, amount); }
    public void submit() { click(SUBMIT_BUTTON); }

    public boolean isOnCheckingView() {
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains(CHECKING_VIEW_PATH),
                ExpectedConditions.visibilityOfElementLocated(SUCCESS_ALERT)
            ));
        } catch (TimeoutException ignored) {}
        return currentUrl().contains(CHECKING_VIEW_PATH);
    }

    public String getSuccessMessage() {
        try { return getText(SUCCESS_ALERT); } catch (TimeoutException e) { return ""; }
    }

    public boolean isOnDepositPage() { return currentUrl().contains(DEPOSIT_PATH); }
}
