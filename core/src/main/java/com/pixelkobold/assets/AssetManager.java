package com.pixelkobold.assets;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import com.pixelkobold.assets.Asset.AssetType;
import com.pixelkobold.log.Log;
import com.pixelkobold.log.LogLevel;

public class AssetManager {


	private static HashMap<String, Asset> assets = new HashMap<>();

	private static HashMap<AssetCategory, Array<AssetDescriptor>> descriptors = new HashMap<>();

	private static Array<AssetCategory> loadGroups = new Array<>();

	public static void loadAll(){
		assets.clear();

		for(AssetCategory group : loadGroups){
			for(AssetDescriptor asset : descriptors.get(group)){
				if(assets.containsKey(asset.name)){
					Log.log(LogLevel.INFO, "Asset with name " + asset.name + " is already loaded! Skipping!");
					continue;
				}
				assets.put(asset.name, new Asset(asset.type, asset.path));
			}
		}
		assets.put("emptyPixel", new Asset(AssetType.SPRITE, "emptyPixel"));
	}

	public static void addToLoad(AssetDescriptor asset){

		if(descriptors.get(asset.group) == null) descriptors.put(asset.group, new Array<>());

		descriptors.get(asset.group).add(asset);

		Log.log(LogLevel.INFO, "Adding Asset to load. Asset: " + asset.toString());
	}

	public static void setGroups(AssetCategory... groups){
		loadGroups = new Array<>(groups);
		loadGroups.add(AssetCategory.MAIN);
		loadGroups.add(AssetCategory.GUI);
	}

	/**
	 *
	 * @param name
	 *            Asset.name
	 * @return Asset
	 */
	public static Asset get(String name){
		return assets.get(name);
	}

	public static void load(AssetDescriptor descriptor){
		Log.log(LogLevel.DEBUG, "Loading on-demand asset: "+descriptor.toString());
		Asset asset = new Asset(descriptor.type, descriptor.path);
		assets.put(descriptor.name, asset);
	}

}
