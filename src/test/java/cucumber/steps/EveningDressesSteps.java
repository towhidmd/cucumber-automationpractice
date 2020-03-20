package cucumber.steps;

import io.cucumber.java.en.Then;
import pageobject.EveningDressesPage;

public class EveningDressesSteps {
    @Then("I see 'Evening Dresses'  title in evening dresses page")
    public void i_see_title_in_evening_dresses_page() {
        new EveningDressesPage(CommonSteps.getDriver()).getEveningPageTitle();
        // throw new io.cucumber.java.PendingException();
    }
}