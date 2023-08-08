package com.pixelkobold.objects;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

public class GameObjectData {

	public static enum GameObjectType{
		STATIC, LIVING, BASE;

		public String getType(){
			return this.name();
		}
	}

	public String name;

//	public float x, y;
	public Vector2 pos;
	public GameObjectType type;
	public HashMap<String, Object> stats;

	public GameObject toGameObject(){
		GameObject o = null;

		switch(type){
		case LIVING:
			o = new LivingObject(name, pos);
			break;
		case STATIC:
			o = new StaticObject(name, pos);
			break;
		case BASE:
			//Shouldn't happen, but just in case
			o = new GameObject(name, pos) {};
			break;
		default:
			break;
		}
		return o;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public GameObjectType getType() {
		return type;
	}

	public void setType(GameObjectType type) {
		this.type = type;
	}

//	public float getX() {
//		return x;
//	}
//
//	public void setX(float x) {
//		this.x = x;
//	}
//
//	public float getY() {
//		return y;
//	}
//
//	public void setY(float y) {
//		this.y = y;
//	}

	public void setPos(Vector2 pos) {
		this.pos=  pos;
	}

	public Vector2 getPos() {
		return pos;
	}



}
