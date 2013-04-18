package com.example.shoot_bubble;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

public abstract class Sprite {

	public static int TYPE_BUBBLE = 1;
	public static int TYPE_IMAGE = 2;
	public static int TYPE_LAUNCH_BUBBLE = 3;
	private Rect spriteArea;

	// private int saved_id;

	public Sprite(Rect spriteArea) {
		this.spriteArea = spriteArea;
		// saved_id = -1;
	}

	// public final int getSavedId()
	// {
	// return saved_id;
	// }
	//
	// public final void clearSavedId()
	// {
	// saved_id = -1;
	// }

	public abstract int getTypeId();

	public void changeSpriteArea(Rect newArea) {
		spriteArea = newArea;
	}

	// Offset the rectangle by adding dx to its left and right coordinates, and
	// adding dy to its top and bottom coordinates.
	public final void relativeMove(Point p) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offset(p.x, p.y);
	}

	public final void relativeMove(int x, int y) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offset(x, y);
	}

	// Offset the rectangle to a specific (left, top) position, keeping its
	// width and height the same.
	public final void absoluteMove(Point p) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offsetTo(p.x, p.y);
	}

	public final Point getSpritePosition() {
		return new Point(spriteArea.left, spriteArea.top);
	}

	public final Rect getSpriteArea() {
		return spriteArea;
	}

	public static void drawImage(Bmp image, int x, int y, Canvas c,
			double scale, int dx, int dy) {
		c.drawBitmap(image.bmp, (float) (x * scale + dx),
				(float) (y * scale + dy), null);
	}

	public static void drawImageClipped(Bmp image, int x, int y, Rect clipr,
			Canvas c, double scale, int dx, int dy) {
		 c.save(Canvas.CLIP_SAVE_FLAG);
		c.clipRect((float) (clipr.left * scale + dx), (float) (clipr.top
				* scale + dy), (float) (clipr.right * scale + dx),
				(float) (clipr.bottom * scale + dy), Region.Op.REPLACE);
		c.drawBitmap(image.bmp, (float) (x * scale + dx),
				(float) (y * scale + dy), null);
		 c.restore();
	}

	public abstract void paint(Canvas c, double scale, int dx, int dy);

}
