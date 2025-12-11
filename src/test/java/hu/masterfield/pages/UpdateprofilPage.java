
package hu.masterfield.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateprofilPage extends BasePage {

    private static final String PROFILE_PATH = "/bank/user/profile";

    // Selectors (megadott szelektorok alapján)
    private static final By MOBILE_PHONE_INPUT = By.id("mobilePhone");
    private static final By SUBMIT_BUTTON = By.xpath("/html/body/div[1]/div[2]/div/div/div/div/form/div[2]/button[1]");
    private static final By SUCCESS_TEXT = By.xpath("/html/body/div[1]/div[2]/div/div/div/div/form/div[1]/div[1]/div/span[2]");

    public UpdateprofilPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.navigate().to(BASE_URL + PROFILE_PATH);
        // Oldal betöltődésének és fő elemek láthatóságának biztosítása
        waitVisible(MOBILE_PHONE_INPUT);
        waitVisible(SUBMIT_BUTTON);
    }

    public boolean isOnProfilePage() {
        return currentUrl().contains(PROFILE_PATH);
    }

    /** (###) ###-#### formátumot várunk pl. "(062) 012-3456" */
    public void enterMobilePhone(String phone) {
        type(MOBILE_PHONE_INPUT, phone);
    }

    public void submit() {
        click(SUBMIT_BUTTON);
    }

    public String getSuccessMessage() {
        return getText(SUCCESS_TEXT);
    }
}
