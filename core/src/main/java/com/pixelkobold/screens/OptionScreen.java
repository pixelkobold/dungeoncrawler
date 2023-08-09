package com.pixelkobold.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.pixelkobold.assets.AssetManager;
import com.pixelkobold.config.Config;
import com.pixelkobold.gui.ResolutionButton;

public class OptionScreen implements Screen {
	private Sprite bg;

	private SpriteBatch batch = new SpriteBatch();

	protected Table table;
	protected Stage stage;
	public static Skin skin, transparentSkin;

	@Override
	public void show(){
		setupGUI();

		bg = AssetManager.get("MAIN_BACK").asSprite();
	}

	public void setupGUI(){
		stage = new Stage();

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		table = new Table(skin);
		// table.setBounds(0, 0, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		// table.setClip(true);

		Gdx.input.setInputProcessor(stage);

		// defaultStyle.over = skin.getDrawable("button.hover");

		TextButton fullScreenButton = new TextButton("Toglle Full Screen", skin, "default");
		fullScreenButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				Config.fullscreen = !Config.fullscreen;
			}
		});

		// fullScreenButton.setFillParent(true);
		TextButton saveSettingsButton = new TextButton("Save settings", skin, "default");
		saveSettingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				show();
			}
		});
		Container<TextButton> saveSettingContainer = new Container<TextButton>(saveSettingsButton);

		saveSettingContainer.padTop(Value.percentHeight(.6f, table));

		TextButton backButton = new TextButton("Go back", skin, "default");
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				Screens.setScreen(Screens.MAIN_MENU_SCREEN);
			}
		});

		VerticalGroup buttons1 = new VerticalGroup();
		buttons1.fill();
		buttons1.addActor(fullScreenButton);

		VerticalGroup buttons2 = new VerticalGroup();
		buttons2.fill();
		buttons2.addActor(saveSettingsButton);
		buttons2.addActor(backButton);

		// buttons.setPosition(Gdx.graphics.getWidth()/4,
		// Gdx.graphics.getHeight()/5);

		VerticalGroup resolution = new VerticalGroup();
		ButtonGroup<TextButton> buttonGroup = new ButtonGroup<>();
		buttonGroup.setMaxCheckCount(1);
		resolution.fill();

		transparentSkin = new Skin(new TextureAtlas("select.pack"));
		TextButtonStyle transparent = new TextButtonStyle();
		transparent.checked = transparentSkin.getDrawable("checked");
		transparent.up = transparentSkin.getDrawable("up");

		addResolutions(buttonGroup, transparent);

		for(TextButton b : buttonGroup.getButtons()){
			resolution.addActor(b);
		}

		resolution.right();

		ScrollPane resolScroll = new ScrollPane(resolution);
		resolScroll.setHeight(700);

		Label fullScreenLabel = new Label("", skin);

		fullScreenLabel.addAction(Actions.forever(new Action() {
			@Override
			public boolean act(float delta){
				if(Config.fullscreen){
					fullScreenLabel.setColor(Color.GREEN);
					fullScreenLabel.setText("on");
				}else{
					fullScreenLabel.setColor(Color.RED);
					fullScreenLabel.setText("off");
				}
				return true;
			}
		}));

		table.setFillParent(true);
		Table left = new Table();
		left.add(buttons1).spaceBottom(Value.percentHeight(.6f, table));
		left.add(fullScreenLabel).top();
		left.row();
		// left.row();
		left.add(buttons2);
		// table.add(buttons1.left()).left();
		table.add(left).left().padLeft(Value.percentWidth(0.1f, table)).expandX();
		// table.add(fullScreenLabel).top().spaceRight(Value.percentWidth(.5f,
		// table));
		table.add(resolScroll).right().padRight(Value.percentWidth(0.1f, table));

		// table.row();
		// table.add(buttons2).bottom().left();
		// table.center();

		stage.addActor(table);

		if(Config.debug) table.setDebug(true);

	}

	private void addResolutions(ButtonGroup<TextButton> buttonGroup, TextButtonStyle transparent){

		buttonGroup.add(new ResolutionButton(480, 234, transparent));
		buttonGroup.add(new ResolutionButton(480, 250, transparent));
		buttonGroup.add(new ResolutionButton(400, 300, transparent));
		buttonGroup.add(new ResolutionButton(640, 480, transparent));
		buttonGroup.add(new ResolutionButton(640, 512, transparent));
		buttonGroup.add(new ResolutionButton(800, 480, transparent));
		buttonGroup.add(new ResolutionButton(848, 480, transparent));
		buttonGroup.add(new ResolutionButton(850, 480, transparent));
		buttonGroup.add(new ResolutionButton(800, 600, transparent));
		buttonGroup.add(new ResolutionButton(960, 540, transparent));
		buttonGroup.add(new ResolutionButton(832, 624, transparent));
		buttonGroup.add(new ResolutionButton(1024, 576, transparent));
		buttonGroup.add(new ResolutionButton(960, 720, transparent));
		buttonGroup.add(new ResolutionButton(1024, 768, transparent));
		buttonGroup.add(new ResolutionButton(1024, 800, transparent));
		buttonGroup.add(new ResolutionButton(1152, 720, transparent));
		buttonGroup.add(new ResolutionButton(1152, 768, transparent));
		buttonGroup.add(new ResolutionButton(1280, 720, transparent));
		buttonGroup.add(new ResolutionButton(1280, 768, transparent));
		buttonGroup.add(new ResolutionButton(1152, 864, transparent));
		buttonGroup.add(new ResolutionButton(1024, 1024, transparent));
		buttonGroup.add(new ResolutionButton(1360, 768, transparent));
		buttonGroup.add(new ResolutionButton(1366, 768, transparent));
		buttonGroup.add(new ResolutionButton(1280, 960, transparent));
		buttonGroup.add(new ResolutionButton(1600, 900, transparent));
		buttonGroup.add(new ResolutionButton(1400, 1050, transparent));
		buttonGroup.add(new ResolutionButton(1400, 1080, transparent));
		buttonGroup.add(new ResolutionButton(1600, 1024, transparent));
		buttonGroup.add(new ResolutionButton(1600, 1200, transparent));
		buttonGroup.add(new ResolutionButton(1920, 1080, transparent));
		buttonGroup.add(new ResolutionButton(1920, 1200, transparent));
		buttonGroup.add(new ResolutionButton(2048, 1152, transparent));
		buttonGroup.add(new ResolutionButton(2880, 900, transparent));
		buttonGroup.add(new ResolutionButton(1920, 1400, transparent));
		buttonGroup.add(new ResolutionButton(2538, 1080, transparent));
		buttonGroup.add(new ResolutionButton(2560, 1080, transparent));
		buttonGroup.add(new ResolutionButton(1920, 1440, transparent));
		buttonGroup.add(new ResolutionButton(2160, 1440, transparent));
		buttonGroup.add(new ResolutionButton(2048, 1536, transparent));
		buttonGroup.add(new ResolutionButton(2304, 1440, transparent));
		buttonGroup.add(new ResolutionButton(2560, 1440, transparent));
		buttonGroup.add(new ResolutionButton(2560, 1600, transparent));
		buttonGroup.add(new ResolutionButton(2560, 1800, transparent));
		buttonGroup.add(new ResolutionButton(2560, 1920, transparent));
		buttonGroup.add(new ResolutionButton(3440, 1440, transparent));
		buttonGroup.add(new ResolutionButton(2763, 1824, transparent));
		buttonGroup.add(new ResolutionButton(2880, 1800, transparent));
		buttonGroup.add(new ResolutionButton(2560, 2048, transparent));
		buttonGroup.add(new ResolutionButton(2800, 2100, transparent));
		buttonGroup.add(new ResolutionButton(3200, 1800, transparent));
		buttonGroup.add(new ResolutionButton(3000, 2000, transparent));
		buttonGroup.add(new ResolutionButton(3200, 2048, transparent));
		buttonGroup.add(new ResolutionButton(3200, 2400, transparent));
		buttonGroup.add(new ResolutionButton(3840, 2160, transparent));
		buttonGroup.add(new ResolutionButton(4096, 2304, transparent));
		buttonGroup.add(new ResolutionButton(5120, 2160, transparent));
		buttonGroup.add(new ResolutionButton(5120, 2880, transparent));
		buttonGroup.add(new ResolutionButton(8192, 4608, transparent));
		buttonGroup.add(new ResolutionButton(8192, 8192, transparent));
	}

	@Override
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0);

		batch.end();

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height){
		if(stage != null) stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void hide(){}

	@Override
	public void dispose(){}

}
