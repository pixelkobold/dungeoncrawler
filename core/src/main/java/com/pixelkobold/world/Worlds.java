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


    private static Array<String> getMapsArray(String path) {
        FileHandle dir = Gdx.files.internal(path);
        Object[] files = Arrays.stream(dir.readString().split("\n")).filter(file -> file.endsWith("tmx")).toArray();
        Array<String> maps = new Array<>();

        for (Object file : files) {
            maps.add(file.toString());
        }

        return maps;
    }

    public static Floor generateFloor(Integer roomsAmount) {
        Floor floor = new Floor();

        // TODO: add variable to choose maps from different levels instead of only 'forest'
        Array<String> maps = getMapsArray("maps/petri/forest/regular");
        Array<String> entranceMaps = getMapsArray("maps/petri/forest/entrance");
        Array<String> bossMaps = getMapsArray("maps/petri/forest/boss");

        String entrance = entranceMaps.random();
        String boss = bossMaps.random();
        Array<String> regulars = new Array<>();

        for (int i = 0; i < roomsAmount; i++) {
            String randomMap = maps.random();
            regulars.add(randomMap);
            maps.removeValue(randomMap, false); // to avoid repeating maps, might delete later
            // TODO: will cause an index out of bounds if we choose a room number bigger than the amount of maps we currently have
        }

        // TODO: read properties of maps and add side rooms

        Room entranceRoom = new Room(entrance, regulars.get(0), null, null, null);
        floor.addRoom(entranceRoom);

        for (int i = 0; i < roomsAmount; i++) {
            if (i == 0) {
                Room room = new Room(regulars.get(i), regulars.get(i + 1), entrance, null, null);
                floor.addRoom(room);
            } else if (i == roomsAmount - 1) {
                Room room = new Room(regulars.get(i), boss, regulars.get(i - 1), null, null);
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
