package com.example.shoot_bubble;

import java.util.Vector;

import android.graphics.Canvas;
import android.os.Bundle;

public abstract class ScreenGame {
	Vector sprites;

	//
	// public final void saveSprites(Bundle map, Vector savedSprites) {
	// for (int i = 0; i < sprites.size(); i++) {
	// ((Sprite) sprites.elementAt(i)).saveState(map, savedSprites);
	// map.putInt(String.format("game-%d", i),
	// ((Sprite) sprites.elementAt(i)).getSavedId());
	// }
	// map.putInt("numGameSprites", sprites.size());
	// }
	//
	// public final void restoreSprites(Bundle map, Vector savedSprites) {
	// sprites = new Vector();
	// int numSprites = map.getInt("numGameSprites");
	// for (int i = 0; i < numSprites; i++) {
	// int spriteIdx = map.getInt(String.format("game-%d", i));
	// sprites.addElement(savedSprites.elementAt(spriteIdx));
	// }
	// }

	public ScreenGame() {
		// TODO Auto-generated constructor stub
		sprites = new Vector();
	}

	public final void addSprite(Sprite sprite) {
		sprites.removeElement(sprite);
		sprites.addElement(sprite);
	}

	public final void removeSprite(Sprite sprite) {
		sprites.removeElement(sprite);
	}

	public final void spriteToBack(Sprite sprite) {
		sprites.removeElement(sprite);
		sprites.insertElementAt(sprite, 0);
	}

	public final void spriteToFront(Sprite sprite) {
		sprites.removeElement(sprite);
		sprites.addElement(sprite);
	}

	public void paint(Canvas c, double scale, int dx, int dy) {
		for (int i = 0; i < sprites.size(); i++) {
			((Sprite) sprites.elementAt(i)).paint(c, scale, dx, dy);
		}
	}

	// public abstract boolean play(boolean key_left, boolean key_right,
	// boolean key_fire, double trackball_dx, boolean touch_fire,
	// double touch_x, double touch_y, boolean ats_touch_fire,
	// double ats_touch_dx);
}
