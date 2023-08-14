package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;

@Data
public class MovementComponent implements Component {
    private float speed = 50;
    private Vector2 movement = new Vector2();
    private boolean isMovingX = false;
    private boolean isMovingY = false;
    private boolean isSprinting = false;

    public static final ComponentMapper<MovementComponent> mapper = ComponentMapper.getFor(MovementComponent.class);
}
