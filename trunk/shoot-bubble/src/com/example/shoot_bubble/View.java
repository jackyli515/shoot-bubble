package com.example.shoot_bubble;

import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class View extends SurfaceView implements Runnable {
	
	boolean isItOkay = false;
	Thread thread = null;
	SurfaceHolder sHolder;

	boolean mImagesReady = false;

	Bitmap ball;
	float X, Y;
	// Original Images
	private Bitmap mBackgroundOrig;
	private Bitmap[] mBubblesOrig;

	// Resized Images
	private Bmp mBackground;
	private Bmp[] mBubbles;

	public static final int GAMEFIELD_WIDTH = 320;
	public static final int GAMEFIELD_HEIGHT = 480;
	public static final int EXTENDED_GAMEFIELD_WIDTH = 640;

	private double mDisplayScale;
	private int mDisplayDX;
	private int mDisplayDY;
	private int mCanvasHeight = 1;
	private int mCanvasWidth = 1;

	// vector contains all resized images
	Vector mImageList;

	public View(Context context, Bitmap b, float x, float y) {
		super(context);
		sHolder = getHolder();
		Resources res = context.getResources();
		mImageList = new Vector();
		mBackground = NewBmpWrap();
		mBackgroundOrig = BitmapFactory.decodeResource(res,
				R.drawable.background);
		System.out.println("aaaaaaaa");
//		setSurfaceSize(getWidth(), getHeight());
		this.ball = b;
		this.X = x;
		this.Y = y;
	}

	private Bmp NewBmpWrap() {
		int new_img_id = mImageList.size();
		System.out.println(new_img_id);
		Bmp new_img = new Bmp(new_img_id);
		mImageList.addElement(new_img);
		return new_img;
	}

	public void setSurfaceSize(int width, int height) {
		mCanvasWidth = width;
		mCanvasHeight = height;
		System.out.println("1111111");
		System.out.println(width+" "+height+" "+GAMEFIELD_WIDTH+" "+GAMEFIELD_HEIGHT);
		if (width / height >= GAMEFIELD_WIDTH / GAMEFIELD_HEIGHT) {
			mDisplayScale = 1.0 * height / GAMEFIELD_HEIGHT;
			mDisplayDX = (int) ((width - mDisplayScale
					* EXTENDED_GAMEFIELD_WIDTH) / 2);
			mDisplayDY = 0;
		} else {
			mDisplayScale = 1.0 * width / GAMEFIELD_WIDTH;
			mDisplayDX = (int) (-mDisplayScale
					* (EXTENDED_GAMEFIELD_WIDTH - GAMEFIELD_WIDTH) / 2);
			mDisplayDY = (int) ((height - mDisplayScale * GAMEFIELD_HEIGHT) / 2);
		}
		resizeBitmaps();
	}

	private void scaleFrom(Bmp image, Bitmap bmp) {
		if (image.bmp != null && image.bmp != bmp) {
			image.bmp.recycle();
		}

		if (mDisplayScale > 0.99999 && mDisplayScale < 1.00001) {
			image.bmp = bmp;
			return;
		}
		int dstWidth = (int) (bmp.getWidth() * mDisplayScale);
		int dstHeight = (int) (bmp.getHeight() * mDisplayScale);
		image.bmp = Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
	}

	private void resizeBitmaps() {
		scaleFrom(mBackground, mBackgroundOrig);
		// for (int i = 0; i < mBubblesOrig.length; i++) {
		// scaleFrom(mBubbles[i], mBubblesOrig[i]);
		// }
		// for (int i = 0; i < mBubblesBlind.length; i++) {
		// scaleFrom(mBubblesBlind[i], mBubblesBlindOrig[i]);
		// }
		// for (int i = 0; i < mFrozenBubbles.length; i++) {
		// scaleFrom(mFrozenBubbles[i], mFrozenBubblesOrig[i]);
		// }
		// for (int i = 0; i < mTargetedBubbles.length; i++) {
		// scaleFrom(mTargetedBubbles[i], mTargetedBubblesOrig[i]);
		// }
		// scaleFrom(mBubbleBlink, mBubbleBlinkOrig);
		// scaleFrom(mGameWon, mGameWonOrig);
		// scaleFrom(mGameLost, mGameLostOrig);
		// scaleFrom(mHurry, mHurryOrig);
		// scaleFrom(mPenguins, mPenguinsOrig);
		// scaleFrom(mCompressorHead, mCompressorHeadOrig);
		// scaleFrom(mCompressor, mCompressorOrig);
		// scaleFrom(mLife, mLifeOrig);
		// scaleFrom(mFontImage, mFontImageOrig);
		mImagesReady = true;
	}

	private void doDraw(Canvas canvas) {
		if (!mImagesReady) {
			System.out.println(":O");
			return;
		}
		if (mDisplayDX > 0 || mDisplayDY > 0) {
			canvas.drawRGB(0, 0, 0);
			System.out.println(":(");
		}
		System.out.println(":)");
		drawBackground(canvas);
	}

	private void drawBackground(Canvas c) {
		c.drawBitmap(mBackground.bmp, (float) (0 * mDisplayScale + mDisplayDX),
				(float) (0 * mDisplayScale + mDisplayDY), null);
	}

	@Override
	public void run() {
		while (isItOkay) {
			if (!sHolder.getSurface().isValid())
				continue;
			Canvas c = sHolder.lockCanvas();

			// here we draw images
			setSurfaceSize(getWidth(), getHeight());
			doDraw(c);
//			c.drawARGB(255, 150, 150, 10);
			c.drawBitmap(ball, X, Y, null);
			sHolder.unlockCanvasAndPost(c);
		}
	}

	public void pause() {
		isItOkay = false;
		while (true) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}

	public void resume() {
		isItOkay = true;
		thread = new Thread(this);
		thread.start();
	}

}
