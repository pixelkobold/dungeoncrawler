package com.pixelkobold.objects;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.renderers.DebugShapeRenderer;
import lombok.Getter;

@Getter
public abstract class GameObject {
public static class GameObjectProps{
	public String action, name, sprite;
}

	/**
	 * Used to get Sprite from AssetManager.
	 */
	private String name;
	/**
	 * The position to draw it. I wish SpriteBatch accepted Vectors
	 */
	protected Vector2 pos;

	/**
	 * It will get value from AssetManager in init(). It is just easier to call
	 * batch.draw(sprite, x, y) than
	 * batch.draw(AssetManager.get("asset").asSprite() every time
	 */
	public Sprite sprite;

	public Rectangle box;
	public static Vector2 mousePosition;

	public static Map<String, GameObjectProps> propsMap;

	public GameObject(String name, Vector2 pos) {
		setName(name);
		this.pos = pos;
	}

	public void init() {
		sprite = AssetManager.get(getName()).asSprite();
		box = new Rectangle(pos.x, pos.y, 18, 30);
		// inventory = new Inventory();
	}

	public void render(SpriteBatch batch, float dt) {
		batch.draw(sprite, pos.x, pos.y);
		DebugShapeRenderer.drawRectangle(box);
	}

	public GameObject setName(String name) {
		this.name = name;
		return this;
	}

	public void setMousePosition(Vector2 mousePosition2){
		mousePosition = mousePosition2;
	}

	public void dispose() {
		box = null;
		mousePosition = null;
		setName(null);
		pos = null;
		sprite = null;
	}

	public Vector2 center(){
		return this.box.getCenter(Vector2.X);
	}

}
