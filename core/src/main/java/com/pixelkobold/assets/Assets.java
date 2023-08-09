package com.pixelkobold.assets;

import com.pixelkobold.assets.Asset.AssetType;

public class Assets {

	public static final void addAssets() {
        AssetManager.addToLoad(new AssetDescriptor(AssetType.SPRITE, "PLAYER_SPRITE_SHEET", AssetCategory.MAIN,"img/collision.png"));
    }
}
