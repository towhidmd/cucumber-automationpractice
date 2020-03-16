package cucumber.steps;

import com.google.common.net.MediaType;
import helper.CommonHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;

public class CommonSteps {

    private static WebDriver driver;

    @Before
    public void setup() {
        driver = CommonHelper.loadWebDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
       // if(scenario.isFailed()){
            byte[] screenshort = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshort, String.valueOf(MediaType.PNG),scenario.getName());
       // }

//        if(driver!=null){
//            driver.quit();

    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Given("I navigate to 'automation practice 'website in the browser")
    public void i_navigate_to_website_in_the_browser() {
        driver.get(CommonHelper.getEnvironmentProperty("app.url"));

    }
}
