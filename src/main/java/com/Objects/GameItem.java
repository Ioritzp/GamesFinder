package com.Objects;

public class GameItem {

    private String gameName;
    private String gameLink;
    private String gameRegularPrice;
    private String gameFinalPrice;


    public GameItem(String gameName, String gameLink, String gameRegularPrice, String gameFinalPrice){
        this.gameName = gameName;
        this.gameLink = gameLink;
        this.gameRegularPrice = gameRegularPrice;
        this.gameFinalPrice = gameFinalPrice;

    }

    public String getGameName() {
        return gameName;
    }

    public String getGameLink() {
        return gameLink;
    }

    public String getGameRegularPrice() {
        return gameRegularPrice;
    }

    public String getGameFinalPrice() {
        return gameFinalPrice;
    }


    public String toCsvRow(){
        String sanitizedGameName = "\"" + gameName.replace("\"", "\"\"") + "\"";
        String sanitizedFinalGamePrice= "\"" + gameFinalPrice.replace("\"", "\"\"") + "\"";
        String sanitizedRegularGamePrice = "\"" + gameRegularPrice.replace("\"", "\"\"") + "\"";
        String hyperLink = "\"=HYPERLINK(\"" + gameLink + "\"";

        return String.join(",", sanitizedGameName, sanitizedRegularGamePrice, sanitizedFinalGamePrice, hyperLink);
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "gameName='" + gameName + '\'' +
                ", gameLink='" + gameLink + '\'' +
                ", gameRegularPrize='" + gameRegularPrice + '\'' +
                ", gameFinalPrize='" + gameFinalPrice + '\'' +
                '}';
    }
}
