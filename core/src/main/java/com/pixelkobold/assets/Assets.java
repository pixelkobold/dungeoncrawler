package com.pixelkobold.assets;

import com.pixelkobold.assets.Asset.AssetType;

public class Assets {

	public static void addAssets() {
        AssetManager.addToLoad(new AssetDescriptor(AssetType.SPRITE, "player", "img/player.png"));
        AssetManager.addToLoad(new AssetDescriptor(AssetType.SPRITE, "MainBack", "img/MainBack.png"));
    }
}
