package com.steamPages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.ActionsUtility.scrollToViewElement;
import static Utilities.ActionsUtility.sendKeys;
import static Utilities.waitUtility.explicitWaitUntilVisible;

public class SteamHomePage extends BasePage {

    private By discountsTab = By.xpath("//div[@class='tab_content'][text()='Ofertas']");
    private By discountsButton = By.xpath("//a[@class='btnv6_white_transparent btn_small_tall']/span[text()='Ofertas']");

    private By searchBar = By.xpath("//div[@class='_2AD2O_FEWy7izR8sZZjjI6']");
    private By searchButton = By.xpath("//button[@class='_1Yo-ZsBr-KbVMyAMXEYszd']");


    //search a game on the search bar
    public SteamHomePage(WebDriver driver){
        setDriver(driver);
    }

    public SteamSearchPage searchGame(String gameName){
        System.out.println("typing: " + gameName);
        sendKeys(find(searchBar), gameName);
        System.out.println("typed: " + gameName);
        click(searchButton);
        return new SteamSearchPage(driver, gameName);
    }

    //go to discounts
    /*private void clickOffersTab(){
        explicitWaitUntilVisible(discountsTab, 5);
        scrollToViewElement(discountsTab);
        click(discountsTab);
    }
    //go to discounts
    public SteamDiscountsPage clickDiscountsButton(){
        clickOffersTab();
        explicitWaitUntilVisible(discountsButton, 5);
        scrollToViewElement(discountsButton);
        click(discountsButton);
        return new SteamDiscountsPage();
    }*/

}
