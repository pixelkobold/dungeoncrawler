package com.pixelkobold.world;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;

public class Worlds {

	public static HashMap<String, World> worldMap = new HashMap<>();

	public static World get(String worldName) {
		if (worldMap.get(worldName) != null) {
			return worldMap.get(worldName);
		}
		Log.log(LogLevel.CRITICAL, "No map with name " + worldName + "exists! Aborting");
		Gdx.app.exit();
		return null;
	}

	public static void loadAll() {
		Json json = new Json();
		FileHandle dir = Gdx.files.internal("worlds/");
		for (FileHandle file : dir.list("json")) {
			WorldData worldData = json.fromJson(WorldData.class, file.readString());
			Log.log(LogLevel.DEBUG, "Adding world: " + file.nameWithoutExtension() + "\n" + worldData.toString());
			worldMap.put(file.nameWithoutExtension(), worldData.toWorld());
		}

	}
}
