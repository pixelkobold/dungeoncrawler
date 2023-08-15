package com.pixelkobold.world;

import com.badlogic.gdx.utils.ArrayMap;
import lombok.Data;


@Data
public class Floor {

    private ArrayMap<String, Room> rooms;

    public Room getRoomByName(String name) {
        return rooms.get(name);
    }

    public void addRoom(Room room) {
        // TODO: check if room exists
        rooms.put(room.getMapName(), room);
    }

}
