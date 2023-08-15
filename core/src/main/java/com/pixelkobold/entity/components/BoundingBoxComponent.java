package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BoundingBoxComponent implements Component, Pool.Poolable {
    private Rectangle box;

    @Override
    public void reset() {
        box = null;
    }

    public static final ComponentMapper<BoundingBoxComponent> mapper = ComponentMapper.getFor(BoundingBoxComponent.class);
}
