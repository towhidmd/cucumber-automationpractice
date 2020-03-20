package cucumber.steps;

import io.cucumber.java.en.Given;
import pageobject.NavigationPage;

public class NavigationSteps {
    @Given("I hover-over on 'WOMEN' tab in home page")
    public void i_hover_over_on_tab_in_home_page() {
        new NavigationPage(CommonSteps.getDriver()).hoverOverWomenTab();
        //throw new io.cucumber.java.PendingException();
    }

    @Given("I click on 'Evening dresses' tab in navigation menu")
    public void i_click_on_tab_in_navigation_menu() {
        new NavigationPage(CommonSteps.getDriver()).hoverOverWomenTab().clickOnEveningDressesLink();
        //throw new io.cucumber.java.PendingException();
    }
}