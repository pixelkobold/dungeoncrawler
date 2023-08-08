package com.pixelkobold.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.pixelkobold.assets.Asset;
import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.assets.AssetCategory;
import com.pixelkobold.assets.AssetDescriptor;
import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.map.MapRenderer;
import com.pixelkobold.objects.GameObject;
import com.pixelkobold.objects.GameObject.GameObjectProps;
import com.pixelkobold.objects.GameObjectManager;
import com.pixelkobold.objects.LivingObject;
import com.pixelkobold.objects.MapCollisionObject;
import com.pixelkobold.objects.PlayerObject;
import com.pixelkobold.objects.StaticObject;
import com.pixelkobold.objects.TransitionObject;
import com.pixelkobold.renderers.DebugShapeRenderer;
import com.pixelkobold.screens.Screens;

public abstract class World extends InputAdapter {

	public GameObjectManager objects = new GameObjectManager();

	protected SpriteBatch batch;
	public static OrthographicCamera cam;
	public static Viewport camViewport;

	public MapRenderer mapRenderer;

	// private Array<MapCollisionObject> collisions;

	public static Vector2 mousePosition = new Vector2();

	protected Matrix4 normalProjection = new Matrix4();

	// protected GuiManager gui = new GuiManager();
	// protected DialogWindowManager dialogs = new DialogWindowManager();

	public String mapName;

	private Vector2 targetPos;

	public World init() {
		if (objects == null)
			objects = new GameObjectManager();

		// Gdx.input.setInputProcessor(this);

		objects.drop();

//		AssetManager.setGroups(AssetCategory.CITY1, AssetCategory.GUI, AssetCategory.MAIN, AssetCategory.TEMP);
		// AssetManager.loadAll();

		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		camViewport = new FitViewport(720, 405, cam);
		// cam.zoom = 2.5f;
		AssetManager.load(
				new AssetDescriptor(AssetType.MAP, this.mapName, AssetCategory.MAIN, "maps/" + this.mapName + ".tmx"));

		mapRenderer = new MapRenderer(AssetManager.get(this.mapName).asMap());

		addObjects();
		addCollisionObjects();
		addTransitionObjects();
		objects.initAll();

		// normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());

		// addGuiElements();
		// gui.initAll();
		// GuiRenderer.setGui(gui);

		// this.dialogs.initAll();
		// System.out.println(mapName);
		return this;
	}

	private void addTransitionObjects() {
		TiledMap map = AssetManager.get(mapName).asMap();
		map.getLayers().get("transitions").getObjects().forEach((MapObject object) -> {
			MapProperties props = object.getProperties();

			objects.addObject(new TransitionObject(props));
		});
	}

	public void addObjects() {
		TiledMap map = AssetManager.get(mapName).asMap();
		MapProperties props = map.getLayers().get("Player").getObjects().get("Player").getProperties();
		if (targetPos == null) {
			float x = props.get("x", float.class);
			float y = props.get("y", float.class);
			targetPos = new Vector2(x, y);
		}

		objects.addObject(new PlayerObject(targetPos).setManager(objects));

		map.getLayers().get("gameObjectLayer").getObjects().forEach(mo -> {
			MapProperties p = mo.getProperties();
			GameObject o;

			switch (p.get("type", String.class)) {
			case "living":
				o = new LivingObject("", new Vector2());
			case "static":
				o = new StaticObject("", new Vector2());
			default:
				o = new GameObject("", new Vector2()) {
				};
			}

			GameObjectProps gop = GameObject.propsMap.get(p.get("json", String.class));
			if (gop == null)
				gop = new GameObjectProps(); // No such json in map

			o.setName(either(o.getName(), either(gop.name, p.get("name", String.class))));
			Asset a = AssetManager.get(either(gop.sprite, either(p.get("sprite", String.class), o.getName())));
			if (a != null)
				o.sprite = a.asSprite();

		});
	}

	private static <T> T either(T a, T b) {
		if (a == null || ((String) a) == "")
			return b;
		return a;
	}

	public void render(float delta) {
		// cam.normalizeUp();
		// cam.lookAt(100, 100, 0);
		cam.position.set((GameObjectManager.getPlayerObject()).getPosition3());
		// cam.update();
		camViewport.apply();
		mousePosition.set(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		objects.setMousePosition(mousePosition);
		DebugShapeRenderer.setCamera(cam);

		mapRenderer.setView(cam);
		mapRenderer.render(objects, delta);

		// mapRenderer.render1

		try {
			batch.getColor();
		} catch (NullPointerException e) {
			batch = new SpriteBatch();
		}
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		batch.end();

		DebugShapeRenderer.drawAll();

	}

	public void addCollisionObjects() {
		TiledMap map = AssetManager.get(mapName).asMap();

		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collision");
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Vector2 p = new Vector2(x, y);
					objects.addObject(new MapCollisionObject(p));
				}
			}
		}

	}

	public void resize(int width, int height) {
		if (camViewport == null)
			return;
		camViewport.update(width, height);
	}

	public void dispose() {
		batch.dispose();
	}

	public void close() {
		dispose();
		this.batch = null;
		this.mapRenderer = null;
		this.normalProjection = null;
		this.objects = null;
		System.gc();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return true;
	}

	@Override
	public boolean keyDown(int key) {

		if (key == Keys.ESCAPE)
			Screens.setScreen(Screens.MAIN_MENU_SCREEN);

		if (key == Keys.F5)
			Screens.setScreen(Screens.PLAY_SCREEN);
		return true;
	}

	public World setPos(Vector2 targetPos) {
		this.targetPos = targetPos;
		return this;
	}

}
