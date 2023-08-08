/**
 *
 */
package com.pixelkobold.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.renderers.DebugShapeRenderer;

/**
 * @author killermenpl
 *
 */
public class MapCollisionObject extends StaticObject {

	public MapCollisionObject(Vector2 pos) {
		super("collision", pos);
	}

	public void init() {
		//super.init();
		int tilesY = (int) this.pos.y;
		int posY = tilesY * 32;

		this.pos.y = posY;
		pos.x *= 32;

		this.sprite = AssetManager.get("emptyPixel").asSprite();

		if(box==null)
		box = new Rectangle(pos.x, pos.y, 32, 32);
	}

	public MapCollisionObject setBox(float width, float height){
		this.box = new Rectangle(pos.x, pos.y+16f, width, height);
		return this;
	}

	@Override
	public void render(SpriteBatch batch, float dt) {
		DebugShapeRenderer.drawRectangle(box);
//		batch.draw(AssetManager.get("TEMP_STATIC_OBJECT").asSprite(), box.x, box.y, box.width, box.height);
	}
}
