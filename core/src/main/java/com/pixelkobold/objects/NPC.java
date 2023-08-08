package com.pixelkobold.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.pixelkobold.renderers.DebugShapeRenderer;

//TODO: facing dir, images based on that;
public class NPC extends LivingObject implements IInteractable {

	// private Direction dir = Direction.UP;

//	protected HashMap<String, DialogWindow> dialogs = new HashMap<>();
//	protected Array<String> dialogNames;
//	protected String currentDialog = "";

	private TextureRegion up, down, left, right;

	public NPC(String name, Vector2 pos) {
		super(name, pos);
	}

	@Override
	public void init() {
		super.init();

		//currentDialog = dialogNames.first();
		//for (String dialog : dialogNames) {
		//	dialogs.put(dialog, new DialogWindow(dialog, this));
		//}

		TextureRegion[][] frames = this.sprite.split(32, 64);

		up = frames[1][1];
		down = frames[0][1];
		left = frames[0][5];
		right = frames[1][5];

		super.init();
	}

	@Override
	public void interact() {
		switch (GameObjectManager.getPlayerObject().getDirection()) {
		case DOWN:
			this.facing = Direction.UP;
			break;
		case LEFT:
			this.facing = Direction.RIGHT;
			break;
		case RIGHT:
			this.facing = Direction.LEFT;
			break;
		case UP:
			this.facing = Direction.DOWN;
			break;
		default:
			break;

		}
		//dialogs.get(currentDialog).open();
	}

	public void render(SpriteBatch batch, float dt) {
		super.render(batch, dt);
		switch(facing){
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
		DebugShapeRenderer.drawRectangle(box);

	}

//	public void renderDialogs(SpriteBatch batch, float dt, BitmapFont font, GlyphLayout layout) {
//		dialogs.get(currentDialog).render(batch, dt, font, layout);
//	}

//	 */
//	public void setDialogNames(String[] strings) {
//		this.dialogNames = new Array<>(strings);
//	}

//	public HashMap<String, DialogWindow> dialogs() {
//		return dialogs;
//	}
}
