package com.pixelkobold.shapes;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.pixelkobold.objects.LivingObject;
import com.pixelkobold.objects.LivingObject.Direction;

public class Cone extends Polygon {

	private final LivingObject parent;
	public float range = 0;
	public Vector2 center = new Vector2();

	public Cone(LivingObject parent) {
		this.parent = parent;
	}

	public void updateCone() {
		center = parent.box.getCenter(center);
		setVertices(new float[] { center.x, center.y, getConeX1(parent.facing), getConeY1(parent.facing),
				getConeX2(parent.facing), getConeY2(parent.facing) });
	}

	private float getConeX1(Direction dir) {
		// System.out.println(inventory == null);
        return switch (dir) {
            case DOWN, LEFT, UP -> center.x - range;
            case RIGHT -> center.x + range;
        };
	}

	private float getConeX2(Direction dir) {
        return switch (dir) {
            case DOWN, RIGHT, UP -> center.x + range;
            case LEFT -> center.x - range;
        };
	}

	private float getConeY1(Direction dir) {
        return switch (dir) {
            case DOWN, LEFT, RIGHT -> center.y - range;
            case UP -> center.y + range;
        };
	}

	private float getConeY2(Direction dir) {
        return switch (dir) {
            case DOWN -> center.y - range;
            case LEFT, RIGHT, UP -> center.y + range;
        };
	}
	public boolean collidesWithRectangle(Rectangle rectangle) {

		Vector2 center = new Vector2(this.getVertices()[0], this.getVertices()[1]);
		Rectangle rec = new Rectangle(rectangle);
		rec.x -= center.x;
		rec.y -= center.y;
		center.setZero();
		Vector2[] corners = { new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y),
				new Vector2(rec.x, rec.y + rec.height), new Vector2(rec.x + rec.width, rec.y + rec.height) };

        switch (parent.facing) {
            case DOWN -> {
                Vector2 left = new Vector2(-range, -range);
                Vector2 right = new Vector2(range, -range);
                for (Vector2 corner : corners) {
                    if (inSquare(corner, left.x, left.y, right.x, center.y)) {
                        if (corner.x <= -corner.y && corner.x >= corner.y) {
                            return true;
                        }
                    }
                }
            }
            case UP -> {
                Vector2 left = new Vector2(center).add(-range, range);
                Vector2 right = new Vector2(center).add(range, range);
                for (Vector2 corner : corners) {
                    if (inSquare(corner, left.x, center.y, right.x, right.y)) {
                        if (corner.x >= -corner.y && corner.x <= corner.y) {
                            return true;
                        }
                    }

                }
            }
            case LEFT -> {
                Vector2 up = new Vector2(center).add(-range, range);
                Vector2 down = new Vector2(center).add(-range, -range);
                for (Vector2 corner : corners) {
                    if (inSquare(corner, up.x, down.y, center.x, up.y)) {
                        if (Math.abs(corner.x) > Math.abs(corner.y)) {
                            return true;
                        }
                    }
                }
            }
            case RIGHT -> {
                Vector2 up = new Vector2(center).add(range, range);
                Vector2 down = new Vector2(center).add(range, -range);
                for (Vector2 corner : corners) {
                    if (inSquare(corner, center.x, down.y, down.x, up.y)) {
                        if (Math.abs(corner.x) > Math.abs(corner.y)) {
                            return true;
                        }
                    }
                }
            }
        }
		return false;
	}

	public boolean inSquare(Vector2 point, float xMin, float yMin, float xMax, float yMax) {
		return point.x > xMin && point.x < xMax && point.y > yMin && point.y < yMax;
	}
}
