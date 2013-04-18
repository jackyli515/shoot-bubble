package com.example.shoot_bubble;

import android.graphics.Canvas;
import android.graphics.Rect;

public class BubbleSprite extends Sprite {

	public BubbleSprite(Rect spriteArea) {
		super(spriteArea);
	}

	public int getTypeId() {
		 return Sprite.TYPE_BUBBLE;
	}

	public void paint(Canvas c, double scale, int dx, int dy) {
		
	}

}
