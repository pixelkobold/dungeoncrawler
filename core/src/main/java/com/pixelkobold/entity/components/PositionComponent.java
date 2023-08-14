package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class PositionComponent implements Component {
    @Getter
    @Setter
    @Accessors(chain = true)
    private Vector2 position = new Vector2();
    public static final ComponentMapper<PositionComponent> mapper = ComponentMapper.getFor(PositionComponent.class);


}
