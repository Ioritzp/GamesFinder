package com.steamPages;

import com.Objects.GameItem;

public class NoGamePage {

    public GameItem returnGame(String gamename){
        return new GameItem(gamename + " is not in library", "data unavailable", "data unavailble", "data unavailable");
    }
}
