package com.pixelkobold.assets;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;

public class AssetManager {


    private static HashMap<String, Asset> assets = new HashMap<>();

    private static Array<AssetDescriptor> descriptors = new Array<>();

    public static void loadAll() {
        assets.clear();

        for (AssetDescriptor descriptor : descriptors) {
            if (assets.containsKey(descriptor.name())) {
                Log.log(LogLevel.INFO, "Asset with name " + descriptor.name() + " is already loaded! Skipping!");
                continue;
            }
            assets.put(descriptor.name(), new Asset(descriptor.type(), descriptor.path()));
        }

        assets.put("emptyPixel", new Asset(AssetType.SPRITE, "emptyPixel"));
    }

    public static void addToLoad(AssetDescriptor asset) {
        descriptors.add(asset);
        Log.log(LogLevel.INFO, "Adding Asset to load. Asset: " + asset.toString());
    }

    /**
     * @param name Asset.name
     * @return Asset
     */
    public static Asset get(String name) {
        return assets.get(name);
    }

    public static void load(AssetDescriptor descriptor) {
        Log.log(LogLevel.DEBUG, "Loading on-demand asset: " + descriptor.toString());
        Asset asset = new Asset(descriptor.type(), descriptor.path());
        assets.put(descriptor.name(), asset);
    }

}
