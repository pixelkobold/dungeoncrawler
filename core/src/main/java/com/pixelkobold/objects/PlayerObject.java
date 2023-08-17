package com.pixelkobold.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.pixelkobold.config.Config;
import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;
import com.pixelkobold.renderers.DebugShapeRenderer;
import org.checkerframework.checker.units.qual.A;

public class PlayerObject extends LivingObject {

    private Animation<TextureRegion> idle, move, attack, die;

	private Rectangle facingBox = new Rectangle();

	private GameObjectManager manager;

    private Direction lastMove = Direction.RIGHT;
    private boolean isAttacking = false;

    public PlayerObject(Vector2 pos) {
		super("player", pos);
	}

	public GameObject setManager(GameObjectManager manager) {
		this.manager = manager;
		return this;
	}

	@Override
	public void init() {
		super.init();
		// TODO: Read from save
		stats.put("maxhp", 100f);
		stats.put("maxmp", 100f);

		stats.put("hp", 75f);
		stats.put("mp", 75f);

	}

    @Override
    protected void setupAnimations() {
        frames = sprite.split(32, 32);

        idle = new Animation<>(1, frames[0][0], frames[0][1], frames[1][0], frames[1][1]);
        move = new Animation<>(.1f, frames[3][0], frames[3][1], frames[3][2], frames[3][3]);

        idle.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        move.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
	public void render(SpriteBatch batch, float dt) {
		box.setPosition(pos);
		box.y += 7;
		box.x += 7;
        processInput(Gdx.input);
		drawAnimations(batch, dt);
		move(dt);
		cone.updateCone();
		DebugShapeRenderer.drawShape(cone);
		DebugShapeRenderer.drawRectangle(box);
	}

    @Override
    public void drawAnimations(SpriteBatch batch, float dt) {
        this.stateTime+=dt;

        Animation<TextureRegion> animation;
        if(isMovingX || isMovingY){
            animation = move;
        } else if (isAttacking) {
            animation = attack;
        } else {
            animation = idle;
        }

        var frame = animation.getKeyFrame(stateTime);
        if(lastMove == Direction.LEFT && !frame.isFlipX()){
            frame.flip(true, false);
        }
        if(lastMove == Direction.RIGHT && frame.isFlipX()){
            frame.flip(true, false);
        }

        batch.draw(frame,pos.x, pos.y);
    }

    public void processInput(Input in) {
		Vector2 tmp = new Vector2();
		if (in.isKeyPressed(Config.keyLeft)) {
			tmp.x = -1;
			facing = Direction.LEFT;
			isMovingX = true;
			dir.setAngleDeg(0);
            lastMove = Direction.LEFT;
		} else if (in.isKeyPressed(Config.keyRight)) {
			tmp.x = 1;
			facing = Direction.RIGHT;
			isMovingX = true;
			dir.setAngleDeg(180);
            lastMove = Direction.RIGHT;
		} else {
			tmp.x = 0;
			isMovingX = false;
		}

		if (in.isKeyPressed(Config.keyUp)) {
			tmp.y = 1;
			facing = Direction.UP;
			isMovingY = true;
			dir.setAngleDeg(90);
		} else if (in.isKeyPressed(Config.keyDown)) {
			tmp.y = -1;
			facing = Direction.DOWN;
			isMovingY = true;
			dir.setAngleDeg(270);
		} else {
			tmp.y = 0;
			isMovingY = false;
		}

		if (in.isKeyPressed(Keys.SHIFT_LEFT))
			tmp.scl(2);

        moveBy(tmp);

		if (in.isKeyJustPressed(Keys.B)) {
            System.out.println("Player: " + pos.toString());
        }

	}

	public void moveBy(Vector2 dest) {
		if (manager == null) {
			Log.log(LogLevel.CRITICAL, "PlayerObject doesn't know about other Objects!");
			Gdx.app.exit();
		}

		Rectangle tmpX = new Rectangle(box);
		tmpX.x += dest.x;

		Rectangle tmpY = new Rectangle(box);
		tmpY.y += dest.y;

		for (GameObject o : manager.get()) {
			if (o instanceof PlayerObject || o instanceof TransitionObject)
				continue;
			if (tmpX.overlaps(o.box) || tmpX.contains(o.box)) {
				dest.x = 0;
			}
			if (tmpY.overlaps(o.box) || tmpY.contains(o.box)) {
				dest.y = 0;
			}
		}

		if (isMovingX) {
			facingBox = tmpX;
			facingBox.x += (dest.x < 0 ? -10 : 10);
		} else if (isMovingY) {
			facingBox = tmpY;
			facingBox.y += (dest.y < 0 ? -10 : 10);
		}

        this.dest = this.pos.add(dest);
	}

	public Vector3 getPosition3() {
		Vector2 tmp1 = pos.cpy();
		Vector3 tmp2 = new Vector3();

		tmp2.x = tmp1.x + 16;
		tmp2.y = tmp1.y + 34;

		return tmp2;
	}

	public Direction getDirection() {
		return this.facing;
	}

}
