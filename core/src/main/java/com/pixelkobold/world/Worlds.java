package com.pixelkobold.world;


import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;


public class Worlds {

    // TODO: add this property to each World
    public enum WorldType {
        ENTRANCE, BOSS, REGULAR
    }

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
//			WorldData worldData = json.fromJson(WorldData.class, file.readString());
//			Log.log(LogLevel.DEBUG, "Adding world: " + file.nameWithoutExtension() + "\n" + worldData.toString());
//			worldMap.put(file.nameWithoutExtension(), worldData.toWorld());
        }


    }

    public static World getRandomWorld(WorldType type) {

        // TODO: create functionality for finding a random world of a certain type

        return null;
    }

    public static Floor generateFloor(Integer roomsAmount) {

        // TODO: read all files, from folder, no need to use World class

        Floor floor = new Floor();

        World entrance = getRandomWorld(WorldType.ENTRANCE);
        World boss = getRandomWorld(WorldType.BOSS);
        Array<World> regulars = new Array<>();

        for (int i = 0; i < roomsAmount; i++) {
            regulars.add(getRandomWorld(WorldType.REGULAR));
        }


        // TODO: read properties of maps and add side rooms

        Room entranceRoom = new Room(entrance.mapName, regulars.items[0].mapName, null, null, null);
        floor.addRoom(entranceRoom);

        for (int i = 0; i < roomsAmount; i++) {
            if (i == 0) {
                Room room = new Room(regulars.items[i].mapName, entrance.mapName, null, null, null);
                floor.addRoom(room);
            } else if (i == roomsAmount - 1) {
                Room room = new Room(regulars.items[i].mapName, null, boss.mapName, null, null);
                floor.addRoom(room);
            } else {
                Room room = new Room(regulars.items[i].mapName, regulars.items[i + 1].mapName, regulars.items[i - 1].mapName, null, null);
                floor.addRoom(room);
            }
        }

        Room bossRoom = new Room(boss.mapName, null, regulars.items[roomsAmount - 1].mapName, null, null);
        floor.addRoom(bossRoom);

        return floor;
    }
}
