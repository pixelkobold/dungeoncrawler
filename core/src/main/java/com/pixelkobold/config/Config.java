package com.pixelkobold.config;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;

public class Config {

	protected static final class IniProperty {
		private String prop;

		IniProperty(String s) {
			prop = s;
		}

		public int asInt() {
			return Integer.parseInt(prop);
		}

		public float asFloat() {
			return Float.parseFloat(prop);
		}

		public char asChar() {
			return prop.charAt(0);
		}

		public boolean asBool() {
			return Boolean.parseBoolean(prop);
		}
	}


	// some default values

	// [Display
	public static int screenHeight = 540;
	public static int screenWidth = 950;

	public static boolean fullscreen = false;

	// [Audio]
	public static int musicVolume = 100;
	public static int soundVolume = 100;

	// [Controlls]
	public static int keyUp = Keys.W;
	public static int keyDown = Keys.S;
	public static int keyLeft = Keys.A;
	public static int keyRight = Keys.D;
	public static int keyInterract = Keys.E;
	public static int keyInventory = Keys.I;

	// public static int keyZoom = Input.Buttons.BACK;

	private static File configFile = new File(Gdx.files.getLocalStoragePath() + "config.ini");


	public static boolean debug = false;


	public static void init() throws Exception {
		// TODO: Manage settings
		// System.out.println(ini.get("Display", "width"));
	}
}
