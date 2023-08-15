package com.pixelkobold.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Asset {

    public enum AssetType {
        SPRITE, MUSIC, SOUND, MAP, SKIN, ATLAS
    }

    private final Object object;

    public Asset(AssetType type, String path) {
        if (path.equals("emptyPixel")) {
            this.object = new Sprite(new Texture(new Pixmap(1, 1, Format.Alpha)));
            return;
        }
        object = switch (type) {
            case MUSIC -> Gdx.audio.newMusic(Gdx.files.internal(path));
            case SOUND -> Gdx.audio.newSound(Gdx.files.internal(path));
            case SPRITE -> new Sprite(new Texture(Gdx.files.internal(path)));
            case MAP -> new TmxMapLoader().load(path);
            case SKIN -> new Skin(Gdx.files.internal(path));
            case ATLAS -> new TextureAtlas(path);
        };
    }

    public Sprite asSprite() {
        return (Sprite) object;
    }

    public Sound asSound() {
        return (Sound) object;
    }

    public Music asMusic() {
        return (Music) object;
    }

    public TiledMap asMap() {
        return (TiledMap) object;
    }

    public Skin asSkin() {
        return (Skin) object;
    }

    @SuppressWarnings("unchecked")
    public <T> T as(Class<T> clazz) {
        return (T) object;
    }
}
