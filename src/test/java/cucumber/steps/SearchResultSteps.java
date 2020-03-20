package cucumber.steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import pageobject.SearchResultPage;

import java.util.List;
import java.util.Map;

public class SearchResultSteps {
    @Then("I see {string} in search result page")
    public void i_see_in_search_result_page(String expectedResult) {
        String actualResult = new SearchResultPage(CommonSteps.getDriver()).getSearchResultTitle();
        Assert.assertEquals("Actual title and expected title not matched", actualResult, expectedResult);
    }

    @Then("I see the following in search result page")
    public void i_see_the_following_in_search_result_page(List<String> expectedTitles) {
        List<String> actualTitles = new SearchResultPage(CommonSteps.getDriver()).getAllResultTitle();
        Assert.assertEquals("Actual and expected titles do not match", actualTitles, expectedTitles);
    }

    @Then("I see the following search result items in search result page")
    public void i_see_the_following_search_result_items_in_search_result_page(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dataMap = dataTable.asMaps().get(0);
        String title = dataMap.get("title");
        String price = dataMap.get("price");
        String index = dataMap.get("index");

        String actualTitle = new SearchResultPage(CommonSteps.getDriver()).getAllResultTitle().get(Integer.parseInt(index));
        String actualPrice = new SearchResultPage(CommonSteps.getDriver()).getPrices().get(Integer.parseInt(index));
        Assert.assertEquals("Actual title and expected title did not match", actualTitle, title);
        Assert.assertEquals("Actual price and expected price did not match", actualPrice, price);

    }

}
