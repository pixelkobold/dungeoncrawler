package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
public class AnimationComponent implements Component, Pool.Poolable {
    @Setter(AccessLevel.PUBLIC)
    @Accessors(chain = true)
    private TextureRegion[][] frames;
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> moving;
    private Animation<TextureRegion> running;

    @SafeVarargs
    public final AnimationComponent setIdle(Pair<Integer, Integer>... indices) {
        idle = new Animation<>(1f, processIndices(indices));
        idle.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        return this;
    }

    @SafeVarargs
    public final AnimationComponent setMoving(Pair<Integer, Integer>... indices) {
        moving = new Animation<>(.1f, processIndices(indices));
        moving.setPlayMode(Animation.PlayMode.LOOP);
        return this;
    }

    @SafeVarargs
    public final AnimationComponent setRunning(Pair<Integer, Integer>... indices) {
        running = new Animation<>(.1f, processIndices(indices));
        running.setPlayMode(Animation.PlayMode.LOOP);
        return this;
    }

    private TextureRegion[] processIndices(Pair<Integer, Integer>[] indices) {
        return Arrays.stream(indices).map(p -> frames[p.getFirst()][p.getSecond()]).toList().toArray(new TextureRegion[]{});
    }

    public static final ComponentMapper<AnimationComponent> mapper = ComponentMapper.getFor(AnimationComponent.class);

    @Override
    public void reset() {
        frames = null;
        idle = null;
        moving = null;
        running = null;
    }
}
