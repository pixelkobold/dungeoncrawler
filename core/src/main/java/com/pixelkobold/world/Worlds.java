package com.pixelkobold.world;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

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
        Floor floor = new Floor();

        // TODO: fix it
        // TODO: merge with new maps structure
        Object[] files = Arrays.stream(Gdx.files.internal("maps/petri/").readString().split("\n")).filter(file -> file.endsWith("tmx")).toArray();
        Array<String> maps = new Array<>();

        for (Object file : files) {
            maps.add(file.toString());
        }

        // TODO: choose maps randomly instead of hardcoding indexes
        String entrance = maps.get(0);
        String boss = maps.get(0);
        Array<String> regulars = new Array<>();

        for (int i = 0; i < roomsAmount; i++) {
            regulars.add(maps.get(i));
        }

        // TODO: read properties of maps and add side rooms

        Room entranceRoom = new Room(entrance, regulars.get(0), null, null, null);
        floor.addRoom(entranceRoom);

        for (int i = 0; i < roomsAmount; i++) {
            if (i == 0) {
                Room room = new Room(regulars.get(i), null, entrance, null, null);
                floor.addRoom(room);
            } else if (i == roomsAmount - 1) {
                Room room = new Room(regulars.get(i), boss, null, null, null);
                floor.addRoom(room);
            } else {
                Room room = new Room(regulars.get(i), regulars.get(i + 1), regulars.get(i - 1), null, null);
                floor.addRoom(room);
            }
        }

        Room bossRoom = new Room(boss, null, regulars.get(roomsAmount - 1), null, null);
        floor.addRoom(bossRoom);

        Log.log(LogLevel.DEBUG, floor.toString());

        return floor;
    }
}
