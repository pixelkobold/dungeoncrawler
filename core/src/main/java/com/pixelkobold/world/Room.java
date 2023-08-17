package com.pixelkobold.world;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@Data
@RequiredArgsConstructor
public class Room {
    private final String mapName;

    @Nullable
    private final String nextRoom;
    @Nullable
    private final String previousRoom;
    @Nullable
    private final String leftSideRoom;
    @Nullable
    private final String rightSideRoom;

}
