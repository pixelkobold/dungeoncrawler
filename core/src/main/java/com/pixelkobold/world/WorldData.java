package com.pixelkobold.world;

import java.util.ArrayList;

import com.pixelkobold.objects.GameObjectData;
import com.pixelkobold.objects.GameObjectData.GameObjectType;
import com.pixelkobold.objects.IInteractable;
import com.pixelkobold.objects.LivingObject;
import com.pixelkobold.objects.StaticObject;

public class WorldData {

	private String mapName;
	private ArrayList<GameObjectData> gameObjects = new ArrayList<>();

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public ArrayList<GameObjectData> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObjectData> objects) {
		this.gameObjects = objects;
	}

	public World toWorld() {
		World w = new World() {
			{
				this.mapName = WorldData.this.mapName;
			}

			@Override
			public void addObjects() {
				super.addObjects();
				for (GameObjectData data : gameObjects) {
					this.objects.addObject(data.toGameObject());
				}

			}
		};
		return w;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorldData [mapName=").append(mapName).append(", gameObjects=").append(gameObjects).append("]");
		return builder.toString();
	}

	public static WorldData fromWorld(World w) {
		WorldData data = new WorldData();
		data.mapName = w.mapName;

//		ArrayList<GameObjectData> list = new ArrayList<>();
		w.objects.get().forEach((o) -> {
			if (!(o instanceof StaticObject) || o instanceof IInteractable) {
				GameObjectData gameObjectData = new GameObjectData();
				gameObjectData.name = o.getName();
				gameObjectData.pos = o.getPos();
				gameObjectData.type = (o instanceof LivingObject ? GameObjectType.LIVING : o instanceof StaticObject ? GameObjectType.STATIC : GameObjectType.BASE);
				gameObjectData.stats =( (LivingObject) o).getStats();
				data.gameObjects.add(gameObjectData);
			}
		});

		return data;
	}

}
