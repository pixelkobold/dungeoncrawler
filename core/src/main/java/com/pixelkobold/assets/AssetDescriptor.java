package com.pixelkobold.assets;

import com.pixelkobold.assets.Asset.AssetType;

public record AssetDescriptor(AssetType type, String name, String path) {
}
