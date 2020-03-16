package cucumber.steps;

import io.cucumber.java.en.Then;
import pageobject.NavigationPage;

public class EveningDressSteps {
    @Then("I see 'Evening Dress' title in evening dresses page")
    public void i_see_title_in_evening_dresses_page() {
        new NavigationPage(CommonSteps.getDriver()).hoverOverWomenTab().clickOnEveningDressesLink().getEveningPageTitle();

    }
}
