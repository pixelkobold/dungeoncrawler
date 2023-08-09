package com.pixelkobold.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.pixelkobold.assets.AssetCategory;
import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.assets.Assets;
import com.pixelkobold.config.Config;
import com.pixelkobold.log.Log;
import com.pixelkobold.world.Worlds;

public class LoadingScreen implements Screen {
	private int count = 0;

	private SpriteBatch batch = new SpriteBatch();
	private Texture bg = new Texture(Gdx.files.internal("img/loading.png"));

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

        batch.draw(bg, 0, 0);

        batch.end();

        switch (count) {
            case 0 -> {
                try {
                    Log.init();
                    Config.init();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            case 1 -> Assets.addAssets();
            case 2 -> {
                AssetManager.setGroups();

                AssetManager.loadAll();
            }
//			GameObject.populateMap();
            case 5 -> Worlds.loadAll();
            case 20 -> ((Game) Gdx.app.getApplicationListener()).setScreen(Screens.MAIN_MENU_SCREEN);
        }

		count++;
	}

	@Override
	public void resize(int width, int height) {

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

	}

}
