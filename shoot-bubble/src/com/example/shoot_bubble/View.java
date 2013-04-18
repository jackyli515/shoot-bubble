package com.example.shoot_bubble;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class View extends SurfaceView implements SurfaceHolder.Callback {
	class GameThread extends Thread {

		boolean isItOkay = false;
		boolean mImagesReady = false;

		// Original Images
		private Bitmap mBackgroundOrig;
		private Bitmap[] mBubblesOrig;

		// Resized Images
		private Bmp mBackground;
		private Bmp[] mBubbles;

		private SurfaceHolder mSurfaceHolder;

		public static final int GAMEFIELD_WIDTH = 320;
		public static final int GAMEFIELD_HEIGHT = 480;
		public static final int EXTENDED_GAMEFIELD_WIDTH = 640;

		private double mDisplayScale;
		private int mDisplayDX;
		private int mDisplayDY;
		private int mCanvasHeight = 1;
		private int mCanvasWidth = 1;

		// Managers
		private LevelManager mLevelManager;

		// vector contains all resized images
		Vector mImageList;

		public GameThread(SurfaceHolder surfaceHolder, byte[] customLevels,
				int startingLevel) {
			mSurfaceHolder = surfaceHolder;
			Resources res = mContext.getResources();
			mBackgroundOrig = BitmapFactory.decodeResource(res,
					R.drawable.background);
			mImageList = new Vector();
			mBackground = NewBmpWrap();
			isItOkay = true;

			if (null == customLevels) {
				try {
					InputStream is = mContext.getAssets().open("levels.txt");
					int size = is.available();
					byte[] levels = new byte[size];
					is.read(levels);
					is.close();
//					SharedPreferences sp = mContext.getSharedPreferences(
//							FrozenBubble.PREFS_NAME, Context.MODE_PRIVATE);
//					startingLevel = sp.getInt("level", 0);
					mLevelManager = new LevelManager(levels, startingLevel);
				} catch (IOException e) {
					// Should never happen.
					throw new RuntimeException(e);
				}
			} else {
				// We were launched by the level editor.
				mLevelManager = new LevelManager(customLevels, startingLevel);
			}
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
			System.out.println(width + " " + height + " " + GAMEFIELD_WIDTH
					+ " " + GAMEFIELD_HEIGHT);
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
			image.bmp = Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight,
					true);
		}

		private void resizeBitmaps() {
			scaleFrom(mBackground, mBackgroundOrig);
			mImagesReady = true;
		}

		private void doDraw(Canvas canvas) {
			if (!mImagesReady) {
				return;
			}
			if (mDisplayDX > 0 || mDisplayDY > 0) {
				canvas.drawRGB(0, 0, 0);
			}
			drawBackground(canvas);
		}

		private void drawBackground(Canvas c) {
			c.drawBitmap(mBackground.bmp,
					(float) (0 * mDisplayScale + mDisplayDX),
					(float) (0 * mDisplayScale + mDisplayDY), null);
		}

		@Override
		public void run() {
			while (isItOkay) {
				if (!mSurfaceHolder.getSurface().isValid())
					continue;
				Canvas c = mSurfaceHolder.lockCanvas();
				doDraw(c);
				mSurfaceHolder.unlockCanvasAndPost(c);
			}
		}
	}

	private Context mContext;
	private GameThread thread;
	
	public View(Context context) {
		super(context);
		mContext = context;
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new GameThread(holder, null, 0);
		setFocusable(true);
		setFocusableInTouchMode(true);
		thread.start();
	}

	public View(Context context, byte[] levels, int startingLevel) {
		super(context);
		mContext = context;
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new GameThread(holder, levels, startingLevel);
		setFocusable(true);
		setFocusableInTouchMode(true);
		thread.start();
	}
	
	

	public GameThread getThread() {
		return thread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.isItOkay = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.isItOkay = false;
	}

}
