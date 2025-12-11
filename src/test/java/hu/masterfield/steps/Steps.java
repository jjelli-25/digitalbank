package hu.masterfield.steps;

import hu.masterfield.pages.LoginPage;
import hu.masterfield.pages.DepositPage;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class Steps {
    private LoginPage loginPage;
    private DepositPage depositPage;

    @Given("I open the Digital Bank site")
    public void i_open_the_digital_bank_site() {
        loginPage = new LoginPage(Hooks.getDriver(), Hooks.getWait());
        loginPage.open();
    }

    @Given("I accept cookies")
    public void i_accept_cookies() { loginPage.acceptCookiesIfPresent(); }

    @When("I sign in using a {string} and {string}")
    public void i_sign_in_using_a_and(String username, String password) { loginPage.login(username, password); }

    @Then("the Digital Bank home page opens")
    public void the_digital_bank_home_page_opens() { assertTrue(loginPage.isHomePageOpened()); }

    @Then("a {string} message is displayed")
    public void a_message_is_displayed(String expectedText) { assertTrue(loginPage.getWelcomeText().contains(expectedText)); }

    @Then("an error message is displayed with {string}")
    public void an_error_message_is_displayed_with(String expectedError) {
        assertTrue(loginPage.isErrorVisible());
        assertTrue(loginPage.getErrorMessage().contains(expectedError));
    }

    @Then("I stay on the login page")
    public void i_stay_on_the_login_page() { assertTrue(loginPage.isOnLoginPage()); }

    @And("I go to the {string} page")
    public void i_go_to_the_page(String pageName) {
        if ("Deposit".equalsIgnoreCase(pageName)) {
            depositPage = new DepositPage(Hooks.getDriver(), Hooks.getWait());
            depositPage.open();
            assertTrue(depositPage.isOnDepositPage());
        } else fail("Unsupported page: " + pageName);
    }

    @When("the user selects the account {string}")
    public void the_user_selects_the_account(String accountName) { ensureDepositPage(); depositPage.selectAccount(accountName); }

    @When("enters the deposit amount {string}")
    public void enters_the_deposit_amount(String amount) { ensureDepositPage(); depositPage.enterAmount(amount); }

    @When("clicks the {string} button")
    public void clicks_the_button(String buttonText) { ensureDepositPage(); depositPage.submit(); }


    @Then("View Checking Accounts page is open")
    public void viewCheckingAccountsPageIsOpen() { ensureDepositPage(); assertTrue(depositPage.isOnCheckingView()); }

    private void ensureDepositPage() { if (depositPage == null) depositPage = new DepositPage(Hooks.getDriver(), Hooks.getWait()); }
}
