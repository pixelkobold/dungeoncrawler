package com.pixelkobold.util;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class GameUtils {

	public static final String GAME_DIR = Gdx.files.getLocalStoragePath();

	public static Random RANDOM = new Random(System.currentTimeMillis());


}
