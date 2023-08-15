package com.pixelkobold.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pixelkobold.entity.components.*;

public class AnimationSystem extends IteratingSystem {
    private final SpriteBatch batch;

    public AnimationSystem(SpriteBatch batch) {
        super(Family.all(PositionComponent.class, StateComponent.class, AnimationComponent.class, DirectionComponent.class).get());
        this.batch = batch;
    }

    private final ComponentMapper<PositionComponent> positionMapper = PositionComponent.mapper;
    private final ComponentMapper<StateComponent> stateMapper = StateComponent.mapper;
    private final ComponentMapper<AnimationComponent> animationMapper = AnimationComponent.mapper;
    private final ComponentMapper<DirectionComponent> directionMapper = DirectionComponent.mapper;

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var position = positionMapper.get(entity);
        var state = stateMapper.get(entity);
        var animations = animationMapper.get(entity);
        var direction = directionMapper.get(entity);

        state.updateTime(deltaTime);
        var animation = switch (state.getState()) {
            case IDLE -> animations.getIdle();
            case WALK -> animations.getMoving();
            case RUN -> animations.getRunning();
        };

        var frame = animation.getKeyFrame(state.getStateTime());
        flip(direction.direction, frame);

        batch.draw(frame, position.getPosition().x, position.getPosition().y);
    }

    private void flip(EntityDirection direction, TextureRegion frame) {
        if (direction == EntityDirection.LEFT && !frame.isFlipX()) {
            frame.flip(true, false);
        }
        if (direction == EntityDirection.RIGHT && frame.isFlipX()) {
            frame.flip(true, false);
        }
    }
}
