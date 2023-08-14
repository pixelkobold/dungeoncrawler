package com.pixelkobold.map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer extends OrthogonalTiledMapRenderer {

    public MapRenderer(TiledMap map) {
        super(map);
    }

    public void render(Engine objects, float dt) {
        beginRender();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            MapLayer layer = map.getLayers().get(i);
            if (layer.isVisible()) {
                if (layer.getName().equals("gameObjectLayer")) {
                    objects.update(dt);
                    continue;
                }
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                }
                if (layer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) layer);
                } else {
                    renderObjects(layer);
                }
            }
        }
        endRender();

    }
}
