package com.pixelkobold;

import com.badlogic.gdx.Game;
import com.pixelkobold.screens.LoadingScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class DungeonCrawler extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen());
    }
}
