package com.pixelkobold.world;

import com.badlogic.gdx.utils.ArrayMap;
import lombok.Data;


@Data
public class Floor {

    private ArrayMap<String, Room> rooms = new ArrayMap<>();

    public Room getRoomByName(String name) {
        return rooms.get(name);
    }

    public void addRoom(Room room) {
        rooms.put(room.getMapName(), room);
    }

}
