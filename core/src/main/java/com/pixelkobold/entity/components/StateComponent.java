package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.Pool;
import com.pixelkobold.entity.states.EntityState;
import lombok.Data;

@Data
public class StateComponent implements Component, Pool.Poolable {
    private float stateTime = 0;
    private EntityState state = EntityState.IDLE;

    public void updateTime(float deltaTime) {
        this.stateTime += deltaTime;
    }

    @Override
    public void reset() {
        state = EntityState.IDLE;
        stateTime = 0;
    }

    public static final ComponentMapper<StateComponent> mapper = ComponentMapper.getFor(StateComponent.class);
}
