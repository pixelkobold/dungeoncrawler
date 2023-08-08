package com.pixelkobold.assets;

import com.pixelkobold.assets.Asset.AssetType;

public class AssetDescriptor {

	@Override
	public String toString() {
		return "AssetDescriptor [type=" + type + ", name=" + name + ", group="+group+" path=" + path + "]";
	}

	public AssetType type;
	public String name;
	public String path;
	public AssetCategory group;

	public AssetDescriptor(AssetType type, String name, AssetCategory group, String path) {
		this.type = type;
		this.name = name;
		this.group = group;
		this.path = path;
	}
}
