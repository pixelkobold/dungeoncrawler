package com.pixelkobold.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Screens {

	public static Screen OPTIONS_SCREEN = new OptionScreen();
	public static Screen PLAY_SCREEN = new PlayingScreen();
	public static Screen MAIN_MENU_SCREEN = new MainMenuScreen();
	public static Screen LOADING_SCREEN = new LoadingScreen();


	public static void setScreen(Screen s){
		((Game) Gdx.app.getApplicationListener()).setScreen(s);
	}
}
