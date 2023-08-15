package com.pixelkobold.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pixelkobold.config.Config;

public class DebugShapeRenderer {

    private static ShapeRenderer renderer = new ShapeRenderer();

    private static Array<Rectangle> rectangles = new Array<>();
    private static Array<Circle> circles = new Array<>();
    private static Array<Polygon> polygons = new Array<>();
    private static OrthographicCamera cam;

    public static void setCamera(OrthographicCamera camera) {
        cam = camera;
    }

    public static void drawAll() {
        if (Config.debug) {
            renderer.setProjectionMatrix(cam.combined);
            renderer.setAutoShapeType(true);
            renderer.begin();
            renderer.set(ShapeType.Line);

            if (rectangles.random() != null) {
                for (Rectangle rec : rectangles) {
                    renderer.rect(rec.x, rec.y, rec.width, rec.height);
                }
            }

            if (circles.random() != null) {
                for (Circle c : circles) {
                    renderer.circle(c.x, c.y, c.radius);
                }
            }

            if (polygons.random() != null) {
                for (Polygon pol : polygons) {
                    // System.out.println(pol.getVertices().length);
                    renderer.polygon(pol.getVertices());
                }
            }
            renderer.end();
            rectangles.clear();
            circles.clear();
            polygons.clear();
        }
    }

    public static void drawRectangle(Rectangle rec) {
        rectangles.add(rec);
    }

    public static void drawRectangle(Vector2 pos, Vector2 end) {
        drawRectangle(new Rectangle(pos.x, pos.y, end.x, end.y));
    }

    public static void drawRectangle(float x, float y, float w, float h) {
        drawRectangle(new Rectangle(x, y, w, h));
    }

    public static void drawCircle(Circle circle) {
        circles.add(circle);
    }

    public static void drawShape(Polygon poly) {
        polygons.add(poly);
    }

}
