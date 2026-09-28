package com.Objects;

public class GameItem {

    private String gameName;
    private String gameLink;
    private String gameRegularPrice;
    private String gameFinalPrice;
    private boolean isDiscounted;


    public GameItem(String gameName, String gameLink, String gameRegularPrice, String gameFinalPrice, boolean isDiscounted){
        this.gameName = gameName;
        this.gameLink = gameLink;
        this.gameRegularPrice = gameRegularPrice;
        this.gameFinalPrice = gameFinalPrice;
        this.isDiscounted = isDiscounted;

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

    public String getDiscounted(){
        if(isDiscounted){
            return "Discounted";
        } else{
            return "Not discounted";
        }
    }


    public String toCsvRow(){
        String sanitizedGameName = "\"" + gameName.replace("\"", "\"\"") + "\"";
        String sanitizedFinalGamePrice= "\"" + gameFinalPrice.replace("\"", "\"\"") + "\"";
        String sanitizedRegularGamePrice = "\"" + gameRegularPrice.replace("\"", "\"\"") + "\"";
        String hyperLink = "\"=HIPERVINCULO(\"" + gameLink + "\"; \"\"" + gameName + "\"\")\"";
        String sanitizedDiscounted = "Discounted";

        if(!isDiscounted){
            sanitizedDiscounted = "Not Discounted";
        }

        return String.join(",", sanitizedGameName, sanitizedRegularGamePrice, sanitizedFinalGamePrice, hyperLink, sanitizedDiscounted);
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
