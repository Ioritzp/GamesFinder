package com.steamPages;

import Utilities.Utility;
import com.Objects.GameItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static Utilities.GetUtility.getText;
import static Utilities.Utility.*;
import static Utilities.waitUtility.waitUntilUrlContains;

public class SteamGamePage extends SteamHomePage{

    //private By priceWrapperLocator = By.className("game_area_purchase_game_wrapper");
    private By priceWrapperLocator = By.xpath("//div[normalize-space(@class)='game_area_purchase_game']");
    private By discountedPriceSource = By.xpath("//div[@class='discount_final_price']");
    private By normalPriceSource = By.xpath("//div[@class='game_purchase_price price']");
    private By originalPriceSource = By.xpath("//div[@class='discount_original_price']");
    private By gameNameSource = By.id("appHubAppName");
    private By showAllDlc = By.id("dlc_show_all_link");

    public GameItem getFinalPrice(String url){
        waitUntilUrlContains(5, url);
        waitForVisible(priceWrapperLocator);
        scrollToElementActions(priceWrapperLocator);
        String gameName = "";
        String link = "";
        String regularPrice = "";
        String finalPrice = "";
        WebElement firstWrapper = driver.findElement(priceWrapperLocator);
        List<WebElement> discountElement = firstWrapper.findElements(By.className("discount_final_price"));
        if(!discountElement.isEmpty()) {
            System.out.println("entering is discounted route");
            waitForVisible(discountedPriceSource);
            scrollToElementActions(discountedPriceSource);
            System.out.println("Discounted game");
           finalPrice = getText(discountedPriceSource);
           link = driver.getCurrentUrl();
           regularPrice = getText(originalPriceSource);
           gameName = getText(gameNameSource);
           return new GameItem(gameName, link, regularPrice, finalPrice);
        } else {
            System.out.println("entering normal price route");
            waitForVisible(normalPriceSource);
            scrollToElementActions(normalPriceSource);
            System.out.println("regular priced game");
            finalPrice = getText(normalPriceSource);
            link = driver.getCurrentUrl();
            regularPrice = "Not discounted";
            gameName = getText(gameNameSource);
            return new GameItem(gameName, link, regularPrice, finalPrice);
        }

    }


}
