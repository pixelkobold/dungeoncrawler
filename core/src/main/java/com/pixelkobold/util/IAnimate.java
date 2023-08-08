package com.pixelkobold.util;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface IAnimate {
	public Sprite[] getFrames();

	public float getFrameTime();

	public int getCurrFrame();

	public void setCurrFrame(int frame);

	public float getCurrTime();

	public void setCurrTime(float f);

	public default void updateFrames(float dt){
		if(getCurrTime() >= getFrameTime()){
			if(getCurrFrame() + 1 < getFrames().length) setCurrFrame(getCurrFrame() + 1);
			else setCurrFrame(0);
			setCurrTime(0);
		}
		setCurrTime(getCurrTime() + dt);
	}

}
