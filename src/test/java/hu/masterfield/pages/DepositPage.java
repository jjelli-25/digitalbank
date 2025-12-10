
package hu.masterfield.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object a Deposit (Befizetés) oldalhoz.
 * URL: https://eng.digitalbank.masterfield.hu/bank/account/deposit
 */
public class DepositPage extends BasePage {

    // --- Oldal URL-ek ---
    private static final String DEPOSIT_PATH = "/bank/account/deposit";
    // A Checking-view egy azonosított nézet a sikeres befizetés után – URL ellenőrzéssel detektáljuk.
    private static final String CHECKING_VIEW_PATH = "/bank/account/checking";

    // --- Lokátorok ---
    // Account for Deposit (legördülő)
    private static final By ACCOUNT_SELECT = By.id("selectedAccount");

    // Deposit Amount (összeg mező)
    private static final By AMOUNT_INPUT = By.id("amount");

    // Submit (beküldés) gomb
    private static final By SUBMIT_BUTTON = By.xpath("//*[@id='right-panel']/div[2]/div/div/div/div/form/div[2]/button[1]");

    // Opcionális: siker üzenet – ha van alert/success elem a DOM-ban; finomhangolható
    private static final By SUCCESS_ALERT = By.xpath("//div[contains(@class,'alert') and contains(@class,'success')]");

    public DepositPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /** Megnyitja a Deposit oldalt és megvárja, míg a fő elemek láthatóak. */
    public void open() {
        driver.navigate().to(BASE_URL + DEPOSIT_PATH);
        waitVisible(ACCOUNT_SELECT);
        waitVisible(AMOUNT_INPUT);
        waitVisible(SUBMIT_BUTTON);
    }

    /** Beállítja a kiválasztott számlát a legördülő listában. */
    public void selectAccount(String accountName) {
        Select select = new Select(waitVisible(ACCOUNT_SELECT));
        // Kiválasztás látható szöveg alapján (pl. "Main account")
        select.selectByVisibleText(accountName);
    }

    /** Megadja a befizetendő összeget (stringben, pl. "100.00"). */
    public void enterAmount(String amount) {
        type(AMOUNT_INPUT, amount);
    }

    /** Beküldi a befizetés űrlapot. */
    public void submit() {
        click(SUBMIT_BUTTON);
    }

    /** Igaz, ha már a Checking-view oldalra navigáltunk (sikeres befizetés). */
    public boolean isOnCheckingView() {
        try {
            // Várunk, amíg az URL a checking-view-ra vált, vagy megjelenik egy success jelzés.
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains(CHECKING_VIEW_PATH),
                    ExpectedConditions.visibilityOfElementLocated(SUCCESS_ALERT)
            ));
        } catch (TimeoutException ignored) {
        }
        return currentUrl().contains(CHECKING_VIEW_PATH);
    }

    /** Opcionális: visszaadja a siker üzenet szövegét, ha elérhető. */
    public String getSuccessMessage() {
        try {
            return getText(SUCCESS_ALERT);
        } catch (TimeoutException e) {
            return "";
        }
    }

    /** Ellenőrzi, hogy a Deposit oldalon vagyunk-e. */
    public boolean isOnDepositPage() {
        return currentUrl().contains(DEPOSIT_PATH);
    }
}
