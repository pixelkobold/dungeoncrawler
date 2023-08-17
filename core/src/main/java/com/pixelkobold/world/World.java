package com.pixelkobold.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.assets.AssetDescriptor;
import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.entity.components.*;
import com.pixelkobold.entity.system.AnimationSystem;
import com.pixelkobold.entity.system.PlayerMovementSystem;
import com.pixelkobold.map.MapRenderer;
import com.pixelkobold.renderers.DebugShapeRenderer;
import com.pixelkobold.screens.Screens;

import static com.github.czyzby.kiwi.util.tuple.immutable.Pair.of;

public class World extends InputAdapter {

    protected SpriteBatch batch;
    public static OrthographicCamera cam;
    public static Viewport camViewport;

    public MapRenderer mapRenderer;

    public static Vector2 mousePosition = new Vector2();

    protected Matrix4 normalProjection = new Matrix4();

    public String mapName;

    private Vector2 targetPos;

    private Engine engine;

    public World(String mapName) {
        this.mapName = mapName;
    }

    public void init() {
        batch = new SpriteBatch();

        engine = new PooledEngine();
        engine.addSystem(new AnimationSystem(batch));
        engine.addSystem(new PlayerMovementSystem());


        cam = new OrthographicCamera();
        camViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
        cam.zoom = .5f;
        AssetManager.load(new AssetDescriptor(AssetType.MAP, this.mapName, "maps/" + this.mapName + ".tmx"));

        mapRenderer = new MapRenderer(AssetManager.get(this.mapName).asMap());

        addObjects();
        addTransitionObjects();
    }

    public MapProperties getMapProperties() {
        TiledMap map = AssetManager.get(mapName).asMap();
        MapProperties props = map.getProperties();

        return props;
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

        var playerFrames = AssetManager.get("player").asSprite().split(32, 32);

        var player = engine.createEntity()
            .add(engine.createComponent(PositionComponent.class).setPosition(targetPos))
            .add(engine.createComponent(DirectionComponent.class))
            .add(engine.createComponent(StateComponent.class))
            .add(engine.createComponent(MovementComponent.class))
            .add(engine.createComponent(AnimationComponent.class).setFrames(playerFrames)
                .setIdle(of(0, 0), of(0, 1), of(1, 0), of(1, 1))
                .setMoving(of(2, 0), of(2, 1), of(2, 2), of(2, 3))
                .setRunning(of(3, 0), of(3, 1), of(3, 2), of(3, 3), of(3, 4), of(3, 5), of(3, 6), of(3, 7))
            )
            .add(engine.createComponent(PlayerComponent.class))
            .add(engine.createComponent(BoundingBoxComponent.class).setBox(new Rectangle(0, 0, 32, 32)));
        engine.addEntity(player);

//        objects.addObject(new PlayerObject(targetPos).setManager(objects));
    }

    public void render(float delta) {
        var player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
        cam.position.set(new Vector3(player.getComponent(PositionComponent.class).getPosition(), 0));

        camViewport.apply();
        mousePosition.set(new Vector2(Gdx.input.getX(), Gdx.input.getY()));


        DebugShapeRenderer.setCamera(cam);

        batch.begin();
        mapRenderer.setView(cam);
        mapRenderer.render(engine, delta);
        batch.end();
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

}
