package com.pixelkobold.assets;

import com.badlogic.gdx.utils.Array;
import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.log.Log;


import java.util.HashMap;

public class AssetManager {


    private static final HashMap<String, Asset> assets = new HashMap<>();

    private static final Array<AssetDescriptor> descriptors = new Array<>();

    public static void loadAll() {
        assets.clear();

        for (AssetDescriptor descriptor : descriptors) {
            if (assets.containsKey(descriptor.name())) {
                Log.info("AssetManager","Asset with name " + descriptor.name() + " is already loaded! Skipping!");
                continue;
            }
            assets.put(descriptor.name(), new Asset(descriptor.type(), descriptor.path()));
        }

        assets.put("emptyPixel", new Asset(AssetType.SPRITE, "emptyPixel"));
    }

    public static void addToLoad(AssetDescriptor asset) {
        descriptors.add(asset);
        Log.info("AssetManager", "Adding Asset to load. Asset: " + asset.toString());
    }

    /**
     * @param name Asset.name
     * @return Asset
     */
    public static Asset get(String name) {
        return assets.get(name);
    }

    public static void load(AssetDescriptor descriptor) {
        Log.info("AssetManager", "Loading on-demand asset: " + descriptor.toString());
        Asset asset = new Asset(descriptor.type(), descriptor.path());
        assets.put(descriptor.name(), asset);
    }

}
