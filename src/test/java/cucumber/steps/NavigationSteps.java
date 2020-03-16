package cucumber.steps;

import io.cucumber.java.en.Given;
import pageobject.NavigationPage;

public class NavigationSteps {

    @Given("I hoverover on 'WOMEN' tab in home page")
    public void i_hoverover_on_tab_in_home_page(){
        new NavigationPage(CommonSteps.getDriver()).hoverOverWomenTab();
    }

    @Given("I click on 'Evening Dresses' tab in navigation menu")
    public void i_click_on_tab_in_navigation_menu() {

        new NavigationPage(CommonSteps.getDriver()).hoverOverWomenTab().clickOnEveningDressesLink();
    }

}
