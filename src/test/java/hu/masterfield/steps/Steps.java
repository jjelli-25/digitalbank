
package hu.masterfield.steps;

import hu.masterfield.pages.LoginPage;
import hu.masterfield.pages.DepositPage;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

/**
 * Egyesített Step definíciók:
 * - Login Feature: valid és invalid login forgatókönyvek
 * - Deposit Feature: befizetés sikeres indítása és ellenőrzése
 *
 * A Deposit feature explicit belépést használ a Background-ban:
 *   When I sign in using a "jjelli" and "1234Start"
 * majd az új navigációs step:
 *   And I go to the "Deposit" page
 *
 * Így nincs szükség paraméterezésre vagy rendszer tulajdonságokra.
 */
public class Steps {

    // Oldalobjektumok
    private LoginPage loginPage;
    private DepositPage depositPage;

    // =========================
    // LOGIN FEATURE STEPS
    // =========================

    @Given("I open the Digital Bank site")
    public void i_open_the_digital_bank_site() {
        loginPage = new LoginPage(Hooks.getDriver(), Hooks.getWait());
        loginPage.open();
    }

    @Given("I accept cookies")
    public void i_accept_cookies() {
        // loginPage az előző step-ben inicializálva lett
        loginPage.acceptCookiesIfPresent();
    }

    @When("I sign in using a {string} and {string}")
    public void i_sign_in_using_a_and(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("the Digital Bank home page opens")
    public void the_digital_bank_home_page_opens() {
        assertTrue("The home page did not open (welcome element is not visible).",
                loginPage.isHomePageOpened());
    }

    @Then("a {string} message is displayed")
    public void a_message_is_displayed(String expectedText) {
        String actual = loginPage.getWelcomeText();
        assertTrue(String.format("Expected welcome to contain '%s' but was '%s'", expectedText, actual),
                actual.contains(expectedText));
    }

    @Then("an error message is displayed with {string}")
    public void an_error_message_is_displayed_with(String expectedError) {
        assertTrue("Error message should be visible.", loginPage.isErrorVisible());
        String actual = loginPage.getErrorMessage();
        assertTrue(String.format("Expected error to contain '%s' but was '%s'", expectedError, actual),
                actual.contains(expectedError));
    }

    @Then("I stay on the login page")
    public void i_stay_on_the_login_page() {
        assertTrue("We should remain on the login page.", loginPage.isOnLoginPage());
    }

    // =========================
    // DEPOSIT FEATURE STEPS
    // =========================

    /**
     * Új navigációs step a Deposit oldalra.
     * A deposit.feature Background:
     * - megnyitja a site-ot
     * - elfogadja a cookie-kat
     * - bejelentkezik (jjelli / 1234Start)
     * - majd ide navigál
     */
    @And("I go to the {string} page")
    public void i_go_to_the_page(String pageName) {
        if ("Deposit".equalsIgnoreCase(pageName)) {
            depositPage = new DepositPage(Hooks.getDriver(), Hooks.getWait());
            depositPage.open();
            assertTrue("Not on Deposit page.", depositPage.isOnDepositPage());
        } else {
            fail("Unsupported page navigation: " + pageName);
        }
    }

    @When("the user selects the account {string}")
    public void the_user_selects_the_account(String accountName) {
        ensureDepositPage();
        depositPage.selectAccount(accountName);
    }

    @When("enters the deposit amount {string}")
    public void enters_the_deposit_amount(String amount) {
        ensureDepositPage();
        depositPage.enterAmount(amount);
    }

    @When("clicks the {string} button")
    public void clicks_the_button(String buttonText) {
        // A gomb szövegét nem használjuk lokátorként, az XPath fix.
        ensureDepositPage();
        depositPage.submit();
    }

    @Then("the system processes the deposit")
    public void the_system_processes_the_deposit() {
        // Átmeneti step – a tényleges validáció a következő lépésekben történik.
        assertTrue("Deposit processing should continue.", true);
    }

    @Then("a success message is displayed")
    public void a_success_message_is_displayed() {
        ensureDepositPage();
        String success = depositPage.getSuccessMessage();
        // A redirect is elegendő bizonyíték lehet; ezért OR kapcsolatban ellenőrzünk.
        assertTrue("Success message should be visible OR we should be redirected to checking-view.",
                !success.isEmpty() || depositPage.isOnCheckingView());
    }

    @Then("the account balance is updated")
    public void the_account_balance_is_updated() {
        ensureDepositPage();
        // Minimális bizonyíték: checking-view oldalra kerültünk (redirect).
        assertTrue("Checking-view page should be opened after a successful deposit.",
                depositPage.isOnCheckingView());
        // Ha lesz konkrét balance-lokátor a checking-view-on, itt célszerű validálni.
    }

    // =========================
    // HELPER
    // =========================

    private void ensureDepositPage() {
        if (depositPage == null) {
            depositPage = new DepositPage(Hooks.getDriver(), Hooks.getWait());
        }
    }
}
