package com.pixelkobold.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.assets.AssetDescriptor;
import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.map.MapRenderer;
import com.pixelkobold.objects.GameObjectManager;
import com.pixelkobold.objects.MapCollisionObject;
import com.pixelkobold.objects.PlayerObject;
import com.pixelkobold.objects.TransitionObject;
import com.pixelkobold.renderers.DebugShapeRenderer;
import com.pixelkobold.screens.Screens;

public abstract class World extends InputAdapter {

    protected SpriteBatch batch;
    public static OrthographicCamera cam;
    public static Viewport camViewport;

    public MapRenderer mapRenderer;

    public static Vector2 mousePosition = new Vector2();

    protected Matrix4 normalProjection = new Matrix4();

    public String mapName;

    private Vector2 targetPos;

    private Engine engine;

    public World init() {
        batch = new SpriteBatch();

        engine = new Engine();

        cam = new OrthographicCamera();
        camViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
        cam.zoom = .5f;
        AssetManager.load(new AssetDescriptor(AssetType.MAP, this.mapName, "maps/" + this.mapName + ".tmx"));

        mapRenderer = new MapRenderer(AssetManager.get(this.mapName).asMap());

        addObjects();
        addTransitionObjects();

        return this;
    }

    private void addTransitionObjects() {
        TiledMap map = AssetManager.get(mapName).asMap();
        map.getLayers().get("transitions").getObjects().forEach((MapObject object) -> {
            MapProperties props = object.getProperties();

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

        var player = engine.createEntity();

//        objects.addObject(new PlayerObject(targetPos).setManager(objects));
    }

    public void render(float delta) {
        cam.position.set((GameObjectManager.getPlayerObject()).getPosition3());

        camViewport.apply();
        mousePosition.set(new Vector2(Gdx.input.getX(), Gdx.input.getY()));


        DebugShapeRenderer.setCamera(cam);

        mapRenderer.setView(cam);
        mapRenderer.render(engine, delta);

        DebugShapeRenderer.drawAll();

    }

    public void addCollisionObjects() {
        TiledMap map = AssetManager.get(mapName).asMap();

//        TODO: Zrobić to dobrze
//		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collision");
//		for (int x = 0; x < layer.getWidth(); x++) {
//			for (int y = 0; y < layer.getHeight(); y++) {
//				Cell cell = layer.getCell(x, y);
//				if (cell != null) {
//					Vector2 p = new Vector2(x, y);
//					objects.addObject(new MapCollisionObject(p));
//				}
//			}
//		}

    }

    public void resize(int width, int height) {
        if (camViewport == null) return;
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

        if (key == Keys.ESCAPE) Screens.setScreen(Screens.MAIN_MENU_SCREEN);

        if (key == Keys.F5) Screens.setScreen(Screens.PLAY_SCREEN);
        return true;
    }

    public World setPos(Vector2 targetPos) {
        this.targetPos = targetPos;
        return this;
    }

}
