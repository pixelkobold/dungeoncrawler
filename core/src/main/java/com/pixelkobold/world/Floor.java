package com.pixelkobold.world;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Floor {

    private ArrayList<Room> rooms; // TODO: ArrayMap from libgdx instead of arraylist
    // or Array from libgdx zamiast arraylisty

    public Room getRoomByName(String name) {
        // TODO
        return null;
    }

}
