package pageobject;

import helper.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationPage extends PageHelper {

    private By womenTabLocator = By.cssSelector("#block_top_menu a[title='Women']");
    private By eveningDressesLinkLocator = By.linkText("Evening Dresses");
    private WebDriver driver;


    public NavigationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    public NavigationPage hoverOverWomenTab() {
        moveToElement(womenTabLocator);
        return new NavigationPage(driver);
    }

    public EveningDressesPage clickOnEveningDressesLink() {
        clickOnElement(eveningDressesLinkLocator);
        return new EveningDressesPage(driver);
    }
}
