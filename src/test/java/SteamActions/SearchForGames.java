package SteamActions;

import Base.BaseTest;
import com.Objects.GameItem;
import com.base.BasePage;
import com.steamPages.AgeCheckPage;
import com.steamPages.NoGamePage;
import com.steamPages.SteamGamePage;
import com.steamPages.SteamHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static Utilities.Utility.fromTxtToArray;

public class SearchForGames{

    private List<GameItem> gameList = new ArrayList<GameItem>();
    private String csvfilePath;
    private SteamHomePage homePage;

    public SearchForGames(String csvfilePath){
        this.csvfilePath = csvfilePath;
        this.gameList = new ArrayList<>();
    }

    private void SetupDriver(){
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");

        //below are options to configure the program to act without opening a visual browser.
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Chrome/120.0.0.0");
        BasePage.driver = new ChromeDriver(options);
        BasePage.driver.get("https://store.steampowered.com/");

        this.homePage = new SteamHomePage(BasePage.driver);

        System.out.println("driver set");
    }

    public List<GameItem> SelectGame(String path, ProgressListener listener) {

        this.gameList.clear();
        try {
            SetupDriver();

            System.out.println("path: " + path);
            String[] gamesToSearch = fromTxtToArray(path);
            int total = gamesToSearch.length;
            for (int i = 0; i < total; i++) {
                String gameName = gamesToSearch[i];
                if(listener != null){
                    listener.onProgress(gameName,i + 1, total);
                }
                System.out.println("Looking for: " + gameName);
                var searchPage = homePage.searchGame(gameName);
                int spaceIndex = gameName.indexOf(' ');
                String urlKeyword = gameName.substring(0, spaceIndex);

                var destinationPage = searchPage.clickOnGame();
                if (destinationPage instanceof AgeCheckPage) {
                    var ageCheckPage = ((AgeCheckPage) destinationPage).verifyAge();
                    var gameInfo = ageCheckPage.getFinalPrice(urlKeyword);
                    processGameInfo(gameInfo);

                } else if (destinationPage instanceof SteamGamePage) {
                    var gameInfo = ((SteamGamePage) destinationPage).getFinalPrice(urlKeyword);
                    processGameInfo(gameInfo);
                } else if (destinationPage instanceof NoGamePage) {
                    var gameInfo = ((NoGamePage) destinationPage).returnGame(gameName);
                    processGameInfo(gameInfo);
                }

                System.out.println("--------------------------------------------------------------");
            }

            exportToCsv(gameList, csvfilePath);
        }finally{
            if(BasePage.driver != null){
                BasePage.driver.quit();
            }
        }
        return gameList;


    }

    private void processGameInfo(GameItem gameInfo){
        if(gameInfo.getGameRegularPrice().equals("Not discounted")){
            System.out.println(gameInfo.getGameName() + " is not discounted. Prize: " + gameInfo.getGameFinalPrice() + ". Link: " + gameInfo.getGameLink());
        } else {

            System.out.println("Final price for " + gameInfo.getGameName() + " is " + gameInfo.getGameFinalPrice() + " the original price is: " + gameInfo.getGameRegularPrice() + ". Link: " + gameInfo.getGameLink());
        }

        gameList.add(gameInfo);
    }

    private void exportToCsv(List<GameItem> gameList, String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8, false))){
            writer.write("Game Name, Game Regular Price, Final Price, Link, Discount");
            writer.newLine();

            for(GameItem game : gameList){
                writer.write(game.toCsvRow());
                writer.newLine();
            }

            System.out.println("Csv successfully created in: " + filePath);
        } catch (IOException ioE){
            System.err.println("Error writing csv" + ioE.getMessage());
        }
    }


}
