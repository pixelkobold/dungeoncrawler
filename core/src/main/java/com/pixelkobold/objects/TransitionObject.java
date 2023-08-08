package com.pixelkobold.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.screens.PlayingScreen;
import com.pixelkobold.world.Worlds;

public class TransitionObject extends StaticObject {

    public MapProperties props;
    public Vector2 targetPos;

    public TransitionObject(MapProperties props) {
        super("transition", new Vector2(props.get("x", Float.class), props.get("y", Float.class)));
        this.props = props;
        this.targetPos = new Vector2(props.get("posX", Float.class),
            props.get("mapheight", Integer.class) * 32 - props.get("posY", Float.class));
    }

    @Override
    public void init() {
        // super.init();
        // float tilesY = this.pos.y;
        // float posY = tilesY * 32f;

        // this.pos.y = posY;
        // pos.x *= 32f;

        this.sprite = AssetManager.get("emptyPixel").asSprite();

        box = new Rectangle(pos.x, pos.y, props.get("width", Float.class), props.get("height", Float.class));

        if (!props.containsKey("posX"))
            props.put("posX", "0");
        if (!props.containsKey("posY"))
            props.put("posY", "0");
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        if (box.contains(GameObjectManager.getPlayerObject().box)
            || box.overlaps(GameObjectManager.getPlayerObject().box)) {
            // GameObjectManager.getPlayerObject().setPosition(targetPos);
            // System.out.println(mapProperties.get("posY")+" :
            // "+GameObjectManager.getPlayerObject().pos.y);
            PlayingScreen.setWorld(Worlds.get(props.get("map", String.class)).setPos(targetPos));
        }
//		DebugShapeRenderer.drawRectangle(box);
    }

    @Override
    public String toString() {
        return "TransitionObject [pos=" + pos + ", box=" + box + "]";
    }
}
