package com.pixelkobold.assets;

import com.pixelkobold.assets.Asset.AssetType;
import lombok.Data;
import lombok.EqualsAndHashCode;

public record AssetDescriptor(AssetType type, String name, String path) {
}
