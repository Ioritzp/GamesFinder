package SteamActions;

import Base.BaseTest;
import com.Objects.GameItem;
import com.steamPages.AgeCheckPage;
import com.steamPages.SteamGamePage;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static Utilities.Utility.fromCsvToArray;
import static Utilities.Utility.fromTxtToArray;

public class SearchForGames extends BaseTest {

    private static List<GameItem> gameList = new ArrayList<GameItem>();
    private static String csvfilePath = "C:/Users/iorit/Desktop/gameList.csv";
    @Test
    public void SelectGame() {

        //TODO: exceptions must be handled - if a game doesn't exist or similar, it should jump to the next instead of crashing.

        //TODO: expand the program to check if the csv exists. If it does, it must upgrade it. First, the games from the new list will be rechecked and if there are changes, update the link/price/discount. next, if there are new games, they must be added
        String path = (System.getProperty("user.dir"))+ "/resources/gameSources/gameList.txt";
        boolean csvExists = false;
        Path csvPath = Paths.get(path);
        if(Files.exists(csvPath) && Files.isRegularFile(csvPath)){
            csvExists = true;
        }
        if(csvExists){
            List<String> currentGames = fromCsvToArray(path);
        }
        String[] gamesToSearch = fromTxtToArray(path);
        for(String gameName : gamesToSearch) {
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
            }

            System.out.println("--------------------------------------------------------------");
        }

        exportToCsv(gameList, csvfilePath);


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
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))){
            writer.write("Game Name, Game Regular Price, Final Price, Link");
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
