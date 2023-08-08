package com.pixelkobold.objects;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.renderers.DebugShapeRenderer;
import com.pixelkobold.shapes.Cone;
import lombok.Getter;

@Getter
public class LivingObject extends GameObject {
	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	protected HashMap<String, Object> stats = new HashMap<>();

	protected float speed = 50;
	protected Vector2 dest;
	public Vector2 dir;
	protected Vector2 vel;
	protected Vector2 mov;
	public Direction facing = Direction.UP;

	protected ITempAI action;

	protected static NinePatchDrawable hpBar, blackBar;

	public Cone cone = new Cone(this);

	protected TextureRegion[][] frames;
	protected Animation<TextureRegion> upAnim, downAnim, leftAnim, rightAnim;
	protected TextureRegion up, down, left, right;
	protected float stateTime = 0;

	protected boolean isMovingX, isMovingY = false;

	public LivingObject(String name, Vector2 pos) {
		super(name, pos);
		dest = pos;
		dir = new Vector2();
		vel = new Vector2();
		mov = new Vector2();
	}

	public LivingObject setAction(ITempAI action) {
		this.action = action;
		return this;
	}

	@Override
	public void init() {
		super.init();

		stats.put("hp", 100f);
		stats.put("maxhp", 100f);
		stats.put("invincible", false);
		if (hpBar == null) {
			hpBar = new NinePatchDrawable(new NinePatch(AssetManager.get("hpBar").asSprite(), 0, 0, 0, 0));
			blackBar = new NinePatchDrawable(new NinePatch(AssetManager.get("blackBar").asSprite(), 0, 0, 0, 0));
		}
		// cone = new Polygon();

		frames = sprite.split(32, 64);

		upAnim = new Animation<>(0.1f, frames[1][1], frames[1][2], frames[1][3]);
		downAnim = new Animation<>(0.1f, frames[0][1], frames[0][2], frames[0][3]);
		leftAnim = new Animation<>(0.1f, frames[0][5], frames[0][6], frames[0][7]);
		rightAnim = new Animation<>(0.1f, frames[1][5], frames[1][6], frames[1][7]);

		upAnim.setPlayMode(PlayMode.LOOP);
		downAnim.setPlayMode(PlayMode.LOOP);
		leftAnim.setPlayMode(PlayMode.LOOP);
		rightAnim.setPlayMode(PlayMode.LOOP);

		up = frames[1][1];
		down = frames[0][1];
		left = frames[0][5];
		right = frames[1][5];

	}

	public void drawAnimations(SpriteBatch batch, float dt) {
		if (isMovingX || isMovingY) {
			stateTime += dt;
			switch (facing) {
			case DOWN:
				batch.draw(downAnim.getKeyFrame(stateTime), pos.x, pos.y);
				break;
			case LEFT:
				batch.draw(leftAnim.getKeyFrame(stateTime), pos.x, pos.y);
				break;
			case RIGHT:
				batch.draw(rightAnim.getKeyFrame(stateTime), pos.x, pos.y);
				break;
			case UP:
				batch.draw(upAnim.getKeyFrame(stateTime), pos.x, pos.y);
				break;
			default:
				break;

			}
		} else {
			switch (facing) {
			case DOWN:
				batch.draw(down, pos.x, pos.y);
				break;
			case LEFT:
				batch.draw(left, pos.x, pos.y);
				break;
			case RIGHT:
				batch.draw(right, pos.x, pos.y);
				break;
			case UP:
				batch.draw(up, pos.x, pos.y);
				break;
			default:
				break;

			}
		}

	}

	@Override
	public void render(SpriteBatch batch, float dt) {
		if (action != null)
			action.action();
		move(dt);

		box.setPosition(pos);
		box.y += 7;
		box.x += 7;

		cone.updateCone();
		DebugShapeRenderer.drawShape(cone);
		DebugShapeRenderer.drawRectangle(box);
		drawAnimations(batch, dt);
	}

	public void move(float dt) {
		if (dest.dst(pos) <= 1)
			return;
		dir.set(dest).sub(pos).nor();
		vel = new Vector2(dir).scl(speed);
		mov.set(vel).scl(dt);
		pos.add(mov);
	}

}
