package com.pixelkobold.world;

import com.pixelkobold.objects.NPC;

public class TestWorld extends World {

	private NPC testNPC;

	@Override
	public TestWorld init() {
		this.mapName = "test_map1";
		super.init();
		return this;
	}

	@Override
	public void addObjects() {
//		objects.addObject(new PlayerObject(new Vector2(1500, 600)).setManager(objects));
	}

}
