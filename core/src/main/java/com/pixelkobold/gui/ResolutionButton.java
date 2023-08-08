package com.pixelkobold.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.pixelkobold.config.Config;

public class ResolutionButton extends TextButton {

	public ResolutionButton(int width, int height, TextButtonStyle transparent) {
		super(width+"x"+height, transparent);

		//right();

		if(Config.screenWidth==width&&Config.screenHeight==height){
			setChecked(true);
		}else{
			setChecked(false);
		}

		setWidth(100);

		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Config.screenWidth = width;
				Config.screenHeight = height;
			}
		});
	}

}
