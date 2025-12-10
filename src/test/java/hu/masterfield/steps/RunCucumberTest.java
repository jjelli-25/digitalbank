
package hu.masterfield.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"hu.masterfield.steps"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        // Ha szeretnél csak login+deposit tagekre szűrni: tags = "@LoginFeature or @DepositFeature",
        monochrome = true
)
public class RunCucumberTest {
    // Üres – a Cucumber Runner végzi a futtatást.
}
