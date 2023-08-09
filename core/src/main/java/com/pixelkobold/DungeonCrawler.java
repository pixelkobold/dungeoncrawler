package com.pixelkobold;

import com.badlogic.gdx.Game;
import com.pixelkobold.screens.LoadingScreen;

public class DungeonCrawler extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen());
    }
}
