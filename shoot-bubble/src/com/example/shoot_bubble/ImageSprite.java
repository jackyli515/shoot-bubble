package com.example.shoot_bubble;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

public class ImageSprite extends Sprite {

	private Bmp image;

	public ImageSprite(Rect spriteArea, Bmp img) {
		super(spriteArea);
		image = img;
	}

//	public void saveState(Bundle map, Vector savedSprites) {
//	    if (getSavedId() != -1) {
//	      return;
//	    }
//	    super.saveState(map, savedSprites);
//	    map.putInt(String.format("%d-imageId", getSavedId()), displayedImage.id);
//	  }

	  public int getTypeId()
	  {
	    return Sprite.TYPE_IMAGE;
	  }

	  public void changeImage(Bmp img)
	  {
	    image = img;
	  }

	  public final void paint(Canvas c, double scale, int dx, int dy)
	  {
	    Point p = super.getSpritePosition();
	    drawImage(image, p.x, p.y, c, scale, dx, dy);
	  }

}
