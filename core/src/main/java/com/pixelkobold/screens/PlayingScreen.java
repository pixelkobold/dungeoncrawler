package com.pixelkobold.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pixelkobold.config.Config;
import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;
import com.pixelkobold.world.World;
import lombok.Getter;

public class PlayingScreen implements Screen {
    public static World world;

    protected static InputMultiplexer inputs;

    protected BitmapFont font = new BitmapFont();
    @Getter
    protected static Table table = new Table();
    protected Stage stage;
    protected Skin skin;

    public static void setWorld(World w) {
        Log.log(LogLevel.DEBUG, "Closing world: " + world.mapName);
        if (world != null)
            world.close();
        Log.log(LogLevel.DEBUG, "Loading world: " + w.mapName);
        Log.log(LogLevel.DEBUG, "Loaded world: " + w.mapName);
        world = w;
        Screens.PLAY_SCREEN.show();
    }

    @Override
    public void show() {
        if (world == null)
            world = new World("test_map1");

        Log.log(LogLevel.DEBUG, "Initing world: " + world.mapName);

        world.init();

        setupUI();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void setupUI() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Skin bars = new Skin(new TextureAtlas(Gdx.files.internal("ui/bars.pack")));
        Table playerStats = new Table();
        ProgressBarStyle HPBarsStyle = new ProgressBarStyle();
        ProgressBarStyle MPBarsStyle = new ProgressBarStyle();
        Label HPLabel = new Label("", skin);
        Label MPLabel = new Label("", skin);
        Table left = new Table();

        Stack HPStack = new Stack();
        Stack MPStack = new Stack();


        table.clear();
        table.setSkin(skin);
        HPBarsStyle.background = bars.getDrawable("bar-Background");
        HPBarsStyle.knobBefore = bars.getDrawable("bar-Red");
        HPBarsStyle.knob = bars.getDrawable("bar-Empty");
//		HPBar.setStyle(HPBarsStyle);

        ProgressBar HPBar = new ProgressBar(0, 100, .2f, false, HPBarsStyle);

        MPBarsStyle.background = bars.getDrawable("bar-Background");
        // HPBar.setDisabled(true);
        MPBarsStyle.knobBefore = bars.getDrawable("bar-Blue");
        MPBarsStyle.knob = bars.getDrawable("bar-Empty");
//		MPBar.setStyle(MPBarsStyle);

        ProgressBar MPBar = new ProgressBar(0, 100, .2f, false, MPBarsStyle);


        HPStack.add(HPBar);
        HPStack.add(new Container<>(HPLabel).right());

        MPStack.add(MPBar);
        MPStack.add(new Container<>(MPLabel).right());

        // HPStack.add(backgroundBar);
        // HPStack.add(HPBar);

        playerStats.add(new Label("HP", skin));
        playerStats.add(HPStack);
        playerStats.row();
        playerStats.add(new Label("MP", skin));
        playerStats.add(MPStack);

        Button button = new Button(skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.PLAY_SCREEN);
            }
        });
        playerStats.row();
        playerStats.add(button);
        playerStats.left().top();
        left.setName("left");
        left.add(playerStats).expand().center().left().top().pad(0).maxHeight(Value.percentHeight(0.5f, table));
        left.row();

        // System.out.println(details.getParent().getName());
        table.add(left).expand().left().fill();
        table.setFillParent(true);
        if (Config.debug)
            table.setDebug(true, true);
        stage.addActor(table.top().left());
        // stage.addActor(GameObjectManager.getPlayerObject().inventory);

        inputs = new InputMultiplexer();
        inputs.addProcessor(0, stage);
        Gdx.input.setInputProcessor(inputs);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputs.addProcessor(1, world);

        world.render(delta);


        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        world.resize(width, height);
        if (stage != null)
            stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
