package com.pixelkobold.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;

//@Deprecated
public class GameObjectManager implements Disposable {
	private boolean inited = false;

	private Array<GameObject> objects = new Array<GameObject>();

	private static PlayerObject player;
	private Vector2 mousePosition;

	public void addObject(GameObject object){
		if(object instanceof PlayerObject) player = (PlayerObject) object;
		objects.add(object);
	}

	public void initAll(){
		if(inited){
			System.out.println("Tryed to init GameObjectManager more than once");
			return;
		}
		if(objects.random() == null){
			System.out.println("No GameObjects to init. Add at least one and try again");
			return;
		}

		for(GameObject o : objects){
			if(!(o instanceof MapCollisionObject)){
//				Log.log(LogLevel.DEBUG, "Initing Object: " + o.getName());
			}
			o.init();
		}

		inited = true;
	}

	public Array<GameObject> get(){
		return new Array<GameObject>(objects);
	}

	public void render(SpriteBatch batch, float dt){
		if(!inited){
			Log.log(LogLevel.CRITICAL, "Tried to render uninited Array!");
			return;
		}
		player.render(batch, dt);
		for(int i = 0; i < objects.size; i++){
			GameObject o = objects.get(i);
			o.setMousePosition(mousePosition);
			if(o instanceof PlayerObject) continue;
			o.render(batch, dt);
		}
	}

	public static PlayerObject getPlayerObject(){
		return player;
	}

	public void setMousePosition(Vector2 mousePosition2){
		this.mousePosition = mousePosition2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose(){
		for(GameObject o : objects){
			o.dispose();
		}
	}

	public void drop(){
		inited = false;
		objects.clear();
	}

	public void remove(GameObject object) {
		this.objects.removeValue(object, true);
	}
}
