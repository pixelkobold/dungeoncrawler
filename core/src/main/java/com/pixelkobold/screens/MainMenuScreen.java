package com.pixelkobold.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.config.Config;

public class MainMenuScreen implements Screen {
    private Sprite bg;

    private Stage stage;
    private final SpriteBatch batch = new SpriteBatch();

    @Override
    public void show() {
        setupGUI();
        bg = AssetManager.get("MainBack").asSprite();
    }

    public void setupGUI() {
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table(skin);

        Gdx.input.setInputProcessor(stage);

        TextButton buttonPlay = new TextButton("Start Game", skin);
        buttonPlay.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        buttonPlay.setSize(100, 20);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.PLAY_SCREEN);
            }
        });
        table.add(buttonPlay);
        table.row();

        TextButton buttonOptions = new TextButton("Options", skin);
        buttonOptions.setSize(100, 20);
        buttonOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.setScreen(Screens.OPTIONS_SCREEN);
            }
        });

        table.add(buttonOptions);
        table.setFillParent(true);

        stage.addActor(table);

        table.setDebug(Config.debug);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (bg != null) {
            batch.draw(bg, 0, 0);
        }
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
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
    public void dispose() {
        stage.dispose();
    }

}
