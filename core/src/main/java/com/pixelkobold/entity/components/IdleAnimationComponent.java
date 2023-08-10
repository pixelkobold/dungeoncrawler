package com.pixelkobold.entity.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IdleAnimationComponent extends DirectionalAnimationComponent {
    public IdleAnimationComponent() {
        super(frames, leftAnim, rightAnim);
    }
}
