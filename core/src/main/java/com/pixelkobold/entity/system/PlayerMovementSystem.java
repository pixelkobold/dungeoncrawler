package com.pixelkobold.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelkobold.config.Config;
import com.pixelkobold.entity.components.*;
import com.pixelkobold.entity.states.EntityState;

public class PlayerMovementSystem extends IteratingSystem {
    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class, PositionComponent.class, MovementComponent.class, DirectionComponent.class, BoundingBoxComponent.class, StateComponent.class).get());
    }

    private final ComponentMapper<PositionComponent> positionMapper = PositionComponent.mapper;
    private final ComponentMapper<MovementComponent> movementMapper = MovementComponent.mapper;
    private final ComponentMapper<DirectionComponent> directionMapper = DirectionComponent.mapper;
    private final ComponentMapper<BoundingBoxComponent> boundingBoxMapper = BoundingBoxComponent.mapper;
    private final ComponentMapper<StateComponent> stateMapper = StateComponent.mapper;

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var position = positionMapper.get(entity).getPosition();
        var direction = directionMapper.get(entity);
        var movement = movementMapper.get(entity);
        var box = boundingBoxMapper.get(entity).getBox();
        var state = stateMapper.get(entity);

        Vector2 tmp = new Vector2();
        if (Gdx.input.isKeyPressed(Config.keyLeft)) {
            tmp.x = -1;
            direction.direction = EntityDirection.LEFT;
            movement.setMovingX(true);
        } else if (Gdx.input.isKeyPressed(Config.keyRight)) {
            tmp.x = 1;
            direction.direction = EntityDirection.RIGHT;
            movement.setMovingX(true);
        } else {
            tmp.x = 0;
            movement.setMovingX(false);
        }

        if (Gdx.input.isKeyPressed(Config.keyUp)) {
            tmp.y = 1;
            movement.setMovingY(true);
        } else if (Gdx.input.isKeyPressed(Config.keyDown)) {
            tmp.y = -1;
            movement.setMovingY(true);
        } else {
            tmp.y = 0;
            movement.setMovingY(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            tmp.scl(5);
            movement.setSprinting(true);
        } else {
            movement.setSprinting(false);
        }

        Rectangle tmpX = new Rectangle(box);
        tmpX.x += tmp.x;

        Rectangle tmpY = new Rectangle(box);
        tmpY.y += tmp.y;


        getEngine().getEntitiesFor(Family.all(BoundingBoxComponent.class).exclude(PlayerComponent.class).get()).forEach(other -> {
            var otherBox = boundingBoxMapper.get(other).getBox();
            if (tmpX.overlaps(otherBox) || tmpX.contains(otherBox)) {
                tmp.x = 0;
            }
            if (tmpY.overlaps(otherBox) || tmpY.contains(otherBox)) {
                tmp.y = 0;
            }
        });

//        if (movement.isMovingX()) {
//            facingBox = tmpX;
//            facingBox.x += (tmp.x < 0 ? -10 : 10);
//        } else if (movement.isMovingY()) {
//            facingBox = tmpY;
//            facingBox.y += (tmp.y < 0 ? -10 : 10);
//        }

        var mov = tmp.nor().scl(movement.getSpeed()).scl(movement.isSprinting() ? 1.5f : 1).scl(deltaTime);
        if (mov.len2() <= .01f) {
            state.setState(EntityState.IDLE);
            return;
        }
        if (movement.isSprinting()) {
            state.setState(EntityState.RUN);
        } else {
            state.setState(EntityState.WALK);
        }
        position.add(mov);
    }

}
