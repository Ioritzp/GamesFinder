package com.steamPages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Utilities.waitUtility.explicitWaitUntilVisible;

public class SteamSearchPage extends SteamHomePage {
    private String gameName;

    private By firstInList = By.cssSelector("#search_resultsRows > a:first-child");

    public Object clickOnGame(){
        explicitWaitUntilVisible(firstInList, 5);
        //TODO: if the game name of 'firstinlist' doesn't match with the searched game, it must return a "game not found" text, set everything on the gameitem object to null -except the name- and continue the search.
        click(firstInList);

        //checks the incoming url to see if it has an agecheck or derives directly to the game page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("/agecheck/"), ExpectedConditions.urlContains("/app/")));
        String currentlUrl = driver.getCurrentUrl();

        if(currentlUrl.contains("/agecheck/")){
            System.out.println("Steam redirection to age check");
            return new AgeCheckPage();
        } else{
            System.out.println("No redirection to age check required");
            return new SteamGamePage();
        }


    }

    //constructor
    public SteamSearchPage(String gameName){
        this.gameName = gameName;

    }

    //getter
    public String getName(){
        return gameName;
    }



}
