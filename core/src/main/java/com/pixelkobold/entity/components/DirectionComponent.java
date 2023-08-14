package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class DirectionComponent implements Component {
    public EntityDirection direction = EntityDirection.RIGHT;
    public static final ComponentMapper<DirectionComponent> mapper = ComponentMapper.getFor(DirectionComponent.class);

}
