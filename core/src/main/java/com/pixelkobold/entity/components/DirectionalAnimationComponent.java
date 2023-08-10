package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DirectionalAnimationComponent implements Component {
    private TextureRegion[][] frames;
    private Animation<TextureRegion> leftAnim, rightAnim;
}
