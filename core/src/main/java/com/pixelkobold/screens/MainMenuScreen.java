package com.pixelkobold.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.config.Config;

public class MainMenuScreen implements Screen {
	// TODO: write GUI. Again

	private Sprite bg;

	// private GuiManager mainGui;
	// private GuiManager optionsGui;

	private SpriteBatch batch = new SpriteBatch();

	protected Table table;
	protected Stage stage;
	protected Skin skin;
	protected TextureAtlas atlas;
	protected BitmapFont fontWhite, fontBlack;

	protected TextButton buttonPlay;

	@Override
	public void show(){
		setupGUI();

//		bg = AssetManager.get("MAIN_BACK").asSprite();
	}

	public void setupGUI(){
		stage = new Stage();

		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		table = new Table(skin);

		Gdx.input.setInputProcessor(stage);

		buttonPlay = new TextButton("Start Game", skin, "default");
		buttonPlay.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		buttonPlay.setSize(100, 20);

		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				Screens.setScreen(Screens.PLAY_SCREEN);
			}
		});
		table.add(buttonPlay);
		table.row();

		TextButton buttonOptions = new TextButton("Options", skin, "default");
		// buttonOptions.setPosition(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2 - (buttonPlay.getHeight() + 10));
		buttonOptions.setSize(100, 20);
		buttonOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				Screens.setScreen(Screens.OPTIONS_SCREEN);
			};
		});

		table.add(buttonOptions);
		table.setFillParent(true);

		stage.addActor(table);

		if(Config.debug) table.setDebug(true);
table.setDebug(Config.debug);
	}

	@Override
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
//		batch.draw(bg, 0, 0);

		batch.end();

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height){
		if(stage != null) stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause(){

	}

	@Override
	public void resume(){

	}

	@Override
	public void hide(){

	}

	@Override
	public void dispose(){
		stage.dispose();
		batch.dispose();
	}

}
