package pageobject;

import helper.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchResultPage extends PageHelper {
    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

    private By searchResultTileLocator = By.cssSelector(".product_list .product-name");
    private By priceLocator = By.cssSelector(".right-block div[itemprop='offers'] span[itemprop='price']");

    public String getSearchResultTitle() {
        return getText(searchResultTileLocator);
    }

    public List<String> getAllResultTitle() {
        return getStringList(searchResultTileLocator);
    }

    public List<String> getPrices() {
        return getStringList(priceLocator);
    }

    public String getPrice() {
        return getText(priceLocator);
    }
}