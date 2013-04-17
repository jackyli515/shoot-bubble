package com.example.shoot_bubble;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import java.util.Vector;

public abstract class Sprite {
	public static int TYPE_BUBBLE = 1;
	public static int TYPE_IMAGE = 2;
	public static int TYPE_LAUNCH_BUBBLE = 3;
	public static int TYPE_OTHER = 4;

	public Rect spriteArea;
	public int id;

	public Sprite(Rect spriteArea) {
		this.spriteArea = spriteArea;
		id = -1;
	}

	// public void saveState(Bundle map, Vector saved_sprites)
	// {
	// if (id != -1) {
	// return;
	// }
	// saved_id = saved_sprites.size();
	// saved_sprites.addElement(this);
	// map.putInt(String.format("%d-left", saved_id), spriteArea.left);
	// map.putInt(String.format("%d-right", saved_id), spriteArea.right);
	// map.putInt(String.format("%d-top", saved_id), spriteArea.top);
	// map.putInt(String.format("%d-bottom", saved_id), spriteArea.bottom);
	// map.putInt(String.format("%d-type", saved_id), getTypeId());
	// }

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

	// TODO msh fahem 2eh 2l relative move we 2eh 2l absolute

	public final void relativeMove(Point p) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offset(p.x, p.y);
	}

	public final void relativeMove(int x, int y) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offset(x, y);
	}

	public final void absoluteMove(Point p) {
		spriteArea = new Rect(spriteArea);
		spriteArea.offsetTo(p.x, p.y);
	}

	public final Point getSpritePosition() {
		return new Point(spriteArea.left, spriteArea.top);
	}

	// public final Rect getSpriteArea()
	// {
	// return spriteArea;
	// }
//TODO SCALE
	public static void drawImage(Bmp image, int x, int y, Canvas c,
			double scale, int dx, int dy) {
		c.drawBitmap(image.bmp, (float) (x * scale + dx),
				(float) (y * scale + dy), null);
	}

	public static void drawImageClipped(Bmp image, int x, int y, Rect clipr,
			Canvas c, double scale, int dx, int dy) {
		// c.save(Canvas.CLIP_SAVE_FLAG);
		c.clipRect((float) (clipr.left * scale + dx), (float) (clipr.top
				* scale + dy), (float) (clipr.right * scale + dx),
				(float) (clipr.bottom * scale + dy), Region.Op.REPLACE);
		c.drawBitmap(image.bmp, (float) (x * scale + dx),
				(float) (y * scale + dy), null);
		// c.restore();
	}

	public abstract void paint(Canvas c, double scale, int dx, int dy);
}
